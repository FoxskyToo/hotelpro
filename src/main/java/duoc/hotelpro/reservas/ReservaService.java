package duoc.hotelpro.reservas;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final WebClient webClient;

    public List<Reserva> listar() {
        log.info("Listando reservas");
        return reservaRepository.findAll();
    }

    public Reserva buscarPorId(Long id) {
        log.info("Buscando reserva con ID {}", id);
        return reservaRepository.findById(id)
                .orElseThrow(() -> new ReservaException("Reserva no encontrada con ID: " + id));
    }

    public Reserva guardar(ReservaDTO dto) {
        log.info("Creando reserva para cliente {} y habitación {}", dto.getClienteId(), dto.getHabitacionId());

        validarFechas(dto);
        validarCliente(dto.getClienteId());
        validarHabitacion(dto.getHabitacionId());

        Reserva reserva = new Reserva();
        reserva.setClienteId(dto.getClienteId());
        reserva.setHabitacionId(dto.getHabitacionId());
        reserva.setFechaIngreso(dto.getFechaIngreso());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setEstado(dto.getEstado() != null ? dto.getEstado() : "ACTIVA");

        return reservaRepository.save(reserva);
    }

    public Reserva actualizar(Long id, ReservaDTO dto) {
        log.info("Actualizando reserva con ID {}", id);

        validarFechas(dto);
        validarCliente(dto.getClienteId());
        validarHabitacion(dto.getHabitacionId());

        Reserva reserva = buscarPorId(id);
        reserva.setClienteId(dto.getClienteId());
        reserva.setHabitacionId(dto.getHabitacionId());
        reserva.setFechaIngreso(dto.getFechaIngreso());
        reserva.setFechaSalida(dto.getFechaSalida());
        reserva.setEstado(dto.getEstado() != null ? dto.getEstado() : reserva.getEstado());

        return reservaRepository.save(reserva);
    }

    public void eliminar(Long id) {
        log.info("Eliminando reserva con ID {}", id);

        Reserva reserva = buscarPorId(id);
        reservaRepository.delete(reserva);
    }

    private void validarFechas(ReservaDTO dto) {
        if (!dto.getFechaSalida().isAfter(dto.getFechaIngreso())) {
            throw new ReservaException("La fecha de salida debe ser posterior a la fecha de ingreso");
        }
    }

    private void validarCliente(Long clienteId) {
        try {
            webClient.get()
                    .uri("/api/clientes/{id}", clienteId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Cliente {} validado correctamente", clienteId);
        } catch (Exception e) {
            log.error("Error validando cliente {}", clienteId);
            throw new ReservaException("El cliente indicado no existe");
        }
    }

    private void validarHabitacion(Long habitacionId) {
        try {
            webClient.get()
                    .uri("/api/habitaciones/{id}", habitacionId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Habitación {} validada correctamente", habitacionId);
        } catch (Exception e) {
            log.error("Error validando habitación {}", habitacionId);
            throw new ReservaException("La habitación indicada no existe");
        }
    }
}