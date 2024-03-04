package aion.service.application;

import aion.dto.response.NewsQueryResponse;

public interface NewsService {

    public NewsQueryResponse queryNews(String query, boolean retry);
}
