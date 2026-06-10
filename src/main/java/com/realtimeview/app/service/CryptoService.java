package com.realtimeview.app.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.dto.CryptoDTO;
import com.realtimeview.app.model.CryptoAsset;
import com.realtimeview.app.model.StockIndex;
import com.realtimeview.app.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CryptoService {
    private final CryptoRepository cryptoRepository;
    // RestClient
    private final RestClient restClient;
    // Jackson ObjectMapper
    private final ObjectMapper objectMapper;

    // Declaring the apiKey value here
    @Value("${coinmarketcap.api.key}")
    private String apiKey;

    // Initializing all the variables in the constructor
    public CryptoService(CryptoRepository cryptoRepository, ObjectMapper objectMapper) {
        this.cryptoRepository = cryptoRepository;
        this.restClient = RestClient.create();
        this.objectMapper = objectMapper;
    }

    // Fetch all crypto assets from DB and convert to DTOs
    public List<CryptoDTO> getAllCryptos() {
        return cryptoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // Fetch single crypto asset by ID
    public CryptoDTO getById(Long id) {
        return cryptoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Crypto asset not found: " + id));
    }

    // External API call here
    // Parameter is symbol for each listing
    public void fetchAndSaveFromAPI(String symbol) {
        // Call crypto API, map response to CryptoAsset, save via repository

        // Fetch the url with the latest quotes
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?symbol=" + symbol;

        // RestClient GET request
        String result = restClient.get()
                .uri(url)
                // Need to have headers here for CoinMarketCap specifically for the request
                .header("X-CMC_PRO_API_KEY", apiKey)
                .header("Accept", "application/json")
                .retrieve()
                .body(String.class);

        try {
            // Parsing the JSON string
            JsonNode rootNode = objectMapper.readTree(result);

            // Need to go through the nested structure
            JsonNode coinNode = rootNode.path("data").path(symbol);
            JsonNode usdNode = coinNode.path("quote").path("USD");

            // Extracting each of the fields separately
            String name = coinNode.path("name").asText();
            BigDecimal price = new BigDecimal(usdNode.path("price").asText());
            BigDecimal marketCap = new BigDecimal(usdNode.path("market_cap").asText());
            BigDecimal volume24h = new BigDecimal(usdNode.path("volume_24h").asText());
            BigDecimal changePercent24h = new BigDecimal(usdNode.path("percent_change_24h").asText());

            // Fetch existing record or create a new one
            CryptoAsset crypto = cryptoRepository.findBySymbol(symbol)
                    .orElse(new CryptoAsset());

            // Setting the fields with their variables
            crypto.setSymbol(symbol);
            crypto.setName(name);
            crypto.setPriceUsd(price);
            crypto.setMarketCap(marketCap);
            crypto.setVolume24h(volume24h);
            crypto.setChangePercent24h(changePercent24h);
            crypto.setLastUpdated(LocalDateTime.now());

            // Save to the repository for each of the crypto listings
            cryptoRepository.save(crypto);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch/parse crypto data for: " + symbol, e);
        }
    }

    // More crypto methods to interpret changes
    private CryptoDTO toDTO(CryptoAsset c) {
        return new CryptoDTO(c.getId(), c.getName(), c.getSymbol(),
                c.getPriceUsd(), c.getMarketCap(),
                c.getChangePercent24h(), c.getLastUpdated());
    }
}