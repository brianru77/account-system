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

    private Long balance;

    // 핵심
    @Version
    private Long version;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0L;
    }

    public void deposit(Long amount) {
        this.balance += amount;
    }

    public void withdraw(Long amount) {
        if (this.balance < amount) {
            throw new RuntimeException("잔액이 부족합니다.");
        }
        this.balance -= amount;
    }
}