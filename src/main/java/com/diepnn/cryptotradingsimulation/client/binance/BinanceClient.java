package com.diepnn.cryptotradingsimulation.client.binance;

import com.diepnn.cryptotradingsimulation.client.binance.dto.BinanceDTO;
import com.diepnn.cryptotradingsimulation.dto.CommonPriceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class BinanceClient {
    private final RestTemplate restTemplate;
    private final BinancePriceMapper mapper;
    private final BinanceProperties properties;

    public Map<String, CommonPriceDTO> getSupportedPrices(Set<String> supportedSymbols) {
        String url = properties.getUrl();

        try {
            BinanceDTO[] arr = restTemplate.getForObject(url, BinanceDTO[].class);

            if (arr == null || arr.length == 0) {
                log.warn("Binance returned empty response. url={}", url);
                return Map.of();
            }

            return Arrays.stream(arr)
                         .map(mapper::toCommon)
                         .filter(p -> p != null && supportedSymbols.contains(p.symbol()))
                         .collect(Collectors.toMap(CommonPriceDTO::symbol, p -> p));

        } catch (RestClientException ex) {
            log.error("Failed to fetch Binance prices. url={}. error={}", url, ex.getMessage(), ex);
            return Map.of();
        } catch (Exception ex) {
            log.error("Unexpected error when mapping Binance prices. url={}", url, ex);
            return Map.of();
        }
    }
}
