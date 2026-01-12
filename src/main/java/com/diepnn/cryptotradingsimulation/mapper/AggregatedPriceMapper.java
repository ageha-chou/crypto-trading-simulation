package com.diepnn.cryptotradingsimulation.mapper;

import com.diepnn.cryptotradingsimulation.domain.entity.AggregatedPrice;
import com.diepnn.cryptotradingsimulation.dto.response.AggregatedPriceDTO;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;

@Component
public class AggregatedPriceMapper {
    public AggregatedPriceDTO toDTO(AggregatedPrice ap) {
        return new AggregatedPriceDTO(
                ap.getTradingPair(),
                ap.getBidPrice(),
                ap.getAskPrice(),
                ap.getBidPriceSource().toString(),
                ap.getAskPriceSource().toString(),
                ap.getUpdatedAt()
        );
    }
}
