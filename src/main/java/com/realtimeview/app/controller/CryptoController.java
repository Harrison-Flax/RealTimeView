package com.realtimeview.app.controller;

import com.realtimeview.app.dto.CryptoDTO;
import com.realtimeview.app.service.CryptoService;
import com.realtimeview.app.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    public CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping
    public ResponseEntity<List<CryptoDTO>> getAllCryptos() {
        return ResponseEntity.ok(cryptoService.getAllCryptos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CryptoDTO> getCryptoById(@PathVariable Long id) {
        return ResponseEntity.ok(cryptoService.getCryptoById(id));
    }

    @PostMapping("/refresh/{id}")
    public ResponseEntity<Void> refreshCrypto(@PathVariable String id) {
        cryptoService.fetchAndSaveFromAPI(id);
        return ResponseEntity.noContent().build();
    }

}
