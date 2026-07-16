package com.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.reservas.dto.ReservaRequest;
import com.reservas.dto.ReservaResponse;
import com.reservas.entity.Reserva;
import com.reservas.entity.Servicio;
import com.reservas.entity.Usuario;
import com.reservas.repository.ReservaRepository;
import com.reservas.repository.ServicioRepository;
import com.reservas.repository.UsuarioRepository;
import com.reservas.service.ReservaService;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void crearReservaConServicioValidoDeberiaGuardarYResponder() {
        ReservaRequest request = new ReservaRequest();
        request.setNombre("Carlos");
        request.setTelefono("300123");
        request.setEmail("carlos@test.com");
        request.setIdServicio(1L);
        request.setFecha(LocalDate.now().plusDays(1));
        request.setHora(LocalTime.of(10, 0));
        request.setObservaciones("Primera reserva");

        Usuario usuario = new Usuario("Carlos", "300123", "carlos@test.com", Usuario.Rol.CLIENTE);
        usuario.setIdUsuario(99L);
        Servicio servicio = new Servicio("Corte", 20, "Servicio de prueba");
        servicio.setIdServicio(1L);
        Reserva reservaGuardada = new Reserva(usuario, servicio, request.getFecha(), request.getHora());
        reservaGuardada.setIdReserva(123L);
        reservaGuardada.setEstado("Pendiente");

        when(usuarioRepository.findByEmail("carlos@test.com")).thenReturn(Optional.of(usuario));
        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        ReservaResponse response = reservaService.crearReserva(request);

        assertNotNull(response);
        assertEquals("Carlos", response.getNombreCliente());
        assertEquals("Corte", response.getNombreServicio());
        assertEquals("Pendiente", response.getEstado());
        verify(reservaRepository).save(any(Reserva.class));
    }

    @Test
    void confirmarReservaInexistenteDeberiaLanzarError() {
        when(reservaRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> reservaService.confirmarReserva(999L));

        assertEquals("Reserva no encontrada", ex.getMessage());
    }
}
