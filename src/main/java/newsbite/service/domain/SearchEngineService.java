package newsbite.service.domain;

import newsbite.model.SearchEngineNewsResponse;

public interface SearchEngineService {
    public SearchEngineNewsResponse searchNews(String query);
}
