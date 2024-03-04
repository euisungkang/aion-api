package aion.service.domain.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import aion.dto.response.OpenAIResponse;
import aion.dto.response.SearchEngineNewsResponse;
import aion.exception.RESTConnectionException;
import aion.exception.ResponseProcessingException;
import aion.model.News;
import aion.service.domain.GPTService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.gpt.url}")
    private String apiURL;

    @Value("${openai.organization.id}")
    private String orgId;

    @Value("${gpt.prompt.system}")
    private String systemPrompt;

    @Value("${gpt.prompt.user}")
    private String userPrompt;

    private final ObjectMapper mapper;

    @Override
    public News analyzeNews(SearchEngineNewsResponse newsResponse) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("OpenAI-Organization", orgId);
        headers.setBearerAuth(apiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo-0125");
        requestBody.put("response_format", Map.of("type", "json_object"));
        requestBody.put("messages", new Object[]{
            Map.of("role", "system", "content", systemPrompt),
            Map.of("role", "user", "content", userPrompt),
        });

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<OpenAIResponse> response = restTemplate.postForEntity(apiURL, requestEntity, OpenAIResponse.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return mapResponseToNews(response.getBody())
                    .orElseThrow(() -> new ResponseProcessingException("GPT: Error processing JSON response after deserialization"));
        } else {
            throw new RESTConnectionException("Error connecting to GPT: " + response.getStatusCode());
        }
    }

    private Optional<News> mapResponseToNews(OpenAIResponse response) {
        try {
            if (response != null && response.getChoices() != null) {
                OpenAIResponse.Choice choice = response.getChoices().getFirst();
                if (choice.getMessage() != null && choice.getMessage().getContent() != null) {
                    String text = choice.getMessage().getContent();

                    System.out.println(text);

                    News validNews = mapper.readValue(text, News.class);
                    return Optional.of(validNews);
                }
            }
        } catch (JsonProcessingException ex) {
            throw new ResponseProcessingException("GPT: Error processing JSON response during deserialization", ex);
        }

        return Optional.empty();
    }
}
