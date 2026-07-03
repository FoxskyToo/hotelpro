package duoc.hotelpro.reservas;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ReservaService reservaService;

    @Test
    void debeLanzarErrorCuandoFechaSalidaEsAnteriorAFechaIngreso() {
        ReservaDTO dto = new ReservaDTO();
        dto.setClienteId(1L);
        dto.setHabitacionId(1L);
        dto.setFechaIngreso(LocalDate.of(2026, 7, 10));
        dto.setFechaSalida(LocalDate.of(2026, 7, 5));
        dto.setEstado("ACTIVA");

        ReservaException exception = assertThrows(
                ReservaException.class,
                () -> reservaService.guardar(dto)
        );

        assertEquals("La fecha de salida debe ser posterior a la fecha de ingreso", exception.getMessage());
    }

    @Test
    void debeGuardarReservaCuandoDatosSonValidos() {
        ReservaDTO dto = new ReservaDTO();
        dto.setClienteId(1L);
        dto.setHabitacionId(1L);
        dto.setFechaIngreso(LocalDate.of(2026, 7, 10));
        dto.setFechaSalida(LocalDate.of(2026, 7, 15));
        dto.setEstado("ACTIVA");

        Reserva reservaGuardada = new Reserva();
        reservaGuardada.setId(1L);
        reservaGuardada.setClienteId(dto.getClienteId());
        reservaGuardada.setHabitacionId(dto.getHabitacionId());
        reservaGuardada.setFechaIngreso(dto.getFechaIngreso());
        reservaGuardada.setFechaSalida(dto.getFechaSalida());
        reservaGuardada.setEstado(dto.getEstado());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{}"));

        when(reservaRepository.save(any(Reserva.class))).thenReturn(reservaGuardada);

        Reserva resultado = reservaService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("ACTIVA", resultado.getEstado());
        verify(reservaRepository, times(1)).save(any(Reserva.class));
    }
}