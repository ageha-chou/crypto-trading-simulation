package com.diepnn.cryptotradingsimulation.client.binance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BinanceDTO(
        String symbol,
        BigDecimal bidPrice,
        BigDecimal askPrice
) {
}
