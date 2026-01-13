## Overview
This project implements a simplified trading service that aggregates prices
from multiple exchanges and allows users to execute BUY/SELL trades based on
the best available price. The system ensures data consistency using
transactional boundaries and optimistic locking.

## Assumptions
- User authentication and authorization are handled externally.
- The authenticated user ID is provided via request header (`X-USER-ID`).
- Only support Ethereum - ETHUSDT and Bitcoin - BTCUSDT pairs of crypto trading.
- Prices are aggregated periodically and stored as the latest snapshot per trading pair.

## Architecture & Design
- Spring Boot REST API
- H2 in-memory database (plain H2 mode)
- Spring Data JPA for persistence
- Optimistic locking (`@Version`) for wallet balance updates
- DTOs are used for API responses (entities are not exposed)

## Data Model
- `users`: User identity reference (authentication is handled externally)
- `trading_pair`: Supported trading pairs with base and quote currencies
- `aggregated_price`: Latest best bid/ask price per trading pair
- `wallet_balance`: User balances per currency (optimistic locking enabled)
- `trading_transaction`: Immutable trade history

## Trade Execution Flow
1. Load latest aggregated price for the trading pair
2. Reject trade if the price is stale (older than 30 seconds)
3. Determine execution price (ASK for BUY, BID for SELL)
4. Calculate quote amount = baseAmount × executedPrice
5. Validate user balances
6. Update wallet balances atomically
7. Persist trade transaction

## API Endpoints
### Execute Trade
POST /api/v1/trades  
Header: `X-USER-ID`

Request body:
```json
{
  "symbol": "ETHUSDT",
  "transType": "BUY",
  "baseAmount": "0.5"
}
```

### Get Trading History
GET /api/v1/trades  
Header: `X-USER-ID`

### Get Wallet Balances
GET /api/v1/wallet-balances  
Header: `X-USER-ID`

### Get Aggregated Prices
GET /api/v1/aggregated-prices