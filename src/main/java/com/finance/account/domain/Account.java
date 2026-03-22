package com.finance.account.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

//데이터베이스의 account 테이블과 1:1로 매핑되는 클래스.

@Entity //이 클래스는 JPA가 관리하는 엔티티임을 선언 == DB 테이블이 되는거지
@Getter //모든 필드에 대해 getBalance(), getAccountNumber() 등의 메서드를 자동으로 생성.
@NoArgsConstructor //파라미터가 없는 기본 생성자를 자동으로 만들기
public class Account {

    @Id //이 필드가 테이블의 PK임.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //ID 값을 DB가 알아서 1, 2, 3.., 순서대로 매김
    private Long id;

    private String accountNumber; //계좌 번호

    private Long balance; //현재 잔액

    //낙관적 락
    //@Version: 데이터 수정 시 '버전 번호'를 체크.
    //100명이 동시에 출금할 때, 1번 사용자가 수정에 성공하면 버전이 1 상승
    //뒤늦게 도착한 2~100번 사용자는 내가 읽은 버전은 0인데 왜 DB는 벌써 1이지 이런 원리로 수정을 포기.
    @Version
    private Long version;

    //계좌 생성 시 사용하는 생성자 (일단 처음 만들 때는 잔액을 0으로 초기화)
    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0L;
    }

    //입금 메서드
    //객체 지향적인 설계로 외부에서 balance를 직접 수정하는 게 아니라 Account 객체 스스로가 자기 잔액을 증가시키도록

    public void deposit(Long amount) {
        this.balance += amount;
    }

    //출금 메서드
    //출금 전 잔액 확인을 직접 검증합니다. if(마나가 부족) {스킬이 안 나가는 로직과 동일}.

    public void withdraw(Long amount) {
        if (this.balance < amount) {
            //잔액이 부족하면 예외를 던집니다.
            //이 예외는 GlobalExceptionHandler가 낚아채서 사용자에게 알려주기.
            throw new RuntimeException("잔액이 부족합니다.");
        }
        this.balance -= amount;
    }
}