package com.diepnn.cryptotradingsimulation.repository;

import com.diepnn.cryptotradingsimulation.domain.entity.WalletBalance;
import com.diepnn.cryptotradingsimulation.domain.entity.WalletBalanceId;
import com.diepnn.cryptotradingsimulation.dto.response.WalletBalanceDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalletBalanceRepository extends JpaRepository<WalletBalance, WalletBalanceId> {
    List<WalletBalance> findAllByIdUserIdAndIdCurrencyIn(Long userId, List<String> currency);

    @Query("""
        select new com.diepnn.cryptotradingsimulation.dto.response.WalletBalanceDTO (
            wb.id.currency,
            wb.balance,
            wb.updatedAt
        )
        from WalletBalance wb
        where wb.id.userId = :userId
    """)
    List<WalletBalanceDTO> findAllByIdUserId(Long userId);
}
