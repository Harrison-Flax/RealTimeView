CREATE TABLE crypto_assets (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    symbol VARCHAR(16) NOT NULL UNIQUE,
    name VARCHAR(64) NOT NULL,
    price_usd NUMERIC(20, 8),
    market_cap NUMERIC(30, 2),
    volume_24h NUMERIC(30, 2),
    change_percent_24h NUMERIC(10, 4),
    last_updated TIMESTAMP
);