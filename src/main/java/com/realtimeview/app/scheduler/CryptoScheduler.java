package com.realtimeview.app.scheduler;

import com.realtimeview.app.service.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public class CryptoScheduler {
    // For Logging
    private static final Logger logger = LoggerFactory.getLogger(CryptoScheduler.class);

    private final CryptoService cryptoService;

    public CryptoScheduler(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    // Scheduling for real time updates
    // Possible with Spring and a fixed rate of 60 seconds
    @Scheduled(fixedRate = 60000)
    public void refreshCrypto() {
        // Need to track a list of coins
        // Starting with 5 for now
        List<String> coinList = List.of("BTC", "ETH", "SOL", "ADA", "XRP");

        // Iterate through each of the tickers
        for (String coin : coinList) {
            // Need a try catch block for logging and an exception
            try {
                // Calling the method to update the coin info
                cryptoService.fetchAndSaveFromAPI(coin);
                logger.info("Successfully refreshed: {}", coin);
            } catch (Exception e) {
                logger.error("Failed to refresh {}: {}", coin, e.getMessage());
            }
        }
    }
}
