package newsbite.service.application.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import newsbite.dto.response.NewsQueryResponse;
import newsbite.dto.response.SearchEngineNewsResponse;
import newsbite.model.News;
import newsbite.service.application.NewsService;
import newsbite.service.domain.GeminiService;
import newsbite.service.domain.SearchEngineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPooled;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final SearchEngineService searchEngineService;
    private final GeminiService geminiService;

    private final JedisPooled jedis;
    private final ObjectMapper mapper;
    private final Logger logger = LoggerFactory.getLogger(NewsService.class);

    private final static Integer DEFAULT_START = 0;
    private final static Integer DEFAULT_DISPLAY = 10;

    public NewsQueryResponse queryNews(String query, boolean retry) {

        final SearchEngineNewsResponse searchResults;

        // Cached Query
        if (!retry && jedis.exists(query)) {
            Object cachedResponse = jedis.jsonGet(query);
            searchResults = mapper.convertValue(cachedResponse, SearchEngineNewsResponse.class);

        // Continuing Query from n ~ n + 10
        } else if (retry && jedis.exists(query)) {
            Object cachedResponse = jedis.jsonGet(query);
            SearchEngineNewsResponse oldSearchResults = mapper.convertValue(cachedResponse, SearchEngineNewsResponse.class);

            final Integer start = oldSearchResults.getStart() + DEFAULT_DISPLAY;

            searchResults = searchEngineService.searchNews(query, start);

            jedis.jsonSetWithEscape(query, searchResults);

        // New Query from 1 ~ 10
        } else {
            searchResults = searchEngineService.searchNews(query, DEFAULT_START);

//            jedis.jsonSetWithEscape(query, searchResults);
        }

        logger.info(searchResults.toString());

        final News news = geminiService.analyzeNews(searchResults);

        logger.info(news.getText());

        return NewsQueryResponse.builder()
                .source(searchResults)
                .res(news.getText())
                .build();
    }
}
