package com.diepnn.cryptotradingsimulation.service;

import com.diepnn.cryptotradingsimulation.dto.response.WalletBalanceDTO;
import com.diepnn.cryptotradingsimulation.repository.WalletBalanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WalletBalanceService {
    private final WalletBalanceRepository walletBalanceRepository;

    public List<WalletBalanceDTO> getBalanceByUserId(Long userId) {
        return walletBalanceRepository.findAllByIdUserId(userId);
    }
}
