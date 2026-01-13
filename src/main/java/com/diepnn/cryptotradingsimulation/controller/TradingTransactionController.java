package com.diepnn.cryptotradingsimulation.controller;

import com.diepnn.cryptotradingsimulation.dto.request.TradeRequest;
import com.diepnn.cryptotradingsimulation.dto.response.PageResponse;
import com.diepnn.cryptotradingsimulation.dto.response.TradeResult;
import com.diepnn.cryptotradingsimulation.service.TradingTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<PageResponse<TradeResult>> getTransactions(@RequestHeader("X-USER-ID") Long userId,
                                                                     @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<TradeResult> data = tradingTransactionService.getTransactionsByUserId(userId, pageable);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(PageResponse.from(data));
    }
}
