package com.diepnn.cryptotradingsimulation.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record AggregatedPriceDTO(
        String symbol,
        BigDecimal bid,
        BigDecimal ask,
        String bidSource,
        String askSource,
        OffsetDateTime updatedAt
) {
}
