package com.diepnn.cryptotradingsimulation.dto.response;

import com.diepnn.cryptotradingsimulation.domain.enums.TransType;

import java.math.BigDecimal;
import java.time.Instant;

public record TradeResult(
        Long tradeId,
        String tradingPair,
        TransType transType,

        BigDecimal baseAmount,
        BigDecimal executedPrice,
        BigDecimal quoteAmount,
        String priceSource,

        Instant createdAt
) {
}
