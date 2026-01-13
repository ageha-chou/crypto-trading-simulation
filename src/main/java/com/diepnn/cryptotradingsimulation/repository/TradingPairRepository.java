package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.TradingPair;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingPairRepository extends JpaRepository<TradingPair, String> {
}
