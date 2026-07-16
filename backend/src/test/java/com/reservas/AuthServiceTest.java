package com.reservas;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.reservas.dto.LoginRequest;
import com.reservas.dto.LoginResponse;
import com.reservas.entity.Usuario;
import com.reservas.repository.UsuarioRepository;
import com.reservas.service.AuthService;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginConUsuarioAdminDeberiaRetornarToken() {
        Usuario usuario = new Usuario("Admin", "123456", "admin@test.com", Usuario.Rol.ADMINISTRADOR);
        usuario.setPassword("encodedPassword");

        when(usuarioRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        LoginResponse response = authService.login(new LoginRequest("admin@test.com", "password"));

        assertNotNull(response);
        assertEquals("admin@test.com", response.getEmail());
        assertTrue(response.getToken().startsWith("admin-token-"));
    }

    @Test
    void loginConPasswordIncorrectaDeberiaLanzarError() {
        Usuario usuario = new Usuario("Admin", "123456", "admin@test.com", Usuario.Rol.ADMINISTRADOR);
        usuario.setPassword("encodedPassword");

        when(usuarioRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> authService.login(new LoginRequest("admin@test.com", "password")));

        assertEquals("Contraseña incorrecta", ex.getMessage());
    }
}
