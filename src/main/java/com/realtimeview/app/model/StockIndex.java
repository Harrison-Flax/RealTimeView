package com.realtimeview.app.model;

// Object mapping
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// Need control over rounding for indices
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_indices")
// Have lombok with Spring automate Getters and Setters
@Getter
@Setter
@NoArgsConstructor
public class StockIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ticker values (e.g. "AAPL")
    @Column(nullable = false, unique = true)
    private String ticker;

    // Name of the stock listing
    @Column(nullable = false)
    private String name;

    private BigDecimal price;
    private BigDecimal change;
    private BigDecimal changePercent;

    // Time of update
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Contructors, getters, and setters





}
