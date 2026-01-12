package com.diepnn.cryptotradingsimulation.service;

import com.diepnn.cryptotradingsimulation.client.binance.BinanceClient;
import com.diepnn.cryptotradingsimulation.client.huobi.HuobiClient;
import com.diepnn.cryptotradingsimulation.domain.entity.AggregatedPrice;
import com.diepnn.cryptotradingsimulation.domain.entity.TradingPair;
import com.diepnn.cryptotradingsimulation.dto.CommonPriceDTO;
import com.diepnn.cryptotradingsimulation.dto.response.AggregatedPriceDTO;
import com.diepnn.cryptotradingsimulation.mapper.AggregatedPriceMapper;
import com.diepnn.cryptotradingsimulation.repository.AggregatedPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AggregatedPriceService {
    private final BinanceClient binanceClient;
    private final HuobiClient huobiClient;
    private final TradingPairService tradingPairService;
    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final AggregatedPriceMapper map;

    /**
     * Refreshes and persists the latest aggregated prices for all supported trading pairs.
     */
    @Transactional
    public void refreshAggregatedPrices() {
        List<TradingPair> supportedTradingPairs = tradingPairService.getSupportedTradingPairs();
        Set<String> supportedPairs = supportedTradingPairs.stream().map(TradingPair::getSymbol).collect(Collectors.toSet());
        Map<String, CommonPriceDTO> fetchedBinance = binanceClient.getSupportedPrices(supportedPairs);
        Map<String, CommonPriceDTO> fetchedHuobi = huobiClient.getSupportedPrices(supportedPairs);

        Map<String, AggregatedPrice> existingAggregatedPrice = aggregatedPriceRepository
                .findAllByTradingPairIn(supportedPairs)
                .stream()
                .collect(Collectors.toMap(AggregatedPrice::getTradingPair, ap -> ap));

        List<AggregatedPrice> toSave = new ArrayList<>(supportedTradingPairs.size());

        for (TradingPair tradingPair : supportedTradingPairs) {
            CommonPriceDTO binance = fetchedBinance.get(tradingPair.getSymbol());
            CommonPriceDTO huobi = fetchedHuobi.get(tradingPair.getSymbol());
            AggregatedPrice ap = existingAggregatedPrice.getOrDefault(
                    tradingPair.getSymbol(),
                    AggregatedPrice.create(tradingPair)
            );

            applyBestPrice(ap, binance, huobi);

            toSave.add(ap);
        }

        aggregatedPriceRepository.saveAll(toSave);
    }

    /**
     * Applies the best aggregated market prices to the given {@link AggregatedPrice}
     * using the latest data from Binance and Huobi.
     *
     * <p>Aggregation rules:</p>
     * <ul>
     *   <li><b>Best Bid (SELL price)</b> – the highest {@code bidPrice} across Binance and Huobi.</li>
     *   <li><b>Best Ask (BUY price)</b> – the lowest {@code askPrice} across Binance and Huobi.</li>
     * </ul>
     *
     * <p>If one exchange does not provide pricing information for a trading pair, the available source will be used.
     * If both sources are unavailable, the aggregated price remains unchanged.</p>
     *
     * @param aggregated the aggregated price entity to update (must not be {@code null})
     * @param binance the latest price snapshot from Binance, or {@code null} if unavailable
     * @param huobi the latest price snapshot from Huobi, or {@code null} if unavailable
     */
    private void applyBestPrice(AggregatedPrice aggregated, CommonPriceDTO binance, CommonPriceDTO huobi) {
        CommonPriceDTO bestBid = Stream.of(binance, huobi)
                                       .filter(Objects::nonNull)
                                       .max(Comparator.comparing(CommonPriceDTO::bidPrice))
                                       .orElse(null);

        if (bestBid != null) {
            aggregated.setBidPrice(bestBid.bidPrice());
            aggregated.setBidPriceSource(bestBid.source());
        }

        CommonPriceDTO bestAsk = Stream.of(binance, huobi)
                                       .filter(Objects::nonNull)
                                       .min(Comparator.comparing(CommonPriceDTO::askPrice))
                                       .orElse(null);

        if (bestAsk != null) {
            aggregated.setAskPrice(bestAsk.askPrice());
            aggregated.setAskPriceSource(bestAsk.source());
        }

        aggregated.setUpdatedAt(LocalDateTime.now());
    }

    public List<AggregatedPriceDTO> getAll() {
        return aggregatedPriceRepository.findAll().stream().map(map::toDTO).toList();
    }
}
