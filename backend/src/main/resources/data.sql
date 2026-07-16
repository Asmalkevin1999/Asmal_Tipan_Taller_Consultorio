INSERT INTO usuarios (nombre, telefono, email, rol, password, activo, departamento, created_at, updated_at)
VALUES ('Administrador', '1234567890', 'admin@reservas.com', 'ADMINISTRADOR', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', true, 'Administración', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO servicios (nombre_servicio, precio, descripcion, duracion_minutos, activo, created_at)
VALUES
('Consulta General', 25, 'Consulta médica general', 30, true, CURRENT_TIMESTAMP),
('Limpieza Dental', 40, 'Limpieza y revisión dental', 45, true, CURRENT_TIMESTAMP),
('Corte de Cabello', 5, 'Corte de cabello básico', 30, true, CURRENT_TIMESTAMP),
('Manicure', 10, 'Manicure completo', 45, true, CURRENT_TIMESTAMP),
('Masaje Relajante', 30, 'Masaje de relajación', 60, true, CURRENT_TIMESTAMP);
