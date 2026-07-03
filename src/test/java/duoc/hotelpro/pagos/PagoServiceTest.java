package duoc.hotelpro.pagos;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private PagoService pagoService;

    @Test
    void debeLanzarErrorCuandoMetodoPagoEsInvalido() {
        PagoDTO dto = new PagoDTO();
        dto.setReservaId(1L);
        dto.setMonto(new BigDecimal("150000"));
        dto.setMetodoPago("CHEQUE");

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{}"));

        PagoException exception = assertThrows(
                PagoException.class,
                () -> pagoService.guardar(dto)
        );

        assertEquals("Método de pago inválido. Use EFECTIVO, TARJETA o TRANSFERENCIA", exception.getMessage());
    }

    @Test
    void debeGuardarPagoCuandoDatosSonValidos() {
        PagoDTO dto = new PagoDTO();
        dto.setReservaId(1L);
        dto.setMonto(new BigDecimal("150000"));
        dto.setMetodoPago("TARJETA");

        Pago pagoGuardado = new Pago();
        pagoGuardado.setId(1L);
        pagoGuardado.setReservaId(1L);
        pagoGuardado.setMonto(new BigDecimal("150000"));
        pagoGuardado.setMetodoPago("TARJETA");
        pagoGuardado.setEstado("PAGADO");
        pagoGuardado.setFechaPago(LocalDate.now());

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), anyLong())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(String.class)).thenReturn(Mono.just("{}"));

        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);

        Pago resultado = pagoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("TARJETA", resultado.getMetodoPago());
        assertEquals("PAGADO", resultado.getEstado());

        verify(pagoRepository, times(1)).save(any(Pago.class));
    }
}