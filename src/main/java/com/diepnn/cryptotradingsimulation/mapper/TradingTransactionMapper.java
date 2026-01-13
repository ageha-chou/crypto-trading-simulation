package com.diepnn.cryptotradingsimulation.mapper;

import com.diepnn.cryptotradingsimulation.domain.entity.TradingTransaction;
import com.diepnn.cryptotradingsimulation.dto.response.TradeResult;
import org.springframework.stereotype.Component;

@Component
public class TradingTransactionMapper {
    public TradeResult toDTO(TradingTransaction tt) {
        return new TradeResult(
                tt.getId(),
                tt.getTradingPair(),
                tt.getTransType(),
                tt.getBaseAmount(),
                tt.getExecutedPrice(),
                tt.getQuoteAmount(),
                tt.getPriceSource().toString(),
                tt.getCreatedAt()
        );
    }
}
