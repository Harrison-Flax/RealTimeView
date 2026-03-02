package com.realtimeview.app.service;

import com.realtimeview.app.dto.StockDTO;
import com.realtimeview.app.model.StockIndex;
import com.realtimeview.app.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StockService {

    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // Fetch all stocks from DB and convert to DTOs
    public List<StockDTO> getAllStocks() {
        return stockRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Fetch single stock by ticker
    public StockDTO getByTicker(String ticker) {
        return stockRepository.findByTicker(ticker)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Ticker not found: " + ticker));
    }

    // External API call here
    public void fetchAndSaveFromAPI(String ticker) {
        // Call stock API, map response to StockIndex, save via repository
    }

    // More stock methods to interpret changes
    private StockDTO toDTO(StockIndex s) {
        return new StockDTO(s.getTicker(), s.getName(), s.getPrice(),
                s.getChange(), s.getChangePercent(), s.getLastUpdate());
    }

}
