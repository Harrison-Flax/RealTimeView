package com.realtimeview.app.scheduler;

import com.realtimeview.app.service.StockService;
import com.realtimeview.app.service.TrackedSymbolsService;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class StockScheduler {
    // For Logging
    private static final Logger logger = LoggerFactory.getLogger(StockScheduler.class);

    private final StockService stockService;

    private final TrackedSymbolsService trackedSymbolsService;

    public StockScheduler(StockService stockService, TrackedSymbolsService trackedSymbolsService) {
        this.stockService = stockService;
        this.trackedSymbolsService = trackedSymbolsService;
    }

    // Scheduling for real time updates
    // Possible with Spring and a fixed rate of 60 seconds
    @Scheduled(fixedRate = 60000)
    public void refreshStocks() {
        // Need to track a list of tickers
        // Starting with 5 for now
        List<String> tickerList = trackedSymbolsService.getActiveByType("STOCK");

        // Iterate through each of the tickers
        for (String ticker : tickerList) {
            // Need a try catch block for logging and an exception
            try {
                // Calling the method to update the ticker info
                stockService.fetchAndSaveFromAPI(ticker);
                logger.info("Successfully refreshed: {}", ticker);
            } catch (Exception e) {
                logger.error("Failed to refresh {}: {}", ticker, e.getMessage());
            }
        }
    }
}
