# BACKEND

## Spring Boot 환경

* **Spring Boot 버전:** 4.0.0
* **Java 버전:** 21
* **Build Tool:** Gradle / Groovy DSL

---

## Local 개발 환경
### Docker
- PostgreSQl, Redis 를 Docker로 실행하여 손쉽게 환경을 구성

#### 설치 및 실행 방법
1. Docker Desktop을 설치한다.
2. 터미널(cmd 또는 PowerShell)에서 프로젝트 루트의 `SRRP_Docker` 디렉토리로 이동한다.
3. 아래 명령어를 실행하여 컨테이너를 실행한다.

```bash
docker compose up -d
```
#### 컨테이너 종료
```bash
docker compose down -v
```

### Postman
- REST API 테스트와 요청/응답 검증에 사용
- JWT 인증 API는 Postman의 Bearer Token 기능을 사용하여 검증
- OAuth2 로그인 테스트 시 토큰 확인 및 API 호출 과정 검증 가능

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
     │   │       ├─ Handler/      # 각종 Handler 
     │   │       ├─ JWT/          # JWT 기반 인증/인가 구현 (토큰 생성, 검증, 필터 등)
     │   │       ├─ Repository/   # JPA Repository 인터페이스
     │   │       ├─ Security/     # 공통 보안 설정 및 기본 시큐리티 로직 
     │   │       ├─ Service/      # 서비스 계층 — 비즈니스 로직
     │   │       └─ BackendApplication.java   # @SpringBootApplication (메인 클래스)
     │   └─ resources/
     │       ├─ application.yml   # 환경 설정
     │       ├─ static/           # 안 씀
     │       └─ templates/        # 안 씀
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

### Swagger
* **Springdoc OpenAPI (springdoc-openapi-starter-webmvc-ui)**  
  Swagger UI 제공 및 API 문서 자동 생성
    - `@Operation`, `@ApiResponse`, `@Parameter` 등의 어노테이션을 통한 상세 문서화
    - `/swagger-ui/index.html` 경로에서 API 문서 UI 제공
    - JWT 인증 UI 연동(`bearerAuth`)을 통해 토큰 기반 테스트 지원
---

## 코드 작성 유의사항

### 1. Domain 생성 규칙

* 모든 Domain 클래스는 반드시 `Domain/BaseEntity.java`를 **extends** 해야 한다.
* `BaseEntity`에는 다음 필드 및 기능이 포함되어 있다.

  * 생성일 (`createdAt`)
  * 수정일 (`updatedAt`)
  * 삭제일 (`deletedAt`)
  * 삭제 여부 (`isDeleted`)
  * 삭제 상태 변경 메소드

---

### 2. Controller에서 사용자 식별 방식

#### 2-1. 인증된 사용자 정보 접근

* `@AuthenticationPrincipal`을 사용하여 `CustomUserPrincipal`을 주입받는다.
* `SecurityContext`를 직접 조회하거나 토큰을 파싱하는 방식은 **금지**한다.

#### 2-2. 특정 권한이 필요한 Controller 메소드

* `@PreAuthorize`를 사용하여 **Controller 진입 시점**에서 권한을 검증한다.
* 현재 사용 가능한 Role

  * `ROLE_USER`
  * `ROLE_ADMIN`
  * `ROLE_MANAGER`
  * `ROLE_SYSTEM`
* 권한 검증 로직을 Service 또는 Controller 내부에 직접 작성하지 않는다.

---

### 3. Service 작성 시 유의사항

1. 반드시 **Interface**를 먼저 작성한 후, 구현체는 `OOServiceImpl` 형태로 작성한다.
2. 기본적으로 `@Transactional(readOnly = true)`를 사용한다.

   * DB에 **쓰기 작업(생성, 수정, 삭제)** 이 발생하는 메소드는

     * `@Transactional`
     * 또는 `@Transactional(readOnly = false)`
       로 명시한다.
3. 하나의 Service에서는 다른 Service를 주입받을 수 있다.

   * Repository를 직접 주입하는 것은 **금지**한다.
   * Repository 접근이 필요할 경우 `BoardPostService`와 같은 중간 Service를 만들어 사용한다.
4. `@RequiredArgsConstructor` 사용 시 반드시 아래 방식으로 사용한다.

   ```java
   @RequiredArgsConstructor(onConstructor_ = @Autowired)
   ```

---

### 4. Exception 처리 방식

1. 기본적으로 Controller 계층에서 발생한 예외는

   * `Handler/GlobalExceptionHandler.java`에서 처리한다.
2. 명시적인 예외 처리가 필요한 경우

   * `Exception/ErrorCode.java`의 **Enum**을 사용하여 예외를 던진다.
   * 존재하지 않는 에러 코드라면 `ErrorCode.java`에 규칙에 맞게 추가하여 사용한다.

---

### 5. JWT 사용 시 유의사항

1. JWT **생성 및 삭제**는 반드시 `JWTCookieService`를 사용한다.
2. JWT **검증 및 파싱**은 `JWTTokenProvider`를 사용한다.

---

### 6. 알림(Notification) 생성 규칙

1. Service 계층에서 `NotificationService.create()`를 직접 호출하는 것은 **절대 금지**한다.
2. 반드시 아래 흐름을 따른다.

   * 알림이 필요한 경우, Service 계층에서는  
   `ApplicationEventPublisher`를 주입받아 
   `eventPublisher.publishEvent()` 를 통해 **Event만 발행**한다.

   * `NotificationHandler.java`에서 Event를 수신하여 알림 생성 처리