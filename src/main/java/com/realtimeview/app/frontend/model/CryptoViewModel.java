package com.realtimeview.app.frontend.model;

import javafx.beans.property.*;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;

import java.math.BigDecimal;

public class CryptoViewModel {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> priceUsd = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> marketCap = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> changePercent24h = new SimpleObjectProperty<>();

    // Constructor declaration
    public CryptoViewModel(Long id, String name, BigDecimal priceUsd,
                           BigDecimal marketCap, BigDecimal changePercent24h) {
        this.id.set(id);
        this.name.set(name);
        this.priceUsd.set(priceUsd);
        this.marketCap.set(marketCap);
        this.changePercent24h.set(changePercent24h);
    }

    // Getters for properties
    public LongProperty idProperty() { return id; }
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<BigDecimal> priceUsdProperty() { return priceUsd; }
    public ObjectProperty<BigDecimal> marketCapProperty() { return marketCap; }
    public ObjectProperty<BigDecimal> changePercent24hProperty() { return changePercent24h; }

    // Getters for data types
    public Long getId() { return id.get(); }
    public String getName() { return name.get(); }
    public BigDecimal getPriceUsd() { return priceUsd.get(); }
    public BigDecimal getMarketCap() { return marketCap.get(); }
    public BigDecimal getChangePercent24h() { return changePercent24h.get(); }
}
