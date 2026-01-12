package com.diepnn.cryptotradingsimulation.client.huobi.dto;

import java.util.List;

public record HuobiResponse(
        List<HuobiDTO> data
) {
}
