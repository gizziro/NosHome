import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'),
      '@shared': resolve(__dirname, 'src/shared')
    }
  },
  server: { 
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true
      }
    }
  },
  build: {
    rollupOptions: {
      input: {
        menu: resolve(__dirname, 'src/menu-manage/index.html'),
        user: resolve(__dirname, 'src/user-manage/index.html')
      },
      output: {
        entryFileNames: (chunkInfo) => {
          const name = chunkInfo.name
          if (name.includes('vendor') || name.includes('chunk-')) {
            return 'js/common/[name].[hash].js'
          }
          return 'js/[name]/[name].[hash].js'
        },
        chunkFileNames: 'js/common/[name].[hash].js',
        assetFileNames: (assetInfo) => {
          const { name } = assetInfo
          const extType = assetInfo.name.split('.').at(-1)
          
          if (extType === 'css') {
            if (name.includes('vendor') || name.includes('chunk-')) {
              return 'css/common/[name].[hash][extname]'
            }
            const matchName = name.match(/src_([\w-]+)_/)
            const folder = matchName ? matchName[1] : 'common'
            return `css/${folder}/[name].[hash][extname]`
          }
          
          // 이미지나 폰트 등 기타 assets
          return 'assets/[name].[hash][extname]'
        }
      }
    }
  }

  
})
