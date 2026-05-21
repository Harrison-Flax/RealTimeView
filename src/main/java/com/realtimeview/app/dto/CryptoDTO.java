package com.realtimeview.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CryptoDTO (
        Long id,
        String name,
        String symbol,
        BigDecimal priceUsd,
        BigDecimal marketCap,
        BigDecimal changePercent24h,
        LocalDateTime lastUpdated
) {}