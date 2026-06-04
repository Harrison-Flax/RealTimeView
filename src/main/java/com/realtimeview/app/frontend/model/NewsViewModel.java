package com.realtimeview.app.frontend.model;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class NewsViewModel {
    private final LongProperty id = new SimpleLongProperty();
    private final StringProperty headline = new SimpleStringProperty();
    private final StringProperty summary = new SimpleStringProperty();
    private final StringProperty source = new SimpleStringProperty();
    private final StringProperty url = new SimpleStringProperty();
    private final StringProperty category = new SimpleStringProperty();
    private final ObjectProperty<LocalDateTime> publishedAt = new SimpleObjectProperty<>();

    // Constructor declaration
    public NewsViewModel(Long id, String headline, String summary,
                         String source, String url, String category,
                         LocalDateTime publishedAt) {
        this.id.set(id);
        this.headline.set(headline);
        this.summary.set(summary);
        this.source.set(source);
        this.url.set(url);
        this.category.set(category);
        this.publishedAt.set(publishedAt);
    }

    // Getters for properties
    public LongProperty idProperty() { return id; }
    public StringProperty headlineProperty() { return headline; }
    public StringProperty summaryProperty() { return summary; }
    public StringProperty sourceProperty() { return source; }
    public StringProperty urlProperty() { return url; }
    public StringProperty categoryProperty() { return category; }
    public ObjectProperty<LocalDateTime> publishedAtProperty() { return publishedAt; }

    // Getters for data types
    public Long getId() { return id.get(); }
    public String getHeadline() { return headline.get(); }
    public String getSummary() { return summary.get(); }
    public String getSource() { return source.get(); }
    public String getUrl() { return url.get(); }
    public String getCategory() { return category.get(); }
    public LocalDateTime getPublishedAt() { return publishedAt.get(); }
}
