package com.finance.account.repository;

import com.finance.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    //특정 계좌(accountId)의 거래 내역을 시간 최신순으로
    List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}