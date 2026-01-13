package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.TradingTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingTransactionRepository extends JpaRepository<TradingTransaction, Long> {
    Page<TradingTransaction> findAllByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
