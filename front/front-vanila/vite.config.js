import { defineConfig } from 'vite';
import { resolve, relative } from 'path';
import { readdirSync, statSync } from 'fs';

/**
 * 특정 디렉토리에서 모든 JS 파일을 검색하여 동적으로 입력 파일 설정 생성
 */
function getInputFiles(dir) {
    const files = {};
    const traverse = (currentPath) => {
      const entries = readdirSync(currentPath);
      for (const entry of entries) {
        const fullPath = resolve(currentPath, entry);
        if (statSync(fullPath).isDirectory()) {
          traverse(fullPath); // 디렉토리라면 재귀적으로 탐색
        } else if (entry.endsWith('.js')) {
          const relativePath = relative(resolve(__dirname, 'src/js'), fullPath);
          const name = relativePath.replace(/\\/g, '/').replace('.js', ''); // 파일명을 키로 사용
          files[name] = fullPath; // 입력 파일로 추가
        }
      }
    };
    traverse(dir);
    return files;
  }

export default defineConfig({
    root: 'src', // HTML 디렉토리를 루트로 설정
    build: {
        rollupOptions: {
            input: getInputFiles(resolve(__dirname, 'src/js')), // src/js 내 모든 JS 파일을 자동 추가
            output: {
              entryFileNames: (chunk) => {
                const relativePath = relative(resolve(__dirname, 'src/js'), chunk.facadeModuleId);
                return `js/${relativePath}`; // 상대 경로를 기준으로 js/account 경로 유지
              },
              assetFileNames: 'assets/[name][extname]', // 추가 리소스 저장 규칙
            },
          },
      outDir: '../../../backend/src/main/resources/static/', // Spring Boot 정적 리소스 위치로 빌드
      emptyOutDir: false, // 빌드 시 static 폴더를 비우지 않도록 설정
    },
  });