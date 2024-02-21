package newsbite.controller;

import lombok.RequiredArgsConstructor;
import newsbite.dto.response.NewsQueryResponse;
import newsbite.service.application.NewsService;
import newsbite.service.domain.SearchEngineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<NewsQueryResponse> getNewsQuery(@RequestParam String query) {
        final NewsQueryResponse res = newsService.queryNews(query);



        return new ResponseEntity<NewsQueryResponse>(res, HttpStatus.OK);
    }
}
