package newsbite.service.application.impl;

import lombok.RequiredArgsConstructor;
import newsbite.dto.response.NewsQueryResponse;
import newsbite.model.SearchEngineNewsResponse;
import newsbite.service.application.NewsService;
import newsbite.service.domain.SearchEngineService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final SearchEngineService searchEngineService;

    public NewsQueryResponse queryNews(String query) {
        final SearchEngineNewsResponse searchResults = searchEngineService.searchNews(query);

        System.out.println(searchResults);

        return null;
    }
}
