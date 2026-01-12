package com.diepnn.cryptotradingsimulation.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record AggregatedPriceDTO(
        String symbol,
        BigDecimal bid,
        BigDecimal ask,
        String bidSource,
        String askSource,
        Instant updatedAt
) {
}
