package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.TradingTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingTransactionRepository extends JpaRepository<TradingTransaction, Long> {
}
