package com.realtimeview.app.frontend.model;

import javafx.beans.property.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CryptoViewModel {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty symbol = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> priceUsd = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> marketCap = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> changePercent24h = new SimpleObjectProperty<>();

    // Constructor declaration
    public CryptoViewModel(String name, String symbol,
                           BigDecimal priceUsd, BigDecimal marketCap, BigDecimal changePercent24h) {
        this.name.set(name);
        this.symbol.set(symbol);
        this.priceUsd.set(priceUsd);
        this.marketCap.set(marketCap);
        this.changePercent24h.set(changePercent24h);
    }

    // Getters for properties
    public StringProperty nameProperty() { return name; }
    public StringProperty symbolProperty() { return symbol; }
    public ObjectProperty<BigDecimal> priceUsdProperty() { return priceUsd; }
    public ObjectProperty<BigDecimal> marketCapProperty() { return marketCap; }
    public ObjectProperty<BigDecimal> changePercent24hProperty() { return changePercent24h; }

    // Getters for data types
    public String getName() { return name.get(); }
    public String getSymbol() { return symbol.get(); }
    public BigDecimal getPriceUsd() { return priceUsd.get(); }
    public BigDecimal getMarketCap() { return marketCap.get(); }
    public BigDecimal getChangePercent24h() { return changePercent24h.get(); }
}
