package com.realtimeview.app.service;

import com.realtimeview.app.dto.StockDTO;
import com.realtimeview.app.model.StockIndex;
import com.realtimeview.app.repository.StockRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StockService {

    // To access the stock repository
    private final StockRepository stockRepository;
    // RestClient
    private final RestClient restClient;
    // Jackson ObjectMapper
    private final ObjectMapper objectMapper;

    // Declaring the apiKey value here
    @Value("${alphavantage.api.key}")
    private String apiKey;

    public StockService(StockRepository stockRepository, ObjectMapper objectMapper) {
        this.stockRepository = stockRepository;
        this.restClient = RestClient.create();
        this.objectMapper = objectMapper;
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
    // Will be using Alpha Vantage
    public void fetchAndSaveFromAPI(String ticker) {
        // Call stock API, map response to StockIndex, save via repository

        // Global Quote returns the latest info
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + ticker + "&apikey=" + apiKey;

        // RestClient GET request
        String result = restClient.get()
                .uri(url)
                .retrieve()
                .body(String.class);
        try {
            // Parsing the JSON string
            JsonNode rootNode = objectMapper.readTree(result);
            JsonNode quoteNode = rootNode.path("Global Quote");

            // Extracting each of the fields separately
            BigDecimal price = new BigDecimal(quoteNode.path("05. price").asText());
            BigDecimal change = new BigDecimal(quoteNode.path("09. change").asText());

            // Stripping "%" for clean output
            String changePercentRaw = quoteNode.path("10. change percent").asText();
            BigDecimal changePercent = new BigDecimal(changePercentRaw.replace("%", ""));

            // Fetch existing record or create a new one
            StockIndex stock = stockRepository.findByTicker(ticker)
                    .orElse(new StockIndex());

            // Setting the fields with their variables
            stock.setTicker(ticker);
            // Placeholder for now
            stock.setName(ticker);
            stock.setPrice(price);
            stock.setChange(change);
            stock.setChangePercent(changePercent);
            stock.setLastUpdated(LocalDateTime.now());

            // Save to the repository for each of the stocks
            stockRepository.save(stock);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch/parse stock data for: " + ticker, e);
        }
    }

    // More stock methods to interpret changes
    private StockDTO toDTO(StockIndex s) {
        return new StockDTO(s.getTicker(), s.getName(), s.getPrice(),
                s.getChange(), s.getChangePercent(), s.getLastUpdated());
    }

}
