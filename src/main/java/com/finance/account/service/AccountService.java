package com.finance.account.service;

import com.finance.account.domain.Account;
import com.finance.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional //작업 중 하나라도 실패하면 모두 취소되도록
@RequiredArgsConstructor //Repository를 자동으로 연결해주는 도구입니다.
public class AccountService {

    private final AccountRepository accountRepository;

    //모든 계좌를 다 가져오는 로직 (장부 조회)
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}