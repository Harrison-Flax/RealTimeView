package com.realtimeview.app.service;

import com.realtimeview.app.dto.CryptoDTO;
import com.realtimeview.app.model.CryptoAsset;
import com.realtimeview.app.repository.CryptoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    public CryptoService(CryptoRepository cryptoRepository) {
        this.cryptoRepository = cryptoRepository;
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
    public void fetchAndSaveFromAPI() {
        // Call crypto API, map response to CryptoAsset, save via repository

    }

    // More crypto methods to interpret changes
    private CryptoDTO toDTO(CryptoAsset c) {
        return new CryptoDTO(c.getId(), c.getName(), c.getSymbol(),
                c.getPriceUsd(), c.getMarketCapUsd(), c.getVolume24hUsd(), c.getPercentChange24h(), c.getLastUpdated());
    }

}
