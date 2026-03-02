package com.realtimeview.app.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "crypto_assets")
public class CryptoAsset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Symbol values (e.g. "BTC")
    @Column(nullable = false, unique = true)
    private String symbol;

    // Name of the crypto asset
    @Column(nullable = false)
    private String name;

    private BigDecimal priceUsd;
    private BigDecimal marketCap;
    private BigDecimal volume24h;
    private BigDecimal changePercent24th;

    // Time of update
    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    // Contructors, getters, and setters

}
