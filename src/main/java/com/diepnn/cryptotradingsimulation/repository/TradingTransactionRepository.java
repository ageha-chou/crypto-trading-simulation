package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.TradingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TradingTransactionRepository extends JpaRepository<TradingTransaction, Long> {
    List<TradingTransaction> findAllByUserId(Long userId);
}
