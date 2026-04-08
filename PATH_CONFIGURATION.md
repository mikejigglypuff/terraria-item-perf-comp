# Vue 빌드 경로 설정 가이드

## 경로 구조

```
src/main/
├── js/                          # Vue.js 소스 코드
│   ├── index.html              # 개발용 HTML (상대 경로 사용)
│   ├── main.js                 # 진입점
│   ├── App.vue
│   ├── components/
│   └── vite.config.js
└── resources/
    └── static/                  # 빌드 결과물 (Spring Boot가 제공)
        ├── index.html          # 빌드된 HTML (절대 경로 사용)
        └── assets/
            ├── main-[hash].js  # 번들된 JS 파일
            └── main-[hash].css  # 번들된 CSS 파일
```

## 경로 설정 상세

### 1. Vite 설정 (`vite.config.js`)

```javascript
base: '/'
```
- **의미**: Spring Boot가 루트 경로(`/`)에서 정적 파일을 제공
- **결과**: 빌드된 HTML에서 `/assets/main.js` 형식의 절대 경로 생성
- **예시**: 
  - ✅ `/assets/main.js` (올바름)
  - ❌ `./assets/main.js` (상대 경로는 작동하지 않음)

### 2. 빌드 출력 경로

```javascript
outDir: path.resolve(__dirname, '../resources/static')
```
- **의미**: `src/main/js` → `src/main/resources/static`
- **결과**: 빌드 결과물이 Spring Boot의 정적 리소스 폴더에 생성됨

### 3. 개발 vs 프로덕션 경로

#### 개발 환경 (`src/main/js/index.html`)
```html
<script type="module" src="./main.js"></script>
```
- 상대 경로 사용 (Vite dev server가 처리)

#### 프로덕션 빌드 (`src/main/resources/static/index.html`)
```html
<script type="module" src="/assets/main.js"></script>
```
- 절대 경로 사용 (Spring Boot 루트 기준)
- Vite가 자동으로 생성

## 경로 해결 흐름

### 1. 개발 시 (Vite Dev Server)
```
http://localhost:5173/
  ↓
Vite dev server가 ./main.js를 처리
  ↓
HMR 및 모듈 변환
```

### 2. 프로덕션 빌드 후
```
http://localhost:8080/
  ↓
Spring Boot가 /index.html 제공
  ↓
브라우저가 /assets/main.js 요청
  ↓
Spring Boot가 /assets/main-[hash].js 제공
```

## 확인 사항

### ✅ 올바른 설정
1. `base: '/'` 설정됨
2. `outDir`이 `../resources/static`으로 설정됨
3. 빌드된 HTML이 `/assets/...` 형식의 절대 경로 사용
4. Spring Boot가 `/assets/**` 경로를 정적 리소스로 제공

### ❌ 문제가 있는 경우
1. `base`가 설정되지 않음 → 상대 경로 생성 → Spring Boot에서 404
2. `outDir`이 잘못됨 → 빌드 결과물이 다른 곳에 생성
3. 수동으로 만든 `index.html`이 빌드 결과물과 충돌

## 빌드 후 확인

빌드 후 다음을 확인하세요:

```bash
cd src/main/js
npm run build
```

생성된 파일 확인:
```
src/main/resources/static/
├── index.html          # <script src="/assets/main-xxx.js">
└── assets/
    ├── main-xxx.js
    └── main-xxx.css
```

`index.html`의 스크립트 경로가 `/assets/...` 형식인지 확인하세요.

## 문제 해결

### 빌드 후 404 오류
- `index.html`의 스크립트 경로 확인
- `base: '/'` 설정 확인
- Spring Boot 재시작

### 경로가 상대 경로로 생성됨
- `vite.config.js`의 `base: '/'` 확인
- 빌드 캐시 삭제 후 재빌드

