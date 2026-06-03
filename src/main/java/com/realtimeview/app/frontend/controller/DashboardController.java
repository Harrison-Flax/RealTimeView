package com.realtimeview.app.frontend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.frontend.model.StockViewModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DashboardController {
    // @FXML injects UI elements from dashboard.fxml
    @FXML
    private TableView<StockViewModel> stockTable;
    @FXML
    private TableColumn<StockViewModel, String> tickerCol;
    @FXML
    private TableColumn<StockViewModel, String> nameCol;
    @FXML
    private TableColumn<StockViewModel, BigDecimal> priceCol;
    @FXML
    private TableColumn<StockViewModel, BigDecimal> changeCol;
    @FXML
    private TableColumn<StockViewModel, BigDecimal> changePercentCol;
    @FXML
    private Label statusLabel;
    @FXML
    private Label lastUpdatedLabel;

    // Handles rendering
    private final ObservableList<StockViewModel> stockData = FXCollections.observableArrayList();

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Frontend runs from here
    private static final String BASE_URL = "http://localhost:8080";

    @FXML
    public void initialize() {
        setupTableColumns();
        stockTable.setItems(stockData);
        loadStocks();
    }

    // Each column needs to be assigned to each respective property on StockViewModel
    private void setupTableColumns() {
        tickerCol.setCellValueFactory(cell -> cell.getValue().tickerProperty());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        priceCol.setCellValueFactory(cell -> cell.getValue().priceProperty());
        changeCol.setCellValueFactory(cell -> cell.getValue().changeProperty());
        changePercentCol.setCellValueFactory(cell -> cell.getValue().changePercentProperty());
    }

    // Loading from Spring Boot backend
    private void loadStocks() {
        statusLabel.setText("Loading...");

        // HTTP calls are made in a background Thread
        Thread.ofVirtual().start(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/api/stocks"))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request, HttpResponse.BodyHandlers.ofString()
                );

                JsonNode stocks = objectMapper.readTree(response.body());

                // UI updates
                Platform.runLater(() -> {
                    stockData.clear();
                    for (JsonNode stock : stocks) {
                        stockData.add(new StockViewModel(
                                stock.path("ticker").asText(),
                                stock.path("name").asText(),
                                new BigDecimal(stock.path("price").asText("0")),
                                new BigDecimal(stock.path("change").asText("0")),
                                new BigDecimal(stock.path("changePercent").asText("0"))
                        ));
                    }
                    String time = LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                    lastUpdatedLabel.setText("Last updated: " + time);
                    statusLabel.setText(stocks.size() + " stocks loaded");
                });

            } catch (Exception e) {
                Platform.runLater(() ->
                        statusLabel.setText("Error loading stocks: " + e.getMessage())
                );
            }
        });
    }

    // Button Handlers
    // For use with onAction=# in FXML
    @FXML
    private void showStocks() {
        // Call on the function to load in stocks from backend
        loadStocks();
    }

    @FXML
    private void showCrypto() {
        // Call on the function to load in crypto from backend

    }

    @FXML
    private void showNews() {
        // Call on the function to load in news from backend

    }

    @FXML
    private void refreshData() {
        loadStocks();
    }
}
