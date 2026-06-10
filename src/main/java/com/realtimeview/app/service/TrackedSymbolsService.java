package com.realtimeview.app.service;

import com.realtimeview.app.model.TrackedSymbols;
import com.realtimeview.app.repository.TrackedSymbolsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrackedSymbolsService {
    private final TrackedSymbolsRepository trackedSymbolsRepository;

    public TrackedSymbolsService(TrackedSymbolsRepository trackedSymbolsRepository) {
        this.trackedSymbolsRepository = trackedSymbolsRepository;
    }

    public List<String> getActiveByType(String type) {
        return trackedSymbolsRepository.findByTypeAndActiveTrue(type)
                .stream()
                // Get the symbol
                .map(TrackedSymbols::getSymbol)
                .toList();
    }

    public void addSymbol(String symbol, String type) {
            // Check if symbol already exists
            if (trackedSymbolsRepository.findBySymbolAndType(symbol, type).isPresent()) {
                throw new RuntimeException("Symbol already tracked: " + symbol);
            }

            // Save new symbol
            TrackedSymbols newSymbol = new TrackedSymbols();
            newSymbol.setSymbol(symbol);
            newSymbol.setType(type);
            newSymbol.setActive(true);
            newSymbol.setAddedAt(LocalDateTime.now());
            trackedSymbolsRepository.save(newSymbol);
    }

    public void deactivateSymbol(String symbol, String type) {
        // Use type here so it isn't hardcoded
        TrackedSymbols existing = trackedSymbolsRepository.findBySymbolAndType(symbol, type)
                .orElseThrow(() -> new RuntimeException("Symbol not found: " + symbol));
        existing.setActive(false);
        trackedSymbolsRepository.save(existing);
    }
}