package com.realtimeview.app.repository;

import com.realtimeview.app.model.StockIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockIndex, Long> {
    Optional<StockIndex> findByName(String name);
}