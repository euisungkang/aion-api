package aion.service.domain;

import aion.dto.response.SearchEngineNewsResponse;

public interface SearchEngineService {
    public SearchEngineNewsResponse searchNews(String query, Integer start);
}
