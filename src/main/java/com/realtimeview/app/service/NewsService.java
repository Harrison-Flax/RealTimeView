package com.realtimeview.app.service;

import com.realtimeview.app.dto.NewsDTO;
import com.realtimeview.app.model.NewsArticle;
import com.realtimeview.app.repository.NewsRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    // Fetch all news articles from DB and convert to DTOs
    public List<NewsDTO> getAllNews() {
        return newsRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Fetch single news article by ID
    public NewsDTO getById(Long id) {
        return newsRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("News article not found: " + id));
    }

    // External API call here
    public void fetchAndSaveFromAPI() {
        // Call news API, map response to NewsArticle, save via repository

    }

    // More news methods to interpret changes
    private NewsDTO toDTO(NewsArticle n) {
        return new NewsDTO(n.getId(), n.getTitle(), n.getDescription(),
                n.getUrl(), n.getPublishedAt());
    }

}
