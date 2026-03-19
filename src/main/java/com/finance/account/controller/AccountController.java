package com.finance.account.controller;

import com.finance.account.domain.Account;
import com.finance.account.domain.Transaction; //영수증(Transaction) 기능
import com.finance.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List; //바구니(List) 추가

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //계좌 생성 API
    @PostMapping
    public Account create(@RequestParam String accountNumber) {
        return accountService.createAccount(accountNumber);
    }

    //입금 API
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestParam Long amount) {
        return accountService.deposit(id, amount);
    }

    //출금 API
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestParam Long amount) {
        return accountService.withdraw(id, amount);
    }

    //이체 API
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromId, @RequestParam Long toId, @RequestParam Long amount) {
        accountService.transfer(fromId, toId, amount);
        return "이체가 성공적으로 완료되었습니다!";
    }

    //거래 내역 조회 API
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id) {
        return accountService.getTransactions(id);
    }
}