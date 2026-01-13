package com.diepnn.cryptotradingsimulation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "trading_pair")
@Getter
@Setter
@NoArgsConstructor
public class TradingPair {
    @Id
    private String symbol;

    @Column
    private String baseCurrency;

    @Column
    private String quoteCurrency;
}
