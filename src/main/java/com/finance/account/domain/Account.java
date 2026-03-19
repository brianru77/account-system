package com.finance.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountNumber;

    private Long balance; // 잔액

    // 계좌를 처음 만들 때 사용할 생성자
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0L; // 처음 생성 시 잔액은 0원
    }
    public void deposit(Long amount) {
        this.balance += amount; // 기존 잔액에 입금액을 더함
    }
    public void withdraw(Long amount) {
        if (this.balance < amount) {
            // 잔액이 부족하면 에러를 발생시켜 거래를 중단시킵니다.
            throw new RuntimeException("잔액이 부족합니다.");
        }
        this.balance -= amount;
    }
}