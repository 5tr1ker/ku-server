# 📎 leaf-between

<h2>Project Introduce</h2>
건국대학교 glocal 캠퍼스의 외주를 받아 구현한 어플리케이션으로 배민을 벤치 마킹하여 구현한 서비스입니다. <br/>

복잡한 데이터베이스 구조에서 원하는 데이터를 추출하고 최적화하기 위해 활용되는 새로운 mysql 로직과 함수를 알게 되었으며, 또한 부하 테스트를 통해 최적의 쿼리를 작성하였습니다. <br/>

<h2>프로젝트 개요 & 주요 기능</h2>
건국대학교 Glocal캠퍼스 학생들에게 지역 내 식당에서 이용할 수 있는 혜택을 제공하여 경제적 부담을 줄이고, 지역 경제와의 상생을 도모하는 프로젝트입니다.
1. 가게 정보 조회 및 검색
2. 특정 가게에 대한 리뷰 조회
3. 주문 내역 및 장바구니에 담은 상품 확인
4. 자동 완성 검색어 및 top 10 인기 검색어

<h2>Project Structure</h2>

> Flutter ( Dart ) + Spring Boot ( Gradle ) 의 어플리케이션 구조

- Java
- Spring Boot
- Gradle
- Rest API
- Spring Security
- JWT
- OAuth 2.0
- Redis
- MySQL
- JPA & Hibernate
- QueryDSL

<h2>ERD</h2>

![erd.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/4f74ed42-2cde-4c55-be68-5d0f23d25eb4/71a44639-bfa0-4887-9737-029117c39b80/erd.png)

<h2>Spring Security</h2>

> Security 설정을 추가해 인가된 사용자만 GET을 제외한 모든 API에 접근할 수 있도록 제한합니다.

