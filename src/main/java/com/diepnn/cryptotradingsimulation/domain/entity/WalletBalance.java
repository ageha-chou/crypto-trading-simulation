package com.diepnn.cryptotradingsimulation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Version;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "wallet_balance")
@NoArgsConstructor
@Getter
@Setter
public class WalletBalance {
    @EmbeddedId
    private WalletBalanceId id;

    @Column
    private BigDecimal balance;

    @Version
    private long version;

    @Column
    private Instant createdAt;

    @Column
    private Instant updatedAt;

    public static WalletBalance create(Long userId, String currency) {
        WalletBalance wb = new WalletBalance();
        wb.id = new WalletBalanceId(userId, currency);
        wb.balance = BigDecimal.ZERO;
        wb.createdAt = Instant.now();
        return wb;
    }
}
