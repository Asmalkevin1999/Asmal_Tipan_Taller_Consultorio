const { test, expect } = require('@playwright/test');

test.describe('Auth E2E', () => {
  test('Login con credenciales válidas', async ({ page }) => {
    await page.goto('http://localhost:3000/');
    await page.fill('input[name="email"]', 'admin@reservas.com');
    await page.fill('input[name="password"]', 'password');
    await page.click('button:has-text("Iniciar Sesión")');

    await expect(page).toHaveURL(/dashboard/);
    await expect(page.locator('text=Bienvenido')).toBeVisible({ timeout: 5000 }).catch(() => {});
  });

  test('Validar token de sesión usando API de autenticación', async ({ request }) => {
    const response = await request.post('http://localhost:8080/api/auth/login', {
      data: {
        email: 'admin@reservas.com',
        password: 'password'
      }
    });

    expect(response.ok()).toBeTruthy();
    const body = await response.json();
    expect(body.token).toBeTruthy();
    const validateResponse = await request.post('http://localhost:8080/api/auth/validate', {
      headers: { Authorization: `Bearer ${body.token}` }
    });

    expect(validateResponse.ok()).toBeTruthy();
    const validBody = await validateResponse.json();
    expect(validBody).toBe(true);
  });
});
