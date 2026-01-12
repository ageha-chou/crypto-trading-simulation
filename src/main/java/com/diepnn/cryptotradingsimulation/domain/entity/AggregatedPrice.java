package com.diepnn.cryptotradingsimulation.domain.entity;

import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "aggregated_price")
@Getter
@Setter
@NoArgsConstructor
public class AggregatedPrice {
    @Id
    private String tradingPair;

    @Column
    private BigDecimal bidPrice;

    @Column
    private BigDecimal askPrice;

    @Column
    @Enumerated(EnumType.STRING)
    private PriceSource bidPriceSource;

    @Column
    @Enumerated(EnumType.STRING)
    private PriceSource askPriceSource;

    @Column
    private LocalDateTime updatedAt;

    public static AggregatedPrice create(TradingPair pair) {
        AggregatedPrice p = new AggregatedPrice();
        p.tradingPair = pair.getSymbol();
        return p;
    }
}
