package com.realtimeview.app.frontend.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigDecimal;

// Holds properties for the data types
public class StockViewModel {
    private final StringProperty ticker = new SimpleStringProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final ObjectProperty<BigDecimal> price = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> change = new SimpleObjectProperty<>();
    private final ObjectProperty<BigDecimal> changePercent = new SimpleObjectProperty<>();

    // Constructor declaration
    public StockViewModel(String ticker, String name, BigDecimal price,
                          BigDecimal change, BigDecimal changePercent) {
        this.ticker.set(ticker);
        this.name.set(name);
        this.price.set(price);
        this.change.set(change);
        this.changePercent.set(changePercent);
    }

    // Getters for properties
    public StringProperty tickerProperty() { return ticker; }
    public StringProperty nameProperty() { return name; }
    public ObjectProperty<BigDecimal> priceProperty() { return price; }
    public ObjectProperty<BigDecimal> changeProperty() { return change; }
    public ObjectProperty<BigDecimal> changePercentProperty() { return changePercent; }

    // Getters for data types
    public String getTicker() { return ticker.get(); }
    public String getName() { return name.get(); }
    public BigDecimal getPrice() { return price.get(); }
    public BigDecimal getChange() { return change.get(); }
    public BigDecimal getChangePercent() { return changePercent.get(); }
}
