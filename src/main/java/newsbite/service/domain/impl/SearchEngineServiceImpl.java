package newsbite.service.domain.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import newsbite.exception.RESTConnectionException;
import newsbite.exception.ResponseProcessingException;
import newsbite.dto.response.SearchEngineNewsResponse;
import newsbite.service.domain.SearchEngineService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchEngineServiceImpl implements SearchEngineService {

    @Value("${naver.search.clientID}")
    private String clientId;

    @Value("${naver.search.secret}")
    private String secret;

    @Value("${naver.search.news.url}")
    private String searchNewsUrl;

    private final ObjectMapper mapper;

    public SearchEngineNewsResponse searchNews(String query, Integer start) {
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", secret);

        String apiURL = searchNewsUrl + encodeValue(query);

        HttpURLConnection con = connect(apiURL);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();

            // Successful response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readBody(con.getInputStream())
                        .orElseThrow(() -> new ResponseProcessingException("Naver Search: deserialized JSON was empty or malformed"));

            // Unsuccessful response
            } else {
                throw new RESTConnectionException("Search Engine, response returned code of " + responseCode + " " + con.getErrorStream());
            }
        } catch (IOException ex) {
            throw new ResponseProcessingException("Search Engine, failed to parse response of News API : " + ex.getMessage(), ex);
        }
        finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiURL) {
        try {
            URL url = new URL(apiURL);
            return (HttpURLConnection)url.openConnection();
        } catch (IOException ex) {
            throw new RESTConnectionException("Search Engine connection failed. : " + apiURL, ex);
        }
    }

    private Optional<SearchEngineNewsResponse> readBody(InputStream body) throws IOException {
        try {
            SearchEngineNewsResponse response = mapper.readValue(body, SearchEngineNewsResponse.class);
            if (response.getDisplay() > 0 &&
                !response.getItems().isEmpty()) {
                    return Optional.of(response);
            }
        } catch (IOException|IllegalArgumentException ex) {
            throw new IOException("Failed to map News response to JSON ", ex);
        }

        return Optional.empty();
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
