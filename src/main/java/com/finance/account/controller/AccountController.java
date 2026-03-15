package com.finance.account.controller;

import com.finance.account.domain.Account;
import com.finance.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //이 클래스가 웹 요청을 받는 창구임
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //브라우저에서 /accounts 라고 치면 이 메서드가 실행
    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        return accountService.getAllAccounts();
    }
}