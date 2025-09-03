// import { fileURLToPath, URL } from 'node:url'

// import { defineConfig } from 'vite'
// import vue from '@vitejs/plugin-vue'

// // https://vitejs.dev/config/
// export default defineConfig({
//   plugins: [vue()],
//   define: {
//     // quiet warning about value not being explicitly defined
//     __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
//   },
//   resolve: {
//     alias: {
//       '@': fileURLToPath(new URL('./src', import.meta.url))
//     }
//   }
// })
import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import path from 'path';

export default defineConfig({
  plugins: [vue()],
  define: {
    __VUE_PROD_HYDRATION_MISMATCH_DETAILS__: 'false'
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  server: {
    port: 5173,
    proxy: {
      '/login': {
        target: 'http://localhost:9000',
        changeOrigin: true
      },
      '/register': {
        target: 'http://localhost:9000',
        changeOrigin: true
      },
      '/coffeeshops': {
        target: 'http://localhost:9000',
        changeOrigin: true
      },
      '/reviews': {
        target: 'http://localhost:9000',
        changeOrigin: true
      }
    }
  }
});