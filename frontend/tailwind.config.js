import defaultTheme from 'tailwindcss/defaultTheme'

/** @type {import('tailwindcss').Config} */
export default {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  darkMode: 'class', // Add this back if you use dark mode

  theme: {
    extend: {
      fontFamily: {
        sans: ['Inter', ...require('tailwindcss/defaultTheme').fontFamily.sans], // Update defaultTheme import for module.exports

      },
    },
  },
  plugins: [],
}