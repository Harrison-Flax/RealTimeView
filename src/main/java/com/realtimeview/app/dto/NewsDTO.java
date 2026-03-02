package com.realtimeview.app.dto;

import java.time.LocalDateTime;

public record NewsDTO (
    Long id,
    String headline,
    String summary,
    String source,
    String url,
    String category,
    LocalDateTime publishedAt
) {}