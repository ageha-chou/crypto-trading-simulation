package com.diepnn.cryptotradingsimulation.client.huobi;

import com.diepnn.cryptotradingsimulation.client.huobi.dto.HuobiDTO;
import com.diepnn.cryptotradingsimulation.client.huobi.dto.HuobiResponse;
import com.diepnn.cryptotradingsimulation.dto.CommonPriceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class HuobiClient {
    private final RestTemplate restTemplate;
    private final HuobiPriceMapper mapper;
    private final HuobiProperties properties;

    public Map<String, CommonPriceDTO> getSupportedPrices(Set<String> supportedSymbols) {
        String url = properties.getUrl();

        try {
            HuobiResponse response = restTemplate.getForObject(url, HuobiResponse.class);

            if (response == null) {
                log.warn("Huobi returned null response. url={}", url);
                return Map.of();
            }

            List<HuobiDTO> data = response.data();

            if (data == null || data.isEmpty()) {
                log.warn("Huobi returned empty response. url={}", url);
                return Map.of();
            }

            return data.stream()
                       .map(mapper::toCommon)
                       .filter(p -> p != null && supportedSymbols.contains(p.symbol()))
                       .collect(Collectors.toMap(CommonPriceDTO::symbol, p -> p));

        } catch (RestClientException ex) {
            log.error("Failed to fetch Huobi prices. url={}. error={}", url, ex.getMessage(), ex);
            return Map.of();
        } catch (Exception ex) {
            log.error("Unexpected error when mapping Huobi prices. url={}", url, ex);
            return Map.of();
        }
    }
}
