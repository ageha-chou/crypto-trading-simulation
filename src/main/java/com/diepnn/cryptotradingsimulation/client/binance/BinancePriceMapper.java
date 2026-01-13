package com.diepnn.cryptotradingsimulation.client.binance;

import com.diepnn.cryptotradingsimulation.dto.CommonPriceDTO;
import com.diepnn.cryptotradingsimulation.client.binance.dto.BinanceDTO;
import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;
import org.springframework.stereotype.Component;

@Component
public class BinancePriceMapper {
    public CommonPriceDTO toCommon(BinanceDTO dto) {
        return new CommonPriceDTO(dto.symbol().toUpperCase(), dto.bidPrice(), dto.askPrice(), PriceSource.BINANCE);
    }
}
