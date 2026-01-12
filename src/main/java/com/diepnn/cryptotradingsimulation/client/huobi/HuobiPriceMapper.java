package com.diepnn.cryptotradingsimulation.client.huobi;

import com.diepnn.cryptotradingsimulation.dto.CommonPriceDTO;
import com.diepnn.cryptotradingsimulation.client.huobi.dto.HuobiDTO;
import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;
import org.springframework.stereotype.Component;

@Component
public class HuobiPriceMapper {
    public CommonPriceDTO toCommon(HuobiDTO dto) {
        return new CommonPriceDTO(dto.symbol().toUpperCase(), dto.bid(), dto.ask(), PriceSource.HUOBI);
    }
}
