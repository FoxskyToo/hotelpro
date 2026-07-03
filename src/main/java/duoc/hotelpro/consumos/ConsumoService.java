package duoc.hotelpro.consumos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumoService {

    private final ConsumoRepository consumoRepository;
    private final WebClient webClient;

    public List<Consumo> listar() {
        log.info("Listando consumos");
        return consumoRepository.findAll();
    }

    public Consumo buscarPorId(Long id) {
        log.info("Buscando consumo con ID {}", id);
        return consumoRepository.findById(id)
                .orElseThrow(() -> new ConsumoException("Consumo no encontrado con ID: " + id));
    }

    public List<Consumo> listarPorReserva(Long reservaId) {
        log.info("Listando consumos de reserva {}", reservaId);
        return consumoRepository.findByReservaId(reservaId);
    }

    public Consumo guardar(ConsumoDTO dto) {
        log.info("Registrando consumo para reserva {} y servicio {}", dto.getReservaId(), dto.getServicioId());

        validarReserva(dto.getReservaId());
        validarServicio(dto.getServicioId());

        Consumo consumo = new Consumo();
        consumo.setReservaId(dto.getReservaId());
        consumo.setServicioId(dto.getServicioId());
        consumo.setCantidad(dto.getCantidad());
        consumo.setTotal(dto.getTotal());

        return consumoRepository.save(consumo);
    }

    public Consumo actualizar(Long id, ConsumoDTO dto) {
        log.info("Actualizando consumo con ID {}", id);

        validarReserva(dto.getReservaId());
        validarServicio(dto.getServicioId());

        Consumo consumo = buscarPorId(id);
        consumo.setReservaId(dto.getReservaId());
        consumo.setServicioId(dto.getServicioId());
        consumo.setCantidad(dto.getCantidad());
        consumo.setTotal(dto.getTotal());

        return consumoRepository.save(consumo);
    }

    public void eliminar(Long id) {
        log.info("Eliminando consumo con ID {}", id);

        Consumo consumo = buscarPorId(id);
        consumoRepository.delete(consumo);
    }

    private void validarReserva(Long reservaId) {
        try {
            webClient.get()
                    .uri("/api/reservas/{id}", reservaId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Reserva {} validada correctamente para consumo", reservaId);
        } catch (Exception e) {
            log.error("Error validando reserva {} para consumo", reservaId);
            throw new ConsumoException("La reserva indicada no existe");
        }
    }

    private void validarServicio(Long servicioId) {
        try {
            webClient.get()
                    .uri("/api/servicios/{id}", servicioId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Servicio {} validado correctamente para consumo", servicioId);
        } catch (Exception e) {
            log.error("Error validando servicio {} para consumo", servicioId);
            throw new ConsumoException("El servicio indicado no existe");
        }
    }
}