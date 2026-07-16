package com.reservas.PRUEBAS_ASMAL_TIPAN;

import com.reservas.dto.LoginRequest;
import com.reservas.dto.LoginResponse;
import com.reservas.entity.Usuario;
import com.reservas.repository.UsuarioRepository;
import com.reservas.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceUnitTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void loginConCredencialesValidasRetornaToken() {
        Usuario usuario = new Usuario("Admin", "1234567890", "admin@test.com", Usuario.Rol.ADMINISTRADOR);
        usuario.setPassword("encodedPassword");

        when(usuarioRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(true);

        LoginResponse response = authService.login(new LoginRequest("admin@test.com", "password"));

        assertNotNull(response);
        assertTrue(response.getToken().startsWith("admin-token-"));
        assertEquals("admin@test.com", response.getEmail());
    }

    @Test
    void loginConPasswordIncorrectaLanzaRuntimeException() {
        Usuario usuario = new Usuario("Admin", "1234567890", "admin@test.com", Usuario.Rol.ADMINISTRADOR);
        usuario.setPassword("encodedPassword");

        when(usuarioRepository.findByEmail("admin@test.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("password", "encodedPassword")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> authService.login(new LoginRequest("admin@test.com", "password")));

        assertEquals("Contraseña incorrecta", exception.getMessage());
    }
}
