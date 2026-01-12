package com.diepnn.cryptotradingsimulation.dto;

import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;

import java.math.BigDecimal;

public record CommonPriceDTO(
        String symbol,
        BigDecimal bidPrice,
        BigDecimal askPrice,
        PriceSource source
) {
}
