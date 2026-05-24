package com.realtimeview.app.repository;

import com.realtimeview.app.model.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<NewsArticle, Long> {
    List<NewsArticle> findByCategory(String category);
    List<NewsArticle> findBySavedByUserTrue();
    Optional<NewsArticle> findByUrl(String url);
}
