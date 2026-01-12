package com.diepnn.cryptotradingsimulation.controller;

import com.diepnn.cryptotradingsimulation.dto.request.TradeRequest;
import com.diepnn.cryptotradingsimulation.dto.response.TradeResult;
import com.diepnn.cryptotradingsimulation.service.TradingTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trades")
@RequiredArgsConstructor
public class TradingTransactionController {
    private final TradingTransactionService tradingTransactionService;

    @PostMapping
    public ResponseEntity<TradeResult> trade(@RequestHeader("X-USER-ID") Long userId,
                                             @RequestBody @Valid TradeRequest request) {
        return ResponseEntity.ok(tradingTransactionService.trade(userId, request));
    }

    @GetMapping
    public ResponseEntity<List<TradeResult>> getTransactions(@RequestHeader("X-USER-ID") Long userId) {
        List<TradeResult> data = tradingTransactionService.getTransactionsByUserId(userId);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }
}
