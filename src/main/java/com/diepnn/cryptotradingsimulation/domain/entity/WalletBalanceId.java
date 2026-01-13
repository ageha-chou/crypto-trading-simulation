package com.diepnn.cryptotradingsimulation.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WalletBalanceId {
    @Column
    private Long userId;

    @Column
    private String currency;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        WalletBalanceId that = (WalletBalanceId) o;
        return Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getCurrency(), that.getCurrency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getCurrency());
    }
}
