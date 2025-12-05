# SRRP ( Spring-React-Rest-PlayGround )
- 스프링부트와 리액트로 가지고 노는 곳 

# BACKEND

## Spring Boot 환경

* **Spring Boot 버전:** 4.0.0
* **Java 버전:** 21
* **Build Tool:** Gradle / Groovy DSL

---

## 프로젝트 구조

```
backend/
 ├─ build.gradle
 └─ src/
     ├─ main/
     │   ├─ java/
     │   │   └─ com.github.gubbib/
     │   │       ├─ Config/       # 각종 Config 설정 등
     │   │       ├─ Controller/   # REST API 컨트롤러 (@RestController 등)
     │   │       ├─ Domain/       # 엔티티 / 도메인 모델 (@Entity 등)
     │   │       ├─ Dto/          # 요청 / 응답 DTO
     │   │       ├─ Exception/    # 전역 및 도메인별 예외 관리 폴더
     │   │       ├─ JWT/          # JWT 기반 인증/인가 구현 (토큰 생성, 검증, 필터 등)
     │   │       ├─ Repository/   # JPA Repository 인터페이스
     │   │       ├─ Security/     # 공통 보안 설정 및 기본 시큐리티 로직 
     │   │       ├─ Service/      # 서비스 계층 — 비즈니스 로직
     │   │       └─ BackendApplication.java   # @SpringBootApplication (메인 클래스)
     │   └─ resources/
     │       ├─ application.properties (또는 application.yml)  # 환경 설정
     │       ├─ static/      # 안 씀
     │       └─ templates/   # 안 씀
     └─ test/
         └─ java/
             └─ com.github.gubbib
                 └─ BackendApplicationTests.java   # 테스트 코드

```

## 주요 의존성 (Dependencies)

### Core & Web

* **Spring Web**
  REST API 구축을 위한 기본 웹 프레임워크

### Database

* **Spring Data JPA**
  ORM 기반 DB 연동 및 Repository 지원
* **PostgreSQL Driver**
  PostgreSQL 데이터베이스 연결용 JDBC 드라이버

### Security & Auth

* **Spring Security**
  인증/인가 처리 전체 담당
* **OAuth2 Client**
  Google/GitHub 등 소셜 로그인 클라이언트 기능
* **OAuth2 Resource Server**
  JWT 기반 API 보호 / 토큰 검증 기능
* **[JJWT (io.jsonwebtoken)](https://mvnrepository.com/search?q=jsonwebtoken)**
  JWT 생성/검증용 라이브러리 (0.11.5 버전 사용)

### Developer Tools

* **Lombok**
  Getter/Setter/Builder 자동 생성
* **Spring Boot DevTools**
  개발 환경 자동 리로드 및 편의 기능 제공
* **Validation (Jakarta Validation)**
  요청 값 자동 검증 (`@Valid`, `@NotBlank`, `@Email` 등)

### Redis

* **Spring Data Redis**
  Redis 기반 캐시, 세션, Refresh Token 저장 등을 위한 Key-Value 스토어 연동

---

# FRONTEND

## React 환경 (Vite 기반)

* **Frontend Framework:** React
* **Build Tool:** Vite
* **Language:** TypeScript
* **패키지 매니저:** npm

---

## 프로젝트 구조

```
frontend/
 ├── public/
 ├── src/
 │    ├── components/
 │    ├── App.jsx
 │    └── main.jsx
 ├── index.html
 ├── package.json
 └── vite.config.js
```

# 수정 필요
- Oauth2UserInfo.java - ofXXX 메소드 함수 완성 필요
- OAuth2SuccessHandler - redirect 경로 수정 필요
- UserServiceImp.java - my-comment 로직 수정 필요
- UserController.java - my-comment 로직 수정 필요
- CommentRepository.java - my-comment 쿼리문 작성 필요