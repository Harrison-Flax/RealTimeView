package com.realtimeview.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.dto.NewsDTO;
import com.realtimeview.app.model.NewsArticle;
import com.realtimeview.app.repository.NewsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NewsServiceTest {
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private NewsService newsService;

    // 1) getAllNews should return a list
    @Test
    @DisplayName("Return Test: a list is the result")
    public void getAllNewsIsAList() {
        // Creating two news articles to test with for their categories
        // ARRANGE
        NewsArticle article1 = new NewsArticle();
        NewsArticle article2 = new NewsArticle();

        article1.setCategory("Business");
        article2.setCategory("Politics");

        // Need a list
        List<NewsArticle> articles = List.of(article1, article2);

        // When findAll is called, return articles
        when(newsRepository.findAll()).thenReturn(articles);

        // Call the function for the return result
        // ACT
        List<NewsDTO> result = newsService.getAllNews();

        // Prove that the size is 2
        // ASSERT
        assertEquals(2, result.size());

        // Prove that the first item's category is Business
        assertEquals("Business", result.get(0).category());
    }

    // 2) getById should throw an exception when an ID isn't found
    @Test
    @DisplayName("Exception Test: when an ID isn't found")
    public void getByIdIsAnExceptionWithoutCategory() {
        // ARRANGE
        // Mock repository returns an empty Optional
        // When the function is called
        // 99L = 99 long (since Id is in long)
        when(newsRepository.findById(99L)).thenReturn(Optional.empty());

        // NO ACT HERE

        // ASSERT
        assertThrows(RuntimeException.class, () -> newsService.getById(99L));
    }

    // 3) getById returns the correct DTO when found
    @Test
    @DisplayName("Return Test: a DTO is found when existing")
    public void getByIdReturnDTO() {
        // ARRANGE
        // Creating a stock with known values
        NewsArticle knownArticle = new NewsArticle();

        // Setting the known values
        knownArticle.setCategory("Politics");
        knownArticle.setSource("WSJ");
        knownArticle.setSummary("AI Boom");

        // Mock repository returns it wrapped in Optional.of()
        // Only when findByCategory("Any Category") is called
        when(newsRepository.findById(1L)).thenReturn(Optional.of(knownArticle));

        // ACT
        // Call on the newsService function
        NewsDTO result = newsService.getById(1L);

        // ASSERT
        // Check if the attributes line up with the article
        assertEquals("Politics", result.category());
        assertEquals("WSJ", result.source());
        assertEquals("AI Boom", result.summary());
    }
}
