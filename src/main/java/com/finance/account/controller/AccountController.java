package com.finance.account.controller;

import com.finance.account.domain.Account;
import com.finance.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController //이 클래스가 웹 요청을 받는 창구임
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    //브라우저에서 accounts 라고 치면 이 메서드가 실행
    //주소창에 localhost:8080/deposit?amount=50000 이라고 치면 입금
    @GetMapping("/deposit")
    public String deposit(@RequestParam Long amount) {
        //1. 장부에서 첫 번째 계좌를 꺼냄.
        List<Account> accounts = accountService.getAllAccounts();

        if (!accounts.isEmpty()) {
            Account account = accounts.get(0);

            //2. 입력받은 금액만큼 잔액을 올림.
            account.setBalance(account.getBalance() + amount);

            //3. 변경된 장부를 다시 금고(DB)에 저장.
            //(원래는 Service에서 처리하는 게 정석이지만, 오늘은 흐름을 보기 위해 여기서 바로 함)
            //accountRepository.save(account); <- JPA는 데이터가 바뀌면 자동으로 저장해주기도 하지만, 확실하게 써줌

            return amount + "원이 입금되었습니다! 현재 잔액: " + account.getBalance();
        }
        return "입금할 계좌를 찾을 수 없습니다.";
    }
}