package com.realtimeview.app.repository;

import com.realtimeview.app.model.TrackedSymbols;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// Long is used for id as the primary key with TrackedSymbols as the class identifier
public interface TrackedSymbolsRepository extends JpaRepository<TrackedSymbols, Long> {
    // Check for duplicate symbols
    Optional<TrackedSymbols> findBySymbolAndType(String symbol, String type);
    // Check for a list of symbols based on header
    List<TrackedSymbols> findByTypeAndActiveTrue(String type);
}
