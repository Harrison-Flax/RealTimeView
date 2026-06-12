package com.realtimeview.app.frontend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realtimeview.app.frontend.model.CryptoViewModel;
import com.realtimeview.app.frontend.model.NewsViewModel;
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

    // Stock table
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

    // Crypto table
    @FXML
    private TableView<CryptoViewModel> cryptoTable;
    @FXML
    private TableColumn<CryptoViewModel, String> nameCryptoCol;
    @FXML
    private TableColumn<CryptoViewModel, String> symbolCryptoCol;
    @FXML
    private TableColumn<CryptoViewModel, BigDecimal> priceUsdCryptoCol;
    @FXML
    private TableColumn<CryptoViewModel, BigDecimal> marketCapCryptoCol;
    @FXML
    private TableColumn<CryptoViewModel, BigDecimal> changePercent24hCryptoCol;

    // News table
    @FXML
    private TableView<NewsViewModel> newsTable;
    @FXML
    private TableColumn<NewsViewModel, String> headlineCol;
    @FXML
    private TableColumn<NewsViewModel, String> summaryCol;
    @FXML
    private TableColumn<NewsViewModel, String> sourceCol;
    @FXML
    private TableColumn<NewsViewModel, String> categoryCol;
    @FXML
    private TableColumn<NewsViewModel, String> publishedAtCol;

    @FXML
    private Label statusLabel;
    @FXML
    private Label lastUpdatedLabel;

    // Handles rendering
    private final ObservableList<StockViewModel> stockData = FXCollections.observableArrayList();
    private final ObservableList<CryptoViewModel> cryptoData = FXCollections.observableArrayList();
    private final ObservableList<NewsViewModel> newsData = FXCollections.observableArrayList();
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Frontend runs from here
    private static final String BASE_URL = "http://localhost:8080";

    @FXML
    public void initialize() {
        setupTableColumns();
        stockTable.setItems(stockData);
        cryptoTable.setItems(cryptoData);
        newsTable.setItems(newsData);

        // Stocks is the default on startup
        showOnlyTable("stocks");
        loadStocks();
    }

    // Each column needs to be assigned to each respective property according to their model
    // Should have their info in a table with columns so its neatly organized
    // And also easy to navigate
    private void setupTableColumns() {
        // Stocks
        tickerCol.setCellValueFactory(cell -> cell.getValue().tickerProperty());
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        priceCol.setCellValueFactory(cell -> cell.getValue().priceProperty());
        changeCol.setCellValueFactory(cell -> cell.getValue().changeProperty());
        changePercentCol.setCellValueFactory(cell -> cell.getValue().changePercentProperty());

        // Crypto
        nameCryptoCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        symbolCryptoCol.setCellValueFactory(cell -> cell.getValue().symbolProperty());
        priceUsdCryptoCol.setCellValueFactory(cell -> cell.getValue().priceUsdProperty());
        marketCapCryptoCol.setCellValueFactory(cell -> cell.getValue().marketCapProperty());
        changePercent24hCryptoCol.setCellValueFactory(cell -> cell.getValue().changePercent24hProperty());

        // News
        headlineCol.setCellValueFactory(cell -> cell.getValue().headlineProperty());
        summaryCol.setCellValueFactory(cell -> cell.getValue().summaryProperty());
        sourceCol.setCellValueFactory(cell -> cell.getValue().sourceProperty());
        categoryCol.setCellValueFactory(cell -> cell.getValue().categoryProperty());
        publishedAtCol.setCellValueFactory(cell -> cell.getValue().publishedAtProperty());
    }

    // A function to determine which table is visible to user
    // Views should be able to switch instead of only viewing everything at once
    private void showOnlyTable(String view) {
        // Stocks, crypto, and news tables are all set to the same format
        // So the table is aligned within the page
        stockTable.setVisible(false);
        stockTable.setManaged(false);
        cryptoTable.setVisible(false);
        cryptoTable.setManaged(false);
        newsTable.setVisible(false);
        newsTable.setManaged(false);

        // Views should be able to switch instead of only viewing everything at once
        // And aligned within the page
        switch (view) {
            case "stocks" -> {
                stockTable.setVisible(true); stockTable.setManaged(true);
            }
            case "crypto" -> {
                cryptoTable.setVisible(true); cryptoTable.setManaged(true);
            }
            case "news" -> {
                newsTable.setVisible(true); newsTable.setManaged(true);
            }
        }
    }

    // Loading from the models and Spring Boot backend
    private void loadStocks() {
        statusLabel.setText("Loading Stocks...");

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
                    updateTimestamp();
                    statusLabel.setText(stocks.size() + " stocks loaded");
                });

            } catch (Exception e) {
                Platform.runLater(() ->
                        statusLabel.setText("Error loading stocks: " + e.getMessage())
                );
            }
        });
    }

    @FXML
    private void loadCrypto() {
        // Logic to load in crypto from backend
        statusLabel.setText("Loading Crypto...");

        // HTTP calls are made in a background Thread
        Thread.ofVirtual().start(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/api/crypto"))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request, HttpResponse.BodyHandlers.ofString()
                );

                JsonNode coins = objectMapper.readTree(response.body());

                // UI updates
                Platform.runLater(() -> {
                    cryptoData.clear();
                    for (JsonNode coin : coins) {
                        cryptoData.add(new CryptoViewModel(
                                coin.path("name").asText(),
                                coin.path("symbol").asText(),
                                new BigDecimal(coin.path("priceUsd").asText("0")),
                                new BigDecimal(coin.path("marketCap").asText("0")),
                                new BigDecimal(coin.path("changePercent24h").asText("0"))
                        ));
                    }
                    updateTimestamp();
                    statusLabel.setText(coins.size() + " coins loaded");
                });

            } catch (Exception e) {
                Platform.runLater(() ->
                        statusLabel.setText("Error loading coins: " + e.getMessage())
                );
            }
        });
    }

    @FXML
    private void loadNews() {
        // Logic to load in news from backend
        statusLabel.setText("Loading news...");

        Thread.ofVirtual().start(() -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(BASE_URL + "/api/news"))
                        .GET()
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request, HttpResponse.BodyHandlers.ofString()
                );

                JsonNode articles = objectMapper.readTree(response.body());

                Platform.runLater(() -> {
                    newsData.clear();
                    for (JsonNode article : articles) {
                        newsData.add(new NewsViewModel(
                                article.path("headline").asText(),
                                article.path("summary").asText(),
                                article.path("source").asText(),
                                article.path("url").asText(),
                                article.path("category").asText(),
                                article.path("publishedAt").asText()
                        ));
                    }
                    updateTimestamp();
                    statusLabel.setText(articles.size() + " articles loaded");
                });
            } catch (Exception e) {
                Platform.runLater(() ->
                        statusLabel.setText("Error loading news: " + e.getMessage())
                );
            }
        });
    }

    // Update the dates from one function
    public void updateTimestamp() {
        String time = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        lastUpdatedLabel.setText("Last updated: " + time);
    }

    // Button Handlers
    // For use with onAction=# in FXML
    @FXML
    private void showStocks() {
        showOnlyTable("stocks");
        loadStocks();
    }

    @FXML
    private void showCrypto() {
        showOnlyTable("crypto");
        loadCrypto();
    }

    @FXML
    private void showNews() {
        showOnlyTable("news");
        loadNews();
    }

    @FXML
    private void refreshData() {
        // Data is refreshed from visible table
        if (stockTable.isVisible()) loadStocks();
        else if (cryptoTable.isVisible()) loadCrypto();
        else if (newsTable.isVisible()) loadNews();
    }
}
