package duoc.hotelpro.mantenimientos;

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
class MantenimientoServiceTest {

    @Mock
    private MantenimientoRepository mantenimientoRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private MantenimientoService mantenimientoService;

    @Test
    void debeGuardarMantenimientoCuandoDatosSonValidos() {

        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setHabitacionId(1L);
        dto.setEmpleadoId(1L);
        dto.setDescripcion("Cambio de cerradura");
        dto.setEstado("PENDIENTE");
        dto.setFechaMantenimiento(LocalDate.now());

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setId(1L);
        mantenimiento.setHabitacionId(1L);
        mantenimiento.setEmpleadoId(1L);
        mantenimiento.setDescripcion("Cambio de cerradura");
        mantenimiento.setEstado("PENDIENTE");
        mantenimiento.setFechaMantenimiento(LocalDate.now());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{}"));

        when(mantenimientoRepository.save(any(Mantenimiento.class)))
                .thenReturn(mantenimiento);

        Mantenimiento resultado = mantenimientoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("PENDIENTE", resultado.getEstado());

        verify(mantenimientoRepository, times(1)).save(any(Mantenimiento.class));
    }

    @Test
    void debeLanzarErrorCuandoEstadoEsInvalido() {

        MantenimientoDTO dto = new MantenimientoDTO();
        dto.setHabitacionId(1L);
        dto.setEmpleadoId(1L);
        dto.setDescripcion("Cambio de puerta");
        dto.setEstado("CANCELADO");
        dto.setFechaMantenimiento(LocalDate.now());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{}"));

        MantenimientoException exception = assertThrows(
                MantenimientoException.class,
                () -> mantenimientoService.guardar(dto)
        );

        assertEquals(
                "Estado inválido. Use PENDIENTE, EN_PROCESO o FINALIZADO",
                exception.getMessage()
        );

        verify(mantenimientoRepository, never()).save(any(Mantenimiento.class));
    }
}