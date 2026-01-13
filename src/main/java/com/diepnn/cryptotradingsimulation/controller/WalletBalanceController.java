package com.diepnn.cryptotradingsimulation.controller;

import com.diepnn.cryptotradingsimulation.dto.response.WalletBalanceDTO;
import com.diepnn.cryptotradingsimulation.service.WalletBalanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet-balances")
@RequiredArgsConstructor
public class WalletBalanceController {
    private final WalletBalanceService walletBalanceService;

    @GetMapping
    public ResponseEntity<List<WalletBalanceDTO>> getWalletBalances(@RequestHeader("X-USER-ID") Long userId) {
        List<WalletBalanceDTO> data = walletBalanceService.getBalanceByUserId(userId);
        if (data.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(data);
    }
}
