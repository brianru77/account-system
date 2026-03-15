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

    // 서버가 켜질 때 실행되는 특수 메서드입니다.
    @Bean
    public CommandLineRunner initData(AccountRepository accountRepository) {
        return args -> {
            accountRepository.save(Account.builder()
                    .accountNumber("123-456")
                    .balance(1000000L)
                    .build());
            System.out.println("금융 데이터 초기화 완료!");
        };
    }
}