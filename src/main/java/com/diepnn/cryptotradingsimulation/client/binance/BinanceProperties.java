package com.diepnn.cryptotradingsimulation.client.binance;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.binance")
@RequiredArgsConstructor
@Getter
public class BinanceProperties {
    private final String url;
}
