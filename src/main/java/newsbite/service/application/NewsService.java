package newsbite.service.application;

import newsbite.dto.response.NewsQueryResponse;

public interface NewsService {

    public NewsQueryResponse queryNews(String query, boolean retry);
}
