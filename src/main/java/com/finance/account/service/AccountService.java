package com.finance.account.service;

import com.finance.account.domain.Account;
import com.finance.account.domain.Transaction; // 추가
import com.finance.account.repository.AccountRepository;
import com.finance.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository; //영수증

    public Account createAccount(String accountNumber) {
        Account newAccount = new Account(accountNumber);
        return accountRepository.save(newAccount);
    }

    @Transactional
    public Account deposit(Long id, Long amount) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.deposit(amount);

        //영수증 입금(DEPOSIT) 기록 저장
        transactionRepository.save(new Transaction(id, "DEPOSIT", amount));

        return account;
    }

    @Transactional
    public Account withdraw(Long id, Long amount) {
        Account account = accountRepository.findById(id).orElseThrow();
        account.withdraw(amount);

        //영수증 출금(WITHDRAW) 기록 저장
        transactionRepository.save(new Transaction(id, "WITHDRAW", amount));

        return account;
    }

    @Transactional
    public void transfer(Long fromId, Long toId, Long amount) {
        Account fromAccount = accountRepository.findById(fromId).orElseThrow();
        Account toAccount = accountRepository.findById(toId).orElseThrow();

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        //영수증 내 계좌에서는 출금, 상대방 계좌에서는 입금 기록 저장
        transactionRepository.save(new Transaction(fromId, "TRANSFER_OUT", amount));
        transactionRepository.save(new Transaction(toId, "TRANSFER_IN", amount));
    }

    //(새로 추가) 특정 계좌의 거래 내역을 조회하는 기능
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
    }
}