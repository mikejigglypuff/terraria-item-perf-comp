import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { fileURLToPath, URL } from 'node:url'
import path from 'path'

// ES 모듈에서 __dirname 대체
const __dirname = path.dirname(fileURLToPath(import.meta.url))

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  
  // Spring Boot는 루트 경로(/)에서 정적 파일을 제공하므로 base를 '/'로 설정
  // 이렇게 하면 빌드된 파일의 경로 참조가 /assets/main.js 형식이 됨
  base: '/',
  
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  
  // 빌드 결과물을 Spring Boot의 static 폴더로 출력
  build: {
    // src/main/js에서 상대 경로로 src/main/resources/static을 가리킴
    outDir: path.resolve(__dirname, '../resources/static'),
    emptyOutDir: true,
    // 빌드된 파일의 경로를 절대 경로로 생성 (Spring Boot 루트 기준)
    assetsDir: 'assets',
    // SPA 라우팅을 위한 설정
    rollupOptions: {
      input: {
        main: path.resolve(__dirname, 'index.html')
      }
    }
  },
  
  // 개발 서버 설정 (개발 시 별도 포트에서 실행)
  server: {
    port: 5173,
    proxy: {
      // API 요청을 Spring Boot 서버로 프록시
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  }
})
