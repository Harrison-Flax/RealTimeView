package com.realtimeview.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.dto.StockDTO;
import com.realtimeview.app.model.StockIndex;
import com.realtimeview.app.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

// Need this annotation for Mockito and JUnit cases
@ExtendWith(MockitoExtension.class)
public class StockServiceTest {
    // Need mocks of the main class resources
    // No need to make them final here as just testing
    @Mock
    private StockRepository stockRepository;
    @Mock
    private ObjectMapper objectMapper;
    // The real class instantiation with injected mocks
    // Instead of purely a mock
    @InjectMocks
    private StockService stockService;

    // 1) getAllStocks should return a list
    @Test
    @DisplayName("Return Test: a list is the result")
    public void getAllStocksIsAList() {
        // Creating two stocks to test with for their tickers
        // ARRANGE
        StockIndex stock1 = new StockIndex();
        StockIndex stock2 = new StockIndex();

        stock1.setTicker("SPY");
        stock2.setTicker("QQQ");

        // Need a list
        List<StockIndex> stocks = List.of(stock1, stock2);

        // When findAll is called, return stocks
        when(stockRepository.findAll()).thenReturn(stocks);

        // Call the function for the return result
        // ACT
        List<StockDTO> result = stockService.getAllStocks();

        // Prove that the size is 2
        // ASSERT
        assertEquals(2, result.size());

        // Prove that the first item's ticker is SPY
        assertEquals("SPY", result.get(0).ticker());
    }

    // 2) getByTicker should throw an exception when a ticker isn't found
    @Test
    @DisplayName("Exception Test: when a ticker isn't found")
    public void getByTickerIsAnExceptionWithoutTicker() {
        // ARRANGE
        // Mock repository returns an empty Optional
        // When the function is called
        when(stockRepository.findByTicker("FAKE")).thenReturn(Optional.empty());

        // NO ACT HERE

        // ASSERT
        assertThrows(RuntimeException.class, () -> stockService.getByTicker("FAKE"));
    }

    // 3) getByTicker returns the correct DTO when found
    @Test
    @DisplayName("Return Test: a DTO is found when existing")
    public void getByTickerReturnDTO() {
        // ARRANGE
        // Creating a stock with known values
        StockIndex knownStock = new StockIndex();

        // Setting the known values
        knownStock.setTicker("AAPL");
        knownStock.setName("Apple Inc.");
        knownStock.setPrice(new BigDecimal("312.06"));

        // Mock repository returns it wrapped in Optional.of()
        // Only when findByTicker("Any Stock") is called
        when(stockRepository.findByTicker("AAPL")).thenReturn(Optional.of(knownStock));

        // ACT
        // Call on the stockService function
        StockDTO result = stockService.getByTicker("AAPL");

        // ASSERT
        // Check if the attributes line up with the stock
        assertEquals("AAPL", result.ticker());
        assertEquals("Apple Inc.", result.name());
        assertEquals(new BigDecimal("312.06"), result.price());
    }
}
