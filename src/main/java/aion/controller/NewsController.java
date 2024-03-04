package aion.controller;

import lombok.RequiredArgsConstructor;
import aion.dto.response.NewsQueryResponse;
import aion.service.application.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(NewsController.class);
    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<Object> getNewsQuery(
            @RequestParam String query,
            @RequestParam boolean retry) {

        final NewsQueryResponse response = newsService.queryNews(query, retry);

        logger.info(response.toString());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
