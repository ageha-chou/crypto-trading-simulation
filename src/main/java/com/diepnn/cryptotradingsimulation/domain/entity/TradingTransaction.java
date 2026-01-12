package com.diepnn.cryptotradingsimulation.domain.entity;

import com.diepnn.cryptotradingsimulation.domain.enums.PriceSource;
import com.diepnn.cryptotradingsimulation.domain.enums.TransType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "trading_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TradingTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tradingPair;

    @Column
    private Long userId;

    @Enumerated(EnumType.STRING)
    private TransType transType;

    @Column
    private BigDecimal baseAmount;

    @Column
    private BigDecimal executedPrice;

    @Column
    private BigDecimal quoteAmount;

    @Enumerated(EnumType.STRING)
    private PriceSource priceSource;

    @Column
    private Instant createdAt;
}
