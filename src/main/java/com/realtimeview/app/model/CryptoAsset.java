package com.realtimeview.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "crypto_assets")
// Have lombok with Spring automate Getters and Setters
@Getter
@Setter
@NoArgsConstructor
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
    private BigDecimal changePercent24h;

    // Time of update
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Contructors, getters, and setters

}
