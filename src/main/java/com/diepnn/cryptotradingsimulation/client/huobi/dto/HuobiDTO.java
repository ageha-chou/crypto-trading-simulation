package com.diepnn.cryptotradingsimulation.client.huobi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HuobiDTO(
    String symbol,
    BigDecimal bid,
    BigDecimal ask
) {
}
