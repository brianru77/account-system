package com.finance.account.service;

import com.finance.account.domain.Account;
import com.finance.account.domain.Transaction;
import com.finance.account.repository.AccountRepository;
import com.finance.account.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//비즈니스 로직의 총괄
//돈이 충분한가? 이체 시 양쪽 계좌를 동시에 수정해야 한다 이런 핵심 규칙을 처리

@Service //이 클래스를 스프링이 관리하는 서비스 빈으로 등록.
@RequiredArgsConstructor //Repository들을 자동으로 연결(DI)해주는 생성자 만들기.
public class AccountService {

    private final AccountRepository accountRepository; //계좌 창고 관리인
    private final TransactionRepository transactionRepository; //영수증 창고 관리인

    //새로운 계좌 생성
    public Account createAccount(String accountNumber) {
        Account newAccount = new Account(accountNumber);
        return accountRepository.save(newAccount);
    }

    //입금로금
    //@Transactional 이 메서드 안의 모든 작업은 하나의 묶음임
    //잔액 수정은 성공했는데 영수증 저장이 실패하면, 잔액 수정도 없던 일로 Rollback
    @Transactional
    public Account deposit(Long id, Long amount) {
        //DB에서 계좌를 찾기. 없으면 예외를 발생
        Account account = accountRepository.findById(id).orElseThrow();

        //계좌 객체 내부의 데이터를 수정
        account.deposit(amount);

        //입금 영수증을 생성하고 저장하기.
        transactionRepository.save(new Transaction(id, "DEPOSIT", amount));

        return account; //영속성 컨텍스트 덕분에 따로 save()를 안 해도 메서드 종료 시 DB에 반영됨
    }

    //출금로직
    @Transactional
    public Account withdraw(Long id, Long amount) {
        Account account = accountRepository.findById(id).orElseThrow();

        //계좌 스스로 잔액 부족 여부를 체크
        account.withdraw(amount);

        // 출금 기록 영수증 저장
        transactionRepository.save(new Transaction(id, "WITHDRAW", amount));

        return account;
    }

    //계좌이체 로직
    //두 계좌의 상태를 동시에 변경하고 각각의 영수증을 남김
    @Transactional
    public void transfer(Long fromId, Long toId, Long amount) {
        //보내는 사람과 받는 사람의 계좌를 각각 조회
        Account fromAccount = accountRepository.findById(fromId).orElseThrow();
        Account toAccount = accountRepository.findById(toId).orElseThrow();

        //보내는 쪽에서 출금 받는 쪽에서 입금 처리.
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        //두 계좌 각각에 맞는 영수증을 발행.
        transactionRepository.save(new Transaction(fromId, "TRANSFER_OUT", amount));
        transactionRepository.save(new Transaction(toId, "TRANSFER_IN", amount));
    }

    //거래 내역 조회
    //특정 계좌의 영수증 꾸러미를 최신순으로 가져오기
    public List<Transaction> getTransactions(Long accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId);
    }
}