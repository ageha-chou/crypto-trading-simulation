package com.diepnn.cryptotradingsimulation.dto.response;

import java.math.BigDecimal;
import java.time.Instant;

public record WalletBalanceDTO(
        String currency,
        BigDecimal balance,
        Instant updatedAt
) {
}
