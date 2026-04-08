# Vue.js + Spring Boot 통합 가이드

## 개요

이 프로젝트는 Spring Boot 백엔드와 Vue.js 프론트엔드를 통합한 구조입니다.

## 프로젝트 구조

```
src/main/
├── js/                    # Vue.js 소스 코드
│   ├── App.vue
│   ├── main.js
│   ├── components/
│   ├── vite.config.js
│   └── package.json
└── resources/
    └── static/            # 빌드된 정적 파일 (Vite 빌드 결과물)
        ├── index.html
        └── assets/
```

## 개발 환경 설정

### 1. Node.js 및 npm 설치 확인
```bash
node --version
npm --version
```

### 2. Vue.js 프로젝트 의존성 설치
```bash
cd src/main/js
npm install
```

### 3. 개발 모드 실행

**방법 1: Vite Dev Server 사용 (권장 - 개발 시)**
```bash
cd src/main/js
npm run dev
```
- Vite dev server가 `http://localhost:5173`에서 실행됩니다
- API 요청은 자동으로 `http://localhost:8080`으로 프록시됩니다
- Hot Module Replacement (HMR) 지원

**방법 2: Spring Boot만 실행 (프로덕션 빌드 후)**
```bash
# 먼저 Vue.js 빌드
cd src/main/js
npm run build

# Spring Boot 실행
./gradlew bootRun
```
- 빌드된 파일이 `src/main/resources/static`에 생성됩니다
- Spring Boot가 `http://localhost:8080`에서 정적 파일과 API를 모두 제공합니다

## 빌드 및 배포

### 프로덕션 빌드
```bash
cd src/main/js
npm run build
```

빌드 결과물은 자동으로 `src/main/resources/static`에 생성됩니다:
- `index.html`
- `assets/` (JS, CSS 파일)

### Spring Boot 실행
```bash
./gradlew bootRun
```

이제 `http://localhost:8080`에서 애플리케이션에 접근할 수 있습니다.

## 작동 원리

### 개발 환경
1. **Vite Dev Server** (`localhost:5173`): Vue.js 개발 서버
   - 빠른 HMR (Hot Module Replacement)
   - 소스맵 지원
   - API 요청은 `/api/*` 경로로 Spring Boot로 프록시

2. **Spring Boot** (`localhost:8080`): 백엔드 API 서버
   - REST API 제공
   - CORS 설정으로 Vite dev server 허용

### 프로덕션 환경
1. **Vite 빌드**: Vue.js 소스를 번들링하여 `static` 폴더에 배치
2. **Spring Boot**: 정적 파일과 API를 모두 제공
   - `/` → `index.html` (SPA 라우팅)
   - `/api/*` → REST API
   - `/assets/*` → 정적 리소스 (JS, CSS)

## 주요 설정 파일

### `vite.config.js`
- 빌드 출력 디렉토리: `../resources/static`
- 개발 서버 프록시: `/api` → `http://localhost:8080`
- SPA 라우팅 지원

### `WebConfig.java`
- SPA 라우팅: 모든 경로를 `index.html`로 리다이렉트
- 정적 리소스 핸들링

## API 연동

Vue.js에서 Spring Boot API를 호출할 때:

```javascript
// 개발 환경 (Vite dev server 사용 시)
fetch('/api/game/titles')  // 자동으로 localhost:8080으로 프록시

// 프로덕션 환경 (Spring Boot에서 제공)
fetch('/api/game/titles')  // 같은 도메인에서 직접 호출
```

## 문제 해결

### CORS 오류
- `application.yml`의 `cors.allowed-origins` 설정 확인
- 개발 시: `http://localhost:5173` 추가

### 빌드 후 페이지가 안 보임
- `src/main/resources/static`에 빌드 결과물이 있는지 확인
- Spring Boot 재시작

### 라우팅이 작동하지 않음
- `WebConfig.java`의 `addViewControllers` 설정 확인
- 모든 경로가 `index.html`로 리다이렉트되는지 확인

