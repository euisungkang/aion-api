package aion.service.domain;

import aion.dto.response.SearchEngineNewsResponse;
import aion.model.News;

public interface GeminiService {

    public News analyzeNews(SearchEngineNewsResponse newsResponse);
}
