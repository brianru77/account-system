package com.finance.account.repository;

import com.finance.account.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//transaction(영수증/거래내역) 데이터를 관리
//모든 입금, 출금, 이체의 기록이 담긴 테이블에 접근

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//특정 계좌(accountId)의 거래 내역을 시간 최신순으로
    List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
}