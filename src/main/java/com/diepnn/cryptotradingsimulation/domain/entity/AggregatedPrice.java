package com.diepnn.cryptotradingsimulation.domain.entity;

import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "aggregated_price")
@Getter
@Setter
@NoArgsConstructor
public class AggregatedPrice {
    @Id
    private String tradingPair;

    @MapsId
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trading_pair", referencedColumnName = "symbol")
    private TradingPair tradingPairEntity;

    @Column
    private BigDecimal bidPrice;

    @Column
    private BigDecimal askPrice;

    @Enumerated(EnumType.STRING)
    private PriceSource bidPriceSource;

    @Enumerated(EnumType.STRING)
    private PriceSource askPriceSource;

    @Column
    private Instant updatedAt;

    public static AggregatedPrice create(TradingPair pair) {
        AggregatedPrice p = new AggregatedPrice();
        p.tradingPairEntity = pair;
        return p;
    }
}
