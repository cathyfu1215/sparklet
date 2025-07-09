import defaultTheme from 'tailwindcss/defaultTheme'

/** @type {import('tailwindcss').Config} */
export default {
  darkMode: 'class',
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],

  theme: {
    extend: {
      colors: {
        // Macaroon Color Palette
        macaroon: {
          peach: {
            50: '#fdf2f8',
            100: '#fce7f3',
            200: '#ffd4ca',
            300: '#ffb5a7',
            400: '#ff9482',
            500: '#ff7961',
            600: '#e85d49',
            700: '#d14839',
            800: '#b93c2e',
            900: '#a03429',
          },
          mint: {
            50: '#f0fdfa',
            100: '#ccfbf1',
            200: '#c8f2e0',
            300: '#a8e6cf',
            400: '#88d8a3',
            500: '#6bc583',
            600: '#52b069',
            700: '#459c57',
            800: '#3a8248',
            900: '#316b3d',
          },
          lavender: {
            50: '#faf5ff',
            100: '#f3e8ff',
            200: '#ddc4f5',
            300: '#c7a8f0',
            400: '#b18ce8',
            500: '#9c70df',
            600: '#8758d4',
            700: '#7345c1',
            800: '#5f39a1',
            900: '#4e2f83',
          },
          vanilla: {
            50: '#fffbeb',
            100: '#fff8e1',
            200: '#fff2cc',
            300: '#ffeb9c',
            400: '#ffe066',
            500: '#ffd23a',
            600: '#ffbf00',
            700: '#e6a600',
            800: '#cc9500',
            900: '#b38300',
          },
          blush: {
            50: '#fdf2f8',
            100: '#fce4ec',
            200: '#f8d7da',
            300: '#f5c6cb',
            400: '#f1b0b7',
            500: '#ed969e',
            600: '#e87c85',
            700: '#e0626c',
            800: '#d74853',
            900: '#ce2e3a',
          },
        },
        // Enhanced neutral palette
        neutral: {
          50: '#fefefe',
          100: '#f9f9f9',
          200: '#f2f2f2',
          300: '#e5e5e5',
          400: '#d0d0d0',
          500: '#9ca3af',
          600: '#6b7280',
          700: '#4b5563',
          800: '#374151',
          900: '#1f2937',
        },
      },
      fontFamily: {
        sans: ['Inter', ...defaultTheme.fontFamily.sans],
      },
      fontSize: {
        'xs': ['0.75rem', { lineHeight: '1rem' }],
        'sm': ['0.875rem', { lineHeight: '1.25rem' }],
        'base': ['1rem', { lineHeight: '1.5rem' }],
        'lg': ['1.125rem', { lineHeight: '1.75rem' }],
        'xl': ['1.25rem', { lineHeight: '1.75rem' }],
        '2xl': ['1.5rem', { lineHeight: '2rem' }],
        '3xl': ['1.875rem', { lineHeight: '2.25rem' }],
        '4xl': ['2.25rem', { lineHeight: '2.5rem' }],
        '5xl': ['3rem', { lineHeight: '1' }],
        '6xl': ['3.75rem', { lineHeight: '1' }],
      },
      spacing: {
        '18': '4.5rem',
        '72': '18rem',
        '84': '21rem',
        '96': '24rem',
      },
      borderRadius: {
        'xl': '0.75rem',
        '2xl': '1rem',
        '3xl': '1.5rem',
      },
      boxShadow: {
        'soft': '0 4px 20px rgba(0, 0, 0, 0.08)',
        'medium': '0 8px 30px rgba(0, 0, 0, 0.12)',
        'large': '0 12px 40px rgba(0, 0, 0, 0.15)',
        'macaroon-peach': '0 4px 12px rgba(255, 181, 167, 0.4)',
        'macaroon-mint': '0 4px 12px rgba(168, 230, 207, 0.4)',
        'macaroon-lavender': '0 4px 12px rgba(199, 168, 240, 0.4)',
      },
      animation: {
        'float': 'float 3s ease-in-out infinite',
        'fade-in': 'fadeIn 0.5s ease-in-out',
        'slide-up': 'slideUp 0.3s ease-out',
      },
      keyframes: {
        float: {
          '0%, 100%': { transform: 'translateY(0)' },
          '50%': { transform: 'translateY(-10px)' },
        },
        fadeIn: {
          '0%': { opacity: '0' },
          '100%': { opacity: '1' },
        },
        slideUp: {
          '0%': { transform: 'translateY(20px)', opacity: '0' },
          '100%': { transform: 'translateY(0)', opacity: '1' },
        },
      },
    },
  },
  plugins: [],
}