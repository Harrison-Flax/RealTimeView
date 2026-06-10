package com.realtimeview.app.scheduler;

import com.realtimeview.app.service.NewsService;
import com.realtimeview.app.service.TrackedSymbolsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NewsScheduler {
    // For Logging
    private static final Logger logger = LoggerFactory.getLogger(NewsScheduler.class);

    private final NewsService newsService;

    private final TrackedSymbolsService trackedSymbolsService;

    public NewsScheduler(NewsService newsService, TrackedSymbolsService trackedSymbolsService) {
        this.newsService = newsService;
        this.trackedSymbolsService = trackedSymbolsService;
    }

    // Scheduling for real time updates
    // Possible with Spring and a fixed rate of 60 seconds
    @Scheduled(fixedRate = 60000)
    public void refreshNews() {
        // Need to track a list of coins
        // Starting with 5 for now
        List<String> categories = trackedSymbolsService.getActiveByType("NEWS");

        // Iterate through each of the tickers
        for (String category : categories) {
            // Need a try catch block for logging and an exception
            try {
                // Calling the method to update the coin info
                newsService.fetchAndSaveFromAPI(category);
                logger.info("Successfully refreshed: {}", category);
            } catch (Exception e) {
                logger.error("Failed to refresh {}: {}", category, e.getMessage());
            }
        }
    }
}
