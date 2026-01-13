package com.diepnn.cryptotradingsimulation.service;

import com.diepnn.cryptotradingsimulation.domain.entity.TradingPair;
import com.diepnn.cryptotradingsimulation.repository.TradingPairRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TradingPairService {
    private final TradingPairRepository tradingPairRepository;

    public List<TradingPair> getSupportedTradingPairs() {
        return tradingPairRepository.findAll();
    }
}
