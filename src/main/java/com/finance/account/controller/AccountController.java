package com.finance.account.controller;

import com.finance.account.domain.Account;
import com.finance.account.domain.Transaction; //영수증 기능 참조
import com.finance.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List; //여러 개의 데이터를 담아 보내기 위한 리스트

@RestController //이 클래스가 JSON 데이터를 주고받는 API 컨트롤러임을 선언.
@RequestMapping("/api/accounts") //이 클래스의 모든 주소는 "/api/accounts"로 시작 그냥 기본 라우팅 경로
@RequiredArgsConstructor //final이 붙은 AccountService를 자동으로 주입==DI하기.
public class AccountController {

    //비즈니스 로직 계산, DB 저장을 담당하는 서비스 객체.
    private final AccountService accountService;

    //계좌생성 API
    //POST http://localhost:8080/api/accounts?accountNumber=123-456
    //@RequestParam: URL 뒤에 붙는 파라미터(?accountNumber=...)를 읽어오는 기능.

    @PostMapping
    public Account create(@RequestParam String accountNumber) {
        //서비스에게 계좌 생성을 시키고, 생성된 결과를 다시 클라이언트에게 돌려줌.
        return accountService.createAccount(accountNumber);
    }

    //입금 API
    //POST http://localhost:8080/api/accounts/1/deposit?amount=1000
    //@PathVariable: 주소창에 포함된 {id} 값을 변수로 가져옴
    @PostMapping("/{id}/deposit")
    public Account deposit(@PathVariable Long id, @RequestParam Long amount) {
        //특정 ID의 계좌에 금액을 충전하는 로직을 호출.
        return accountService.deposit(id, amount);
    }

    //출금 API
    //POST http://localhost:8080/api/accounts/1/withdraw?amount=500
    @PostMapping("/{id}/withdraw")
    public Account withdraw(@PathVariable Long id, @RequestParam Long amount) {
        //특정 ID의 계좌에서 금액을 빼는 로직을 호출. if 잔액 부족 시 에러 발생
        return accountService.withdraw(id, amount);
    }

    //이체 API
    //POST http://localhost:8080/api/accounts/transfer?fromId=1&toId=2&amount=1000
    //@return: 성공 메시지 문자열(String)을 반환.
    @PostMapping("/transfer")
    public String transfer(@RequestParam Long fromId, @RequestParam Long toId, @RequestParam Long amount) {
        //1번(from)에서 2번(to)으로 이체하는 서비스 로직 실행
        //내부적으로 @Transactional이 걸려 있어 한쪽이라도 실패하면 자동 롤백되는 구조.
        accountService.transfer(fromId, toId, amount);
        return "이체가 성공적으로 완료되었습니다!";
    }

    //거래 내역 조회 API]
    //GET http://localhost:8080/api/accounts/1/transactions
    //@return: 해당 계좌의 모든 영수증(Transaction) 목록을 리스트로 반환
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactions(@PathVariable Long id) {
        //특정 계좌의 영수증 꾸러미 리스트를 가져옴.
        //스프링이 이 리스트를 자동으로 JSON 배열([ {...}, {...} ])로 변환해 서빙?함.
        return accountService.getTransactions(id);
    }
}