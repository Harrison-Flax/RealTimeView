package com.realtimeview.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "news_articles")
// Have lombok with Spring automate Getters and Setters
@Getter
@Setter
@NoArgsConstructor
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String headline;

    @Column(columnDefinition = "TEXT")
    private String summary;

    private String source;
    private String url;
    private String category;

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "saved_by_user")
    private boolean savedByUser = false;

    // Constructors, getters, and setters

}
