import fs from 'fs';
import path from 'path';

// 복사할 파일 목록과 대상 경로
const filesToCopy = [
  {
    src: path.resolve('dist/admin.js'),
    dest: path.resolve('../../backend/src/main/resources/static/js/admin/admin.js'),
  },
  {
    src: path.resolve('dist/ViewGroups.js'),
    dest: path.resolve('../../backend/src/main/resources/static/js/admin/ViewGroups.js'),
  },
  {
    src: path.resolve('dist/ViewMenus.js'),
    dest: path.resolve('../../backend/src/main/resources/static/js/admin/ViewMenus.js'),
  },
  {
    src: path.resolve('dist/ViewMembers.js'),
    dest: path.resolve('../../backend/src/main/resources/static/js/admin/ViewMembers.js'),
  },
  {
    src: path.resolve('dist/admin.css'),
    dest: path.resolve('../../backend/src/main/resources/static/css/admin/admin.css'),
  },
];

filesToCopy.forEach(({ src, dest }) => {
  // 대상 디렉토리가 없으면 생성
  const destDir = path.dirname(dest);
  if (!fs.existsSync(destDir)) {
    fs.mkdirSync(destDir, { recursive: true });
  }

  // 파일 복사
  fs.copyFileSync(src, dest);
  console.log(`Copied ${src} to ${dest}`);
});
