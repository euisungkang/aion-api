package newsbite.service.domain;

import newsbite.dto.response.SearchEngineNewsResponse;
import newsbite.model.News;

public interface GeminiService {

    public News analyzeNews(SearchEngineNewsResponse newsResponse);
}
