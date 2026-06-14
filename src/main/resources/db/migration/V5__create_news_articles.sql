CREATE TABLE news_articles (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    headline VARCHAR(255) NOT NULL,
    summary TEXT,
    source VARCHAR(255),
    url VARCHAR(1024),
    category VARCHAR(64),
    published_at TIMESTAMP,
    saved_by_user BOOLEAN NOT NULL DEFAULT false
);