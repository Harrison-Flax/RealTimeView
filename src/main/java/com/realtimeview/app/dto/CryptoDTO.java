package com.realtimeview.app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CryptoDTO (
    String symbol,
    String name,
    BigDecimal priceUsed,
    BigDecimal marketCap,
    BigDecimal changePercent24h,
    LocalDateTime lastUpdated
) {}
