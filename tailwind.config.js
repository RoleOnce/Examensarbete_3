/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,js}",
    "./src/main/resources/templates/**/*.html",  // För Thymeleaf templates
    "./src/main/resources/static/**/*.{html,js}" // För statiska filer
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}