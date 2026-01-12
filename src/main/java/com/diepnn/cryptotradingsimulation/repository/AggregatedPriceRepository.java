package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, String> {
    List<AggregatedPrice> findAllByTradingPairIn(Set<String> tradingPairs);
}
