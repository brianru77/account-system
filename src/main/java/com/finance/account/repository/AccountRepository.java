package com.finance.account.repository;

import com.finance.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

//JpaRepository를 상속받는 것만으로 저장, 조회, 삭제 기능은 자동
public interface AccountRepository extends JpaRepository<Account, Long> {
}