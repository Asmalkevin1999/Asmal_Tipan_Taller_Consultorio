package com.reservas.PRUEBAS_ASMAL_TIPAN;

import com.reservas.dto.ReservaRequest;
import com.reservas.dto.ReservaResponse;
import com.reservas.entity.Reserva;
import com.reservas.entity.Servicio;
import com.reservas.entity.Usuario;
import com.reservas.repository.ReservaRepository;
import com.reservas.repository.ServicioRepository;
import com.reservas.repository.UsuarioRepository;
import com.reservas.service.ReservaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservaServiceUnitTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void crearReservaConDatosValidosDebeGuardarReserva() {
        ReservaRequest request = new ReservaRequest();
        request.setNombre("Carlos");
        request.setTelefono("300123");
        request.setEmail("carlos@test.com");
        request.setIdServicio(1L);
        request.setFecha(LocalDate.now().plusDays(1));
        request.setHora(LocalTime.of(10, 0));
        request.setObservaciones("Primera reserva");

        Usuario usuario = new Usuario("Carlos", "300123", "carlos@test.com", Usuario.Rol.CLIENTE);
        Usuario reservaCliente = new Usuario();
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
    void confirmarReservaInexistenteLanzaRuntimeException() {
        when(reservaRepository.findById(999L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> reservaService.confirmarReserva(999L));

        assertEquals("Reserva no encontrada", exception.getMessage());
    }
}
