package com.realtimeview.app.controller;

import com.realtimeview.app.dto.StockDTO;
import com.realtimeview.app.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    // GET /api/stocks - Get all stocks
    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    // GET /api/stocks/{ticker} - Get stock by ticker
    @GetMapping("/{ticker}")
    public ResponseEntity<StockDTO> getByTicker(@PathVariable String ticker) {
        return ResponseEntity.ok(stockService.getByTicker(ticker));
    }

    // POST /api/stocks/refresh/{ticker} - Refresh stock data from external API
    @PostMapping("/refresh/{ticker}")
    public ResponseEntity<Void> refreshStock(@PathVariable String ticker) {
        stockService.fetchAndSaveFromAPI(ticker);
        return ResponseEntity.noContent().build();
    }

}
