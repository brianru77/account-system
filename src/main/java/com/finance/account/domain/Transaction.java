package com.finance.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
//accountId로 검색할 때 속도 증가시켜줌
@Table(indexes = {
        @Index(name = "idx_account_id", columnList = "accountId")
})

public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountId; //어떤 계좌의 거래인지

    private String type; //거래 종류 (입금, 출금, 이체 등)

    private Long amount; //거래 금액

    private LocalDateTime createdAt; //거래 발생 시간

    //영수증 발행을 위한 생성자
    public Transaction(Long accountId, String type, Long amount) {
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.createdAt = LocalDateTime.now(); //현재 시간 자동 저장
    }

}