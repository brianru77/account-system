## Simple Banking API

+ 계좌 개설
+  동시성이 고려된 안전한 계좌 이체
+  금융 시스템의 코어 기능을 구현한 RESTful API 서버 
+ **데이터의 무결성 보장** 및 **트래픽 병목 상황에서 동시성 제어**에 중점을 두고 개발.

### Tech Stack
- **Language:** Java 17
- **Framework:** Spring Boot 3.x, Spring Data JPA
- **Database:** MySQL 8.0, H2 (Test)
- **Infrastructure:** Docker Compose
- **Test Tool:** Postman, Apache JMeter

---

### Technical Highlights

#### 1. 트랜잭션(Transaction) 일관성 보장
- 출금과 입금이 동시에 일어나는 이체 과정에서 예기치 않은 서버 다운이나 예외 발생 시, 돈이 증발하는 문제를 방지하기 위해 @Transactional을 활용.
- **결과:** 이체 중간 단계에서 실패 시 완벽한 롤백(Rollback)을 보장하여 금전 데이터의 정합성을 지켰습니다.

#### 2. 동시성 제어와 갱신 손실(Lost Update) 방어
- 100명의 사용자가 0.001초의 오차 없이 동시에 출금을 요청할 경우 발생하는 동시성 문제를 해결했습니다.
- JPA의 @Version을 활용한 **낙관적 락(Optimistic Lock)** 메커니즘을 도입.
- **결과:** Apache JMeter를 통한 부하 테스트(100 Threads) 결과, 단 하나의 요청만 성공하고 나머지는 OptimisticLockingFailureException으로 안전하게 튕겨내어 잔액 데이터가 오염되는 것을 완벽히 방어했습니다.

#### 3. 글로벌 예외 처리 (Global Exception Handling)
- 서버 내부의 에러(500 Internal Server Error)를 클라이언트에게 그대로 노출하지 않고, @RestControllerAdvice를 사용하여 전역적으로 예외를 통제했습니다.
- **결과:** 잔액 부족, 계좌 없음 등의 비즈니스 예외를 {"errorCode": "FAIL", "errorMessage": "잔액이 부족합니다"} 형태의 규격화된 JSON으로 응답하도록 하여 프론트엔드와의 협업 효율을 높였습니다.

#### 4. Docker를 활용한 인프라 환경 구축
- 로컬 환경의 종속성을 없애고 빠르고 일관된 DB 세팅을 위해 **Docker Compose**를 도입하여 MySQL 컨테이너 환경을 구축했습니다.
- DB Indexing @Table(indexes=...)을 통해 수백만 건의 영수증(Transaction) 데이터가 쌓여도 빠르게 조회할 수 있도록 설계했습니다.

---

### 주요 기능

1. **Account (계좌 도메인)**
   - 계좌 생성 (초기 잔액 0원)
   - 입금 / 출금 기능 (잔액 부족 시 방어 로직)
   - 계좌 간 이체 기능

2. **Transaction (영수증/거래 내역 도메인)**
   - 입금, 출금, 이체 발생 시 자동으로 거래 이력 생성
   - 특정 계좌의 과거 거래 내역 최신순 조회

---
