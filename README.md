# 📎 leaf-between ( 2024.04 ~ 2024.09 )

<h2>Project Introduce</h2>
건국대학교 glocal 캠퍼스의 외주를 받아 구현한 어플리케이션으로 배민을 벤치 마킹하여 구현한 서비스입니다. <br/>

복잡한 데이터베이스 구조에서 원하는 데이터를 추출하고 최적화하기 위해 활용되는 새로운 mysql 로직과 함수를 알게 되었으며, 또한 부하 테스트를 통해 최적의 쿼리를 작성하였습니다. <br/>

<h2>프로젝트 개요 & 주요 기능</h2>
건국대학교 Glocal캠퍼스 학생들에게 지역 내 식당에서 이용할 수 있는 혜택을 제공하여 경제적 부담을 줄이고, 지역 경제와의 상생을 도모하는 프로젝트입니다. <br/>
할인 혜택을 제공하는 매장들을 조회하고, 쿠폰을 활용하여 학생들이 저렴한 금액으로 식사를 해결할 수 있는 서비스입니다. <br/><br/>


- 사용자
    - OAuth2.0 소설 계정 로그인 ( Google, Naver, Kakao )
    - 학교 인증 ( SMTP 기반 학교 이메일 인증 )
    - 개인 정보 변경 , 퀘스트 , 1대1 고객센터
    - 마이페이지
- 마켓
    - 스토어 조회 및 검색 ( 최신 순 , 인기 순 , 거리가 가까운 순 )
    - 가게 상세 정보, 메뉴, 쿠폰, 메뉴 옵션 조회
    - 새로운 스토어 요청
- 리뷰
    - 인기 리뷰 top 12 조회
    - 총 평점, 별점 별로 분포도 조회
    - 특정 가게에 대한 리뷰 조회 ( 최신 순 , 추천 순 , 별점 순 )
    - 포토 리뷰 조회
    - 사용자가 작성한 리뷰 관리
- 장바구니 및 주문
    - 주문 내역 조회
    - 장바구니에 담은 상품 확인
    - 상품 주문
    - 할인 가능한 경우 할인율 표시
- 검색
    - 최근 검색 History
    - 자동 완성 검색어
    - top 10 인기 검색어

<h2>개발 구성원</h2>

- 백엔드 1명 ( 박상진 )
- 플러터 개발자 3명 ( 이정원, 함채현, 이준희 )  
- 디자이너 1명 ( 장하빈 )

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

![erd](https://github.com/user-attachments/assets/a0537e7c-7468-4641-ac63-e53a58a39197)

<h2>Spring Security</h2>

> Security 설정을 추가해 인가된 사용자만 GET을 제외한 모든 API에 접근할 수 있도록 제한합니다.

![image](https://github.com/user-attachments/assets/dccc27de-0c02-4b9e-99cc-103d176acf65)

- CSRF : disable
- Session Creation Policy : STATELESS
- JwtAuthenticationFilter : before UsernamePasswordAuthenticationFilter.class
</br>

- GET 을 제외한 모든 Methods 는 인증이 필요하며 특수한 경우 ( 로그인 , 회원가입 , 로그아웃 ) 만 인증을 제외합니다. </br>
- 인가가 필요한 API는 .hasRole() .hasAnyRole() 으로 접근 지정하고 그 외 모든 USER 가 접근할 수 있는 API는 .permitAll() 로 설정했습니다.</br>

<h2>JPA & QueryDSL</h2>

> 객체 중심 설계와 반복적인 CRUD를 Spring Data JPA 로 최소화해 비즈니스 로직에 집중합니다.

- QueryDSL : 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 컴파일 시점에 빠르게 찾아냅니다.</br>
- Spring Data JPA 를 이용해 반복적인 CRUD를 최소화 하고 , 페이징을 이용해 성능을 높혔습니다.
- QueryDSL 을 이용해 복잡한 JPQL 작성시 발생할 수 있는 문법오류를 최소화 합니다.

JPA & QueryDSL 패키지 구조는 다음과 같습니다.</br>

- domain ( Entity class )</br>
- repository ( Spring Data JPA interface )</br>
- CustomRepositoryImpl ( QueryDSL )</br>

<h2>OAuth2.0 & JWT</h2>

> 구글 & 네이버 & 카카오 소셜 서버를 이용해 불필요한 회원가입을 줄이고 , JWT을 이용해 사용자 인증 정보를 저장합니다.

- Access Token은 Flutter 내부에 저장되며, 매 요청마다 토큰을 검증합니다.

<h2>1. 로그인</h2>

<img src="https://github.com/user-attachments/assets/9fa123c3-7804-4633-ac4f-ccb36c1bd0c2" width="25%">

<img src="https://github.com/user-attachments/assets/2a540cc8-3003-4c5e-a318-782c1d974d28" width="25%">

<h2>2. 메인화면</h2>

![image](https://github.com/user-attachments/assets/c762fda0-f8e2-4f87-9a6d-3c8b4ddac1f8)

<h2>3. 매장 검색</h2>

![image](https://github.com/user-attachments/assets/87a6d028-6d90-4d62-b8a0-80c0bd39e454)

<h2>4. 검색 페이지</h2>

![image](https://github.com/user-attachments/assets/a2019472-4c77-410e-822a-ff5f3227da46)

<h2>5. 파트너쉽 요청 페이지</h2>

<img src="https://github.com/user-attachments/assets/d6d53ecc-c1e5-4e92-aa56-9915bb5b035f" width="20%">

<img src="https://github.com/user-attachments/assets/501ff713-471a-47be-832a-967cacefb03e" width="20%">

<img src="https://github.com/user-attachments/assets/7fb773e1-0aa7-4851-85e2-c6e1889887df" width="20%">

<img src="https://github.com/user-attachments/assets/f0e1aecf-79d2-4f73-8123-eadf08669c90" width="20%">

<h2>6. 매장 상세 보기</h2>

![image](https://github.com/user-attachments/assets/c233a305-59a0-4c6b-98c2-421e84971cfc)

<h2>7. 쿠폰 및 메뉴 옵션 보기</h2>

<img src="https://github.com/user-attachments/assets/acdd77ac-20b6-4eb8-8929-332ecbcc87b5" width="25%">

<img src="https://github.com/user-attachments/assets/77122855-97e3-4ab1-b8f6-7ddafe0ca417" width="25%">

<h2>8. 리뷰 및 포토 리뷰 조회</h2>

<img src="https://github.com/user-attachments/assets/06a275be-79e8-4f18-b56c-74e804da99c5" width="25%">

<img src="https://github.com/user-attachments/assets/044e2da1-95d2-4ea5-ab45-490413926cf7" width="25%">

<img src="https://github.com/user-attachments/assets/62fe3d9f-6e37-4388-8b6e-4e06ef621869" width="25%">

<h2>9. 마이페이지 및 내 정보 변경</h2>

<img src="https://github.com/user-attachments/assets/b1c366b4-cdbb-4680-a190-18c878d7dd9e" width="25%">

<img src="https://github.com/user-attachments/assets/36c8609c-6d28-4b75-92c0-c9b175ed6fa4" width="25%">

<h2>10. 공지사항</h2>

<img src="https://github.com/user-attachments/assets/2025448e-9b7e-4308-adf4-be34f0e315a8" width="25%">

<img src="https://github.com/user-attachments/assets/fd96b590-0f4e-4712-8370-a2e8cd29e6cb" width="25%">

<h2>11. 쿠폰 보관함 및 찜 마켓 조회</h2>

<img src="https://github.com/user-attachments/assets/a6b7f656-b273-4d33-84a3-a3d0f0c3b3f4" width="25%">

<img src="https://github.com/user-attachments/assets/48dd97d3-5404-463b-9844-e162adda4ba0" width="25%">

<h2>12. 주문 내역 조회</h2>

<img src="https://github.com/user-attachments/assets/acfa56e5-3527-4f81-916f-42f403a2d5b7" width="25%">

<img src="https://github.com/user-attachments/assets/8e1d3273-8e3f-4e6b-af2f-bb8c286e1027" width="25%">

<h2>13. 리뷰 관리</h2>

<img src="https://github.com/user-attachments/assets/68d918e8-bd87-4b56-9d33-e288b9e9242d" width="20%">

<img src="https://github.com/user-attachments/assets/37f6f815-41fe-4480-9bd2-6f99ad3ba4a2" width="20%">

<img src="https://github.com/user-attachments/assets/4f32cd86-30a3-4cde-afa6-e71a737777b5" width="20%">

<img src="https://github.com/user-attachments/assets/94291f69-84ee-4fdd-b28f-21953c21a737" width="20%">

<h2>14. 퀘스트</h2>

![image](https://github.com/user-attachments/assets/ecc51f15-f23d-473a-8290-b02462d9eabd)

<h2>15. 이벤트</h2>

<img src="https://github.com/user-attachments/assets/60abdc99-2028-48b8-ae1b-567a5089e226" width="25%">

<img src="https://github.com/user-attachments/assets/125ec035-ab2f-4c2b-93f1-c84ec52efe71" width="25%">

<h2>16. 장바구니</h2>

<img src="https://github.com/user-attachments/assets/b41566d6-87f8-4a8e-af9a-316102ea9df5" width="25%">

<img src="https://github.com/user-attachments/assets/e3ad4eef-89cb-4e09-91ff-dd786fb54fb7" width="25%">

<h2>17. 주문 결제 페이지</h2>

![image](https://github.com/user-attachments/assets/a58803c0-3d59-4038-a84a-6a9cb6ac24f6)

