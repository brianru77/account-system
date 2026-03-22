package com.finance.account.repository;

import com.finance.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

//DB에서 Account(계좌) 데이터를 넣고 빼는 창고 지기 역할
public interface AccountRepository extends JpaRepository<Account, Long> {
    //JPA
    //JpaRepository를 상속받는 것만으로도 아래와 같은 기능이 자동으로 생성.
    //save 기능 account 계좌 정보를 DB에 저장 (Insert / Update)
    //find 기능 ById(id) ID값으로 특정 계좌 찾기 (Select)
    //delete 기능 계좌 정보 삭제 (Delete)
    //findAll() 모든 계좌 목록 가져오기
    //직접 쿼리 작성x 자바 메서드 호출만으로 DB를 완벽하게 제어
}