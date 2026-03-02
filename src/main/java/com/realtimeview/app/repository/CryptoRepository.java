package com.realtimeview.app.repository;

import com.realtimeview.app.model.CryptoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CryptoRepository extends JpaRepository<CryptoAsset, Long> {
    Optional<CryptoAsset> findBySymbol(String symbol);
}