package com.diepnn.cryptotradingsimulation.schedule;

import com.diepnn.cryptotradingsimulation.service.AggregatedPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Refresh aggregated prices every {@systemProperty scheduler.price.refresh-ms} seconds, default is 10 seconds.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AggregatedPriceRefreshScheduler {
    private final AggregatedPriceService aggregatedPriceService;

    @Scheduled(fixedDelayString = "${scheduler.price.refresh-ms:10000}")
    public void refreshPrices() {
        log.info("Refreshing aggregated prices...");
        try {
            aggregatedPriceService.refreshAggregatedPrices();
        } catch (Exception e) {
            log.error("Error occurred while refreshing aggregated prices", e);
        }
    }
}
