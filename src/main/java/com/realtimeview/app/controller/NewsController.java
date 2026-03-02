package com.realtimeview.app.controller;

import com.realtimeview.app.dto.NewsDTO;
import com.realtimeview.app.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public ResponseEntity<List<NewsDTO>> getAllNews() {
        return ResponseEntity.ok(newsService.getAllNews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNewsById(@PathVariable Long id) {
        return ResponseEntity.ok(newsService.getNewsById(id));
    }

    @PostMapping("/refresh/{id}")
    public ResponseEntity<Void> refreshNews(@PathVariable String id) {
        newsService.fetchAndSaveFromAPI(id);
        return ResponseEntity.noContent().build();
    }
}
