INSERT INTO trading_pair (symbol, base_currency, quote_currency) VALUES
('ETHUSDT', 'ETH', 'USDT'),
('BTCUSDT', 'BTC', 'USDT');

INSERT INTO users (username, name) VALUES ('ageha', 'Ageha');

INSERT INTO wallet_balance (user_id, currency, balance, version)
VALUES (1, 'USDT', 50000, 1);