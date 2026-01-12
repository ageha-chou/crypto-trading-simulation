package com.diepnn.cryptotradingsimulation.service;

import com.diepnn.cryptotradingsimulation.domain.entity.AggregatedPrice;
import com.diepnn.cryptotradingsimulation.domain.entity.TradingTransaction;
import com.diepnn.cryptotradingsimulation.domain.entity.WalletBalance;
import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;
import com.diepnn.cryptotradingsimulation.domain.enums.TransType;
import com.diepnn.cryptotradingsimulation.dto.request.TradeRequest;
import com.diepnn.cryptotradingsimulation.dto.response.TradeResult;
import com.diepnn.cryptotradingsimulation.exception.InvalidBalanceException;
import com.diepnn.cryptotradingsimulation.exception.NotSupportedSymbolException;
import com.diepnn.cryptotradingsimulation.exception.PriceStaleException;
import com.diepnn.cryptotradingsimulation.mapper.TradingTransactionMapper;
import com.diepnn.cryptotradingsimulation.repository.AggregatedPriceRepository;
import com.diepnn.cryptotradingsimulation.repository.TradingTransactionRepository;
import com.diepnn.cryptotradingsimulation.repository.WalletBalanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TradingTransactionService {
    private final AggregatedPriceRepository aggregatedPriceRepository;
    private final WalletBalanceRepository walletBalanceRepository;
    private final TradingTransactionRepository transactionRepository;
    private final TradingTransactionMapper mapper;

    /**
     * Executes a BUY or SELL trade for a given user and trading pair.
     *
     * <p>The trade is executed atomically within a single database transaction.
     * The method performs the following steps:</p>
     * <ol>
     *   <li>Loads the latest aggregated price for the requested trading pair.</li>
     *   <li>Rejects the trade if the price is stale (older than 30 seconds).</li>
     *   <li>Determines the execution price (ASK for BUY, BID for SELL).</li>
     *   <li>Calculates the quote amount as {@code baseAmount × executedPrice}.</li>
     *   <li>Loads and updates the user's wallet balances using optimistic locking.</li>
     *   <li>Persists an immutable trade transaction record.</li>
     * </ol>
     *
     * @param userId  the authenticated user's identifier
     * @param request the trade request containing trading pair, side, and base amount
     * @return a {@link TradeResult} containing the executed trade details
     *
     * @throws NotSupportedSymbolException if the trading pair is not supported
     * @throws PriceStaleException if the aggregated price is older than the allowed staleness window
     * @throws InvalidBalanceException if the user does not have sufficient balance to execute the trade
     */
    @Transactional
    public TradeResult trade(Long userId, TradeRequest request) {
        AggregatedPrice ap = aggregatedPriceRepository.findByTradingPair(request.getSymbol());
        if (ap == null) {
            throw new NotSupportedSymbolException("Does not support symbol " + request.getSymbol());
        }

        // Staleness check
        Instant now = Instant.now();
        if (ap.getUpdatedAt().isBefore(now.minusSeconds(30))) {
            throw new PriceStaleException("Price is stale. Please retry with the latest price.");
        }

        String base = ap.getTradingPairEntity().getBaseCurrency();
        String quote = ap.getTradingPairEntity().getQuoteCurrency();

        List<WalletBalance> balances = walletBalanceRepository.findAllByIdUserIdAndIdCurrencyIn(userId, List.of(base, quote));

        boolean isBuy = request.getTransType() == TransType.BUY;
        BigDecimal baseAmount = request.getBaseAmount();
        BigDecimal executedPrice = isBuy ? ap.getAskPrice() : ap.getBidPrice();
        PriceSource priceSource = isBuy ? ap.getAskPriceSource() : ap.getBidPriceSource();
        BigDecimal quoteAmount = baseAmount.multiply(executedPrice).setScale(8, RoundingMode.HALF_DOWN);;

        WalletBalance baseBal = balances.stream()
                                        .filter(x -> base.equals(x.getId().getCurrency()))
                                        .findFirst()
                                        .orElse(null);
        WalletBalance quoteBal = balances.stream()
                                         .filter(x -> quote.equals(x.getId().getCurrency()))
                                         .findFirst()
                                         .orElse(null);

        if (baseBal == null) {
            baseBal = WalletBalance.create(userId, base);
            balances.add(baseBal);
        }

        if (quoteBal == null) {
            quoteBal = WalletBalance.create(userId, quote);
            balances.add(quoteBal);
        }

        if (isBuy) {
            if (quoteBal.getBalance().compareTo(quoteAmount) < 0) throw new InvalidBalanceException("Insufficient " + quote);
            quoteBal.setBalance(quoteBal.getBalance().subtract(quoteAmount));
            baseBal.setBalance(baseBal.getBalance().add(baseAmount));
        } else {
            if (baseBal.getBalance().compareTo(baseAmount) < 0) throw new InvalidBalanceException("Insufficient " + base);
            baseBal.setBalance(baseBal.getBalance().subtract(baseAmount));
            quoteBal.setBalance(quoteBal.getBalance().add(quoteAmount));
        }

        quoteBal.setUpdatedAt(now);
        baseBal.setUpdatedAt(now);
        walletBalanceRepository.saveAll(balances);

        TradingTransaction tx = TradingTransaction.builder()
                                                  .userId(userId)
                                                  .tradingPair(request.getSymbol())
                                                  .transType(request.getTransType())
                                                  .baseAmount(baseAmount)
                                                  .executedPrice(executedPrice)
                                                  .quoteAmount(quoteAmount)
                                                  .priceSource(priceSource)
                                                  .createdAt(now)
                                                  .build();

        TradingTransaction saved = transactionRepository.save(tx);
        return mapper.toDTO(saved);
    }
}
