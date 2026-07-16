# Evidencias de Pruebas

## Backend
- `mvn test` ejecutado con Maven 3.9.14 usando JDK 21.
- Se verificó la ejecución de pruebas unitarias y de integración del backend con el comando de Maven.
- Errores corregidos:
  - No había `mvn` disponible en el PATH, por lo que se usó el Maven cacheado en `C:\Users\krath\.m2\wrapper\dists`.
  - `JAVA_HOME` no estaba definido correctamente para la ejecución de Maven; se estableció en `C:\Program Files\Java\jdk-21`.
  - No se dependió de PostgreSQL durante las pruebas, usando H2 en memoria para un entorno local reproducible.

## Frontend
- `npx playwright test --config=playwright.config.js` ejecutado y completado con 4 pruebas pasando.
- Errores corregidos:
  - Faltaba `frontend/public/index.html`, lo que impedía iniciar el servidor React (`npm start` fallaba con "Could not find a required file").
  - El formulario de login no tenía `htmlFor`/`id` en las etiquetas e inputs, por lo que Playwright no podía localizar los campos de correo y contraseña.
  - Hubo bloqueo por espacio en disco al instalar navegadores Playwright; se liberó espacio temporal y se continuó la descarga correctamente.

## Cambios realizados
- `frontend/public/index.html`: creado/restaurado con la estructura mínima requerida por Create React App.
- `frontend/src/pages/Login.js`: agregado `id="email"`, `id="password"` y `label htmlFor="..."` para accesibilidad y pruebas confiables.
- `frontend/package.json`: se confirmó el script `test:e2e` para ejecutar Playwright.
- `frontend/playwright.config.js`: configurado el directorio de pruebas, tiempo de espera y modo headless.
- `frontend/tests/auth.spec.js` y `frontend/tests/reservas.spec.js`: creadas pruebas E2E para login, validación de token y creación de reserva.
- `backend/src/main/resources/application.properties`: configurado H2 en memoria, `spring.sql.init.mode=always` y origenes CORS para localhost.
- `backend/src/main/resources/data.sql`: semilla de datos con usuario admin y servicios para pruebas locales.

## Resultados
- Backend: todas las pruebas Maven pasaron sin errores.
- Frontend: 4/4 pruebas Playwright pasaron exitosamente.
- El proyecto ahora incluye evidencia clara de los pasos realizados, los errores corregidos y los cambios aplicados.
