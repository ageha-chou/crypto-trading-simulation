DROP TABLE IF EXISTS trading_pair;
DROP TABLE IF EXISTS aggregated_price;

CREATE TABLE trading_pair (
    symbol VARCHAR(20) NOT NULL,
    base_currency VARCHAR(10) NOT NULL,
    quote_currency VARCHAR(10) NOT NULL
);

CREATE TABLE aggregated_price (
    trading_pair VARCHAR(20) NOT NULL,
    bid_price DECIMAL(19, 8) NOT NULL,
    ask_price DECIMAL(19, 8) NOT NULL,
    bid_price_source VARCHAR(50) NOT NULL,
    ask_price_source VARCHAR(50) NOT NULL,
    updated_at TIMESTAMP NOT NULL
);