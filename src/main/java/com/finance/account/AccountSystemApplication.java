package com.finance.account;

import com.finance.account.domain.Account;
import com.finance.account.repository.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AccountSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountSystemApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(AccountRepository accountRepository) {
        return args -> {
            // 1번 계좌 생성 및 10,000원 입금
            Account account1 = new Account("111-111");
            account1.deposit(10000L);
            accountRepository.save(account1);

            // 2번 계좌 생성 (잔액 0원)
            Account account2 = new Account("222-222");
            accountRepository.save(account2);

            System.out.println("초기 데이터 자동 세팅 완료! (1번: 1만원, 2번: 0원)");
        };
    }
}