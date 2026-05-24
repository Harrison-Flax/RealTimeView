package com.realtimeview.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.dto.NewsDTO;
import com.realtimeview.app.model.NewsArticle;
import com.realtimeview.app.model.StockIndex;
import com.realtimeview.app.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    // RestClient
    private final RestClient restClient;
    // Jackson ObjectMapper
    private final ObjectMapper objectMapper;

    // Declaring the apiKey value here
    @Value("${newsapi.api.key}")
    private String apiKey;

    public NewsService(NewsRepository newsRepository, ObjectMapper objectMapper) {
        this.newsRepository = newsRepository;
        this.restClient = RestClient.create();
        this.objectMapper = objectMapper;
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
    public void fetchAndSaveFromAPI(String category) {
        // Call news API, map response to NewsArticle, save via repository

        // Fetch the url
        String url = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=" + apiKey;

        // RestClient GET request
        String result = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);

        try {
            // Parsing the JSON string
            JsonNode rootNode = objectMapper.readTree(result);

            // Go through articles
            JsonNode articles = rootNode.path("articles");

            // Iterating through each article info
            for (JsonNode article : articles) {
                String headline = article.path("title").asText();
                String summary = article.path("description").asText();
                String source = article.path("source").path("name").asText();
                String url2 = article.path("url").asText();

                // Need to convert date and time zone and format it
                String publishedAtRaw = article.path("publishedAt").asText();
                LocalDateTime publishedAt = OffsetDateTime.parse(publishedAtRaw)
                        .toLocalDateTime();

                // Check if URL already exists so no duplicates are present
                if (newsRepository.findByUrl(url2).isEmpty()) {
                    NewsArticle newsArticle = new NewsArticle();
                    newsArticle.setHeadline(headline);
                    newsArticle.setSummary(summary);
                    newsArticle.setSource(source);
                    newsArticle.setUrl(url2);
                    newsArticle.setCategory(category);
                    newsArticle.setPublishedAt(publishedAt);

                    newsRepository.save(newsArticle);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch/parse stock data for: " + category, e);
        }
    }

    // More news methods to interpret changes
    private NewsDTO toDTO(NewsArticle n) {
        return new NewsDTO(n.getId(), n.getHeadline(), n.getSummary(),
                n.getSource(), n.getUrl(), n.getCategory(), n.getPublishedAt());
    }

}
