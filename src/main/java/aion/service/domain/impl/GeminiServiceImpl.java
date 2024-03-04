package aion.service.domain.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import aion.dto.response.GeminiResponse;
import aion.dto.response.SearchEngineNewsResponse;
import aion.exception.RESTConnectionException;
import aion.exception.ResponseProcessingException;
import aion.model.News;
import aion.service.domain.GeminiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.prompt}")
    private String geminiPrompt;

    private final ObjectMapper mapper;

    @Override
    public News analyzeNews(SearchEngineNewsResponse newsResponse) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final String URL = geminiApiUrl + geminiApiKey;
        String requestBody = String.format("{ \"contents\": [{ \"parts\": [{ \"text\": \"%s\" }] }] }",
                String.format(geminiPrompt, newsResponse.getItems().toString()));

        HttpEntity<String> requestEntity = new HttpEntity<String>(requestBody, headers);

        ResponseEntity<GeminiResponse> response = restTemplate.postForEntity(URL, requestEntity, GeminiResponse.class);

        if (response.getStatusCode() == HttpStatus.OK &&
                response.getBody() != null) {

            return mapResponseToNews(response.getBody())
                    .orElseThrow(() -> new ResponseProcessingException("Gemini response received, but not expected format "));
        } else {
            throw new RESTConnectionException("Gemini response failed with code: " + response.getStatusCode());
        }
    }

    private Optional<News> mapResponseToNews(GeminiResponse response) {
//        try {
//            final String resString = response.getCandidates().getFirst()
//                    .getContent().getParts().getFirst().getText();
//
//            News news = mapper.readValue(resString, News.class);
//
//            return Optional.of(news);

             News news = new News();
             news.setText(response.getCandidates().getFirst()
                    .getContent().getParts().getFirst().getText());

             return Optional.of(news);
//        }
//        catch (JsonProcessingException ex) {
//            throw new ResponseProcessingException("Gemini: Error deserializing JSON response");
//        }
    }
}