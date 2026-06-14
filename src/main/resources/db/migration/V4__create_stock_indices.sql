CREATE TABLE stock_indices (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ticker VARCHAR(16) NOT NULL UNIQUE,
    name VARCHAR(256) NOT NULL,
    price NUMERIC(20, 4),
    change NUMERIC(20, 4),
    change_percent NUMERIC(10, 4),
    last_updated TIMESTAMP
);