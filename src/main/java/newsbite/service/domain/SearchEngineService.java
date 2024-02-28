package newsbite.service.domain;

import newsbite.dto.response.SearchEngineNewsResponse;

public interface SearchEngineService {
    public SearchEngineNewsResponse searchNews(String query, Integer start);
}
