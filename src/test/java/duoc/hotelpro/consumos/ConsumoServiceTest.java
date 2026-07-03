package duoc.hotelpro.consumos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsumoServiceTest {

    @Mock
    private ConsumoRepository consumoRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private ConsumoService consumoService;

    @Test
    void debeGuardarConsumoCuandoDatosSonValidos() {
        ConsumoDTO dto = new ConsumoDTO();
        dto.setReservaId(1L);
        dto.setServicioId(1L);
        dto.setCantidad(2);
        dto.setTotal(new BigDecimal("24000"));

        Consumo consumoGuardado = new Consumo();
        consumoGuardado.setId(1L);
        consumoGuardado.setReservaId(1L);
        consumoGuardado.setServicioId(1L);
        consumoGuardado.setCantidad(2);
        consumoGuardado.setTotal(new BigDecimal("24000"));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{}"));

        when(consumoRepository.save(any(Consumo.class))).thenReturn(consumoGuardado);

        Consumo resultado = consumoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(2, resultado.getCantidad());
        assertEquals(new BigDecimal("24000"), resultado.getTotal());

        verify(consumoRepository, times(1)).save(any(Consumo.class));
    }

    @Test
    void debeLanzarErrorCuandoReservaNoExiste() {
        ConsumoDTO dto = new ConsumoDTO();
        dto.setReservaId(999L);
        dto.setServicioId(1L);
        dto.setCantidad(2);
        dto.setTotal(new BigDecimal("24000"));

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenThrow(new RuntimeException("404 Not Found"));

        ConsumoException exception = assertThrows(
                ConsumoException.class,
                () -> consumoService.guardar(dto)
        );

        assertEquals("La reserva indicada no existe", exception.getMessage());
        verify(consumoRepository, never()).save(any(Consumo.class));
    }
}