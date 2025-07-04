// vite.config.ts
import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
// Remove these imports, as they are no longer needed here:
// import tailwindcssPostcss from '@tailwindcss/postcss'
// import autoprefixer from 'autoprefixer'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
  ],
  // REMOVE THIS ENTIRE `css` BLOCK:
  // css: {
  //   postcss: {
  //     plugins: [
  //       tailwindcssPostcss,
  //       autoprefixer(),
  //     ],
  //   },
  // },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})