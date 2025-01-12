import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  server: {
    host: true,  // 또는 '0.0.0.0'
    proxy: {
      '/api': {
        target: 'http://localhost:9999', // 백엔드 서버 주소
        changeOrigin: true,
        pathRewrite: { '^/api': '/api' } // 이 설정은 선택 사항입니다
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  base: '/admin/',  // 리소스의 기준 경로 설정
  build : {
    target: "es2015",
    rollupOptions: {
      output: {
        format: 'esm', // ESM 형식으로 빌드
        entryFileNames: 'admin.js',     // 메인 entry 파일 이름을 'abc.js'로 고정
        chunkFileNames: '[name].js',    // 코드 분할된 파일도 고정된 이름으로 사용 가능
        assetFileNames: (assetInfo) => {
          // CSS 파일 이름만 'admin.css'로 설정
          if (assetInfo.name && assetInfo.name.endsWith('.css')) {
            return 'admin.css';
          }
          return '[name].[ext]'; // 나머지 파일 이름은 기본 설정 유지
        },
      }
    }
  }
})
