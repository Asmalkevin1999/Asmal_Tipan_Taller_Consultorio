# Evidencias de Pruebas

## Backend
- `mvn test` ejecutado con Maven 3.9.14 usando JDK 21.
- Se verificó la ejecución de pruebas unitarias y de integración del backend con el comando de Maven.

## Frontend
- `npx playwright test --config=playwright.config.js` ejecutado y completado con 4 pruebas pasando.
- Se corrigió la asociación `htmlFor` / `id` en `frontend/src/pages/Login.js` para que Playwright encuentre correctamente los campos de login.

## Arreglos y mejoras
- Se restauró `frontend/public/index.html` para permitir iniciar el servidor React.
- Se confirmó que Playwright descargó navegadores en `C:\Users\krath\AppData\Local\ms-playwright`.
- El backend usa H2 en memoria con inicialización de datos en `backend/src/main/resources/data.sql` para pruebas locales.

## Resultados
- Backend: pruebas de Maven completadas sin errores.
- Frontend: 4/4 pruebas Playwright pasan exitosamente.
