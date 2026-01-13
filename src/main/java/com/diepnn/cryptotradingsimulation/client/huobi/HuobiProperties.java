package com.diepnn.cryptotradingsimulation.client.huobi;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.huobi")
@RequiredArgsConstructor
@Getter
public class HuobiProperties {
    private final String url;
}
