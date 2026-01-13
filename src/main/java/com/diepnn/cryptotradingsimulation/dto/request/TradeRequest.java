package com.diepnn.cryptotradingsimulation.dto.request;

import com.diepnn.cryptotradingsimulation.domain.enums.TransType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TradeRequest {
    @NotNull
    @NotBlank(message = "The field is required")
    private String symbol;

    @NotNull
    private TransType transType;

    @NotNull
    @Positive(message = "The field must be greater than 0")
    @Digits(integer = 19, fraction = 8, message = "Invalid amount")
    private BigDecimal baseAmount;
}
