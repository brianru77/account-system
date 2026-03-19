package com.finance.account.repository;

import com.finance.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // 특정 계좌(accountId)의 거래 내역을 시간 역순(최신순)으로 가져오는 마법의 명령어입니다!
    List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}