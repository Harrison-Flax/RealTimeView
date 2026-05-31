package com.realtimeview.app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.dto.CryptoDTO;
import com.realtimeview.app.model.CryptoAsset;
import com.realtimeview.app.repository.CryptoRepository;
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

// Same general logic as rest of tests
// For backend classes at least
@ExtendWith(MockitoExtension.class)
public class CryptoServiceTest {
    @Mock
    private CryptoRepository cryptoRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private CryptoService cryptoService;

    // 1) getAllCrypto should return a list
    @Test
    @DisplayName("Return Test: a list is the result")
    public void getAllCryptoIsAList() {
        // ARRANGE
        CryptoAsset crypto1 = new CryptoAsset();
        CryptoAsset crypto2 = new CryptoAsset();

        crypto1.setSymbol("BTC");
        crypto2.setSymbol("ETH");

        // Need a list
        List<CryptoAsset> coins = List.of(crypto1, crypto2);

        // When findAll is called, return coins
        when(cryptoRepository.findAll()).thenReturn(coins);

        // Call the function for the return result
        // ACT
        List<CryptoDTO> result = cryptoService.getAllCryptos();

        // Prove that the size is 2
        // ASSERT
        assertEquals(2, result.size());

        // Prove that the first item's ticker is BTC
        assertEquals("BTC", result.get(0).symbol());
    }

    // 2) getById should throw an exception when an ID isn't found
    @Test
    @DisplayName("Exception Test: when an ID isn't found")
    public void getBySymbolIsAnExceptionWithoutTicker() {
        // ARRANGE
        // Mock repository returns an empty Optional
        // When the function is called
        // 99L = 99 long (since Id is in long)
        when(cryptoRepository.findById(99L)).thenReturn(Optional.empty());

        // NO ACT HERE

        // ASSERT
        assertThrows(RuntimeException.class, () -> cryptoService.getById(99L));
    }

    // 3) getById returns the correct DTO when found
    @Test
    @DisplayName("Return Test: a DTO is found when existing")
    public void getBySymbolReturnDTO() {
        // ARRANGE
        // Creating a crypto with known values
        CryptoAsset knownCrypto = new CryptoAsset();

        // Setting the known values
        knownCrypto.setSymbol("ETH");
        knownCrypto.setName("Ethereum");
        knownCrypto.setPriceUsd(new BigDecimal("1999.17"));

        // Mock repository only returns it wrapped in Optional.of()
        // Only when findById("Any Long") is called
        when(cryptoRepository.findById(1L)).thenReturn(Optional.of(knownCrypto));

        // ACT
        // Call on the cryptoService function
        CryptoDTO result = cryptoService.getById(1L);

        // ASSERT
        // Check if the attributes line up with the coin
        assertEquals("ETH", result.symbol());
        assertEquals("Ethereum", result.name());
        assertEquals(new BigDecimal("1999.17"), result.priceUsd());
    }
}
