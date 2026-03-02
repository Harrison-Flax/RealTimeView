package com.realtimeview.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StockDTO (
    String ticker,
    String name,
    BigDecimal price,
    BigDecimal change,
    BigDecimal changePercent,
    LocalDateTime lastUpdated
) {}
