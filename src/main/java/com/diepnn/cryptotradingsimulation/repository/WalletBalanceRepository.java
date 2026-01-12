package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.WalletBalance;
import com.diepnn.cryptotradingsimulation.domain.entity.WalletBalanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalletBalanceRepository extends JpaRepository<WalletBalance, WalletBalanceId> {
    List<WalletBalance> findAllByIdUserIdAndIdCurrencyIn(Long userId, List<String> currency);
}
