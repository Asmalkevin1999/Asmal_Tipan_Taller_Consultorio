const { test, expect } = require('@playwright/test');

test.describe('Reservas E2E', () => {
  test('Crear reserva desde el formulario público', async ({ page }) => {
    await page.goto('http://localhost:3000/reservar');

    await page.fill('input[name="nombre"]', 'Usuario Prueba');
    await page.fill('input[name="telefono"]', '3001234567');
    await page.fill('input[name="email"]', 'prueba@reservas.com');
    await page.selectOption('select[name="idServicio"]', { index: 1 });
    await page.fill('input[name="fecha"]', '2026-12-01');
    await page.selectOption('select[name="hora"]', '10:00');
    await page.fill('textarea[name="observaciones"]', 'Reserva de prueba');

    await page.click('button:has-text("Confirmar Reserva")');
    await expect(page.locator('text=Reserva creada exitosamente')).toBeVisible();
  });

  test('Acceder a la página de login y validar formulario', async ({ page }) => {
    await page.goto('http://localhost:3000/');
    await expect(page.getByLabel('Correo Electrónico')).toBeVisible();
    await expect(page.getByLabel('Contraseña')).toBeVisible();

    await page.fill('input[name="email"]', 'admin@reservas.com');
    await page.fill('input[name="password"]', 'password');
    await page.click('button:has-text("Iniciar Sesión")');

    await expect(page).toHaveURL(/dashboard/);
  });
});
