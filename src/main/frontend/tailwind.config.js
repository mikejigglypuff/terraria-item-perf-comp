/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{vue,js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'terraria-blue': '#2c3e50', // 기본 짙은 파란색
        'terraria-dark-blue': '#1a2a3a', // 더 짙은 파란색 (선택창 배경)
        'terraria-ui-border': '#4a5a6a', // UI 테두리 색상
      },
      fontFamily: {
        'terraria': ['Andy Bold', 'sans-serif'], // 테라리아 폰트 (Andy Bold)
      },
      backgroundImage: {
        'terraria-panel': "linear-gradient(to bottom, #3a4a5a, #1a2a3a)",
      }
    },
  },
  plugins: [],
}
