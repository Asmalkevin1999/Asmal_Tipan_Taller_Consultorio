package com.reservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reservas.config.CorsConfig;
import com.reservas.config.SecurityConfig;
import com.reservas.dto.ReservaRequest;
import com.reservas.dto.ReservaResponse;
import com.reservas.service.ReservaService;

@WebMvcTest(com.reservas.controller.ReservaController.class)
@Import({SecurityConfig.class, CorsConfig.class})
@AutoConfigureMockMvc(addFilters = false)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservaService reservaService;

    @Test
    void crearReservaValidaDeberiaRetornar201() throws Exception {
        ReservaRequest request = new ReservaRequest();
        request.setNombre("Ana");
        request.setTelefono("300123");
        request.setEmail("ana@test.com");
        request.setIdServicio(1L);
        request.setFecha(LocalDate.now().plusDays(1));
        request.setHora(LocalTime.of(10, 0));

        ReservaResponse response = new ReservaResponse();
        response.setIdReserva(10L);
        response.setNombreCliente("Ana");
        response.setEstado("Pendiente");

        when(reservaService.crearReserva(any(ReservaRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/reservas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreCliente").value("Ana"));
    }

    @Test
    void obtenerReservaInexistenteDeberiaRetornar404() throws Exception {
        when(reservaService.obtenerReservaPorId(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/reservas/999"))
                .andExpect(status().isNotFound());
    }
}
