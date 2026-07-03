package duoc.hotelpro.pagos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient webClient;

    public List<Pago> listar() {
        log.info("Listando pagos");
        return pagoRepository.findAll();
    }

    public Pago buscarPorId(Long id) {
        log.info("Buscando pago con ID {}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> new PagoException("Pago no encontrado con ID: " + id));
    }

    public List<Pago> listarPorReserva(Long reservaId) {
        log.info("Listando pagos de reserva {}", reservaId);
        return pagoRepository.findByReservaId(reservaId);
    }

    public Pago guardar(PagoDTO dto) {
        log.info("Registrando pago para reserva {}", dto.getReservaId());

        validarReserva(dto.getReservaId());
        validarMetodoPago(dto.getMetodoPago());

        Pago pago = new Pago();
        pago.setReservaId(dto.getReservaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago().toUpperCase());
        pago.setEstado(dto.getEstado() != null ? dto.getEstado() : "PAGADO");
        pago.setFechaPago(dto.getFechaPago() != null ? dto.getFechaPago() : LocalDate.now());

        return pagoRepository.save(pago);
    }

    public Pago actualizar(Long id, PagoDTO dto) {
        log.info("Actualizando pago con ID {}", id);

        validarReserva(dto.getReservaId());
        validarMetodoPago(dto.getMetodoPago());

        Pago pago = buscarPorId(id);
        pago.setReservaId(dto.getReservaId());
        pago.setMonto(dto.getMonto());
        pago.setMetodoPago(dto.getMetodoPago().toUpperCase());
        pago.setEstado(dto.getEstado() != null ? dto.getEstado() : pago.getEstado());
        pago.setFechaPago(dto.getFechaPago() != null ? dto.getFechaPago() : pago.getFechaPago());

        return pagoRepository.save(pago);
    }

    public void eliminar(Long id) {
        log.info("Eliminando pago con ID {}", id);

        Pago pago = buscarPorId(id);
        pagoRepository.delete(pago);
    }

    private void validarReserva(Long reservaId) {
        try {
            webClient.get()
                    .uri("/api/reservas/{id}", reservaId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Reserva {} validada correctamente para pago", reservaId);
        } catch (Exception e) {
            log.error("Error real validando reserva {} para pago: {}", reservaId, e.getMessage(), e);
            throw new PagoException("La reserva indicada no existe");
        }
    }

    private void validarMetodoPago(String metodoPago) {
        String metodo = metodoPago.toUpperCase();

        if (!metodo.equals("EFECTIVO")
                && !metodo.equals("TARJETA")
                && !metodo.equals("TRANSFERENCIA")) {
            throw new PagoException("Método de pago inválido. Use EFECTIVO, TARJETA o TRANSFERENCIA");
        }
    }
}