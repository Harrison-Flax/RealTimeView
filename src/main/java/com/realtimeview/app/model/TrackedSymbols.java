package com.realtimeview.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Follows pattern from StockIndex class
// But all stock elements are handled here
// To handle multiple non hardcoded stocks
@Entity
@Table(name = "tracked_symbols")
@Getter
@Setter
@NoArgsConstructor
public class TrackedSymbols {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // A symbol is not nullable and is unique
    @Column(nullable = false, unique = true)
    private String symbol;

    // A type is not nullable and isn't unique
    // As many symbols can have a type
    @Column(nullable = false)
    private String type;

    private boolean active;

    // Timestamp for SQL
    @Column(name = "added_at")
    private LocalDateTime addedAt;
}
