👥 팀 규칙 (Conventions)
Naming

컴포넌트 파일명: PascalCase.tsx (예: Header.tsx)

폴더명: 소문자(pages, components) 권장

함수/변수: camelCase

Commit message (권장)

feat: ...

fix: ...

refactor: ...

chore: ...


# SRRP Frontend

개발 동아리 웹 사이트 프론트엔드 레포지토리입니다.  
**CRUD 게시판** 기능과 **경성대 API 기반 로그인**을 연동하는 것을 목표로 합니다.

---

## ✅ 주요 기능 (Features)

### Auth
- 경성대 API 기반 로그인/로그아웃
- 로그인 상태 유지 (토큰/세션 기반)
- 보호된 라우트(Protected Route) 접근 제어

### Board (CRUD)
- 게시글 목록 조회
- 게시글 상세 조회
- 게시글 작성(Create)
- 게시글 수정(Update)
- 게시글 삭제(Delete)
- (선택) 검색/필터/페이지네이션

### UI
- Header / Footer 공통 레이아웃
- 반응형 레이아웃
- (선택) 다크모드

---

## 🧰 기술 스택 (Tech Stack)
- **React + TypeScript**
- **Vite**
- **Tailwind CSS**
- (선택) React Router
- (선택) 상태관리: Zustand / Redux Toolkit / TanStack Query

> Node 버전: **>= 20** 권장

---

## 📁 프로젝트 구조 (예시)
> 리팩토링 이후 구조 기준 (팀 합의에 따라 변경 가능)
```
frontend/
src/
app/
App.tsx
main.tsx
routes.tsx
pages/
home/
HomePage.tsx
board/
BoardListPage.tsx
BoardDetailPage.tsx
BoardWritePage.tsx
BoardEditPage.tsx
auth/
LoginPage.tsx
components/
layout/
Header.tsx
Footer.tsx
Layout.tsx
auth/
LoginButton.tsx
ui/
Button.tsx
api/
client.ts
auth.ts
board.ts
styles/
globals.css
```

---

## 🔐 환경 변수 (Environment Variables)

`.env` 파일은 커밋하지 않습니다. (`.gitignore`)

예시: `.env.example`
```bash
VITE_API_BASE_URL=http://localhost:8000
VITE_KSU_AUTH_URL=[경성대 로그인 API URL]

실제 로컬 설정: .env

VITE_API_BASE_URL=http://localhost:8000
VITE_KSU_AUTH_URL=...
