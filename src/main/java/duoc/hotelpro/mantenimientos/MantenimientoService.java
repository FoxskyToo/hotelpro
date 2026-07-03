package duoc.hotelpro.mantenimientos;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final WebClient webClient;

    public List<Mantenimiento> listar() {
        log.info("Listando mantenimientos");
        return mantenimientoRepository.findAll();
    }

    public Mantenimiento buscarPorId(Long id) {
        log.info("Buscando mantenimiento con ID {}", id);
        return mantenimientoRepository.findById(id)
                .orElseThrow(() -> new MantenimientoException("Mantenimiento no encontrado con ID: " + id));
    }

    public List<Mantenimiento> listarPorHabitacion(Long habitacionId) {
        log.info("Listando mantenimientos de habitación {}", habitacionId);
        return mantenimientoRepository.findByHabitacionId(habitacionId);
    }

    public List<Mantenimiento> listarPorEmpleado(Long empleadoId) {
        log.info("Listando mantenimientos de empleado {}", empleadoId);
        return mantenimientoRepository.findByEmpleadoId(empleadoId);
    }

    public Mantenimiento guardar(MantenimientoDTO dto) {
        log.info("Registrando mantenimiento para habitación {} y empleado {}",
                dto.getHabitacionId(), dto.getEmpleadoId());

        validarHabitacion(dto.getHabitacionId());
        validarEmpleado(dto.getEmpleadoId());
        validarEstado(dto.getEstado());

        Mantenimiento mantenimiento = new Mantenimiento();
        mantenimiento.setHabitacionId(dto.getHabitacionId());
        mantenimiento.setEmpleadoId(dto.getEmpleadoId());
        mantenimiento.setDescripcion(dto.getDescripcion());
        mantenimiento.setEstado(dto.getEstado() != null ? dto.getEstado().toUpperCase() : "PENDIENTE");
        mantenimiento.setFechaMantenimiento(dto.getFechaMantenimiento());

        return mantenimientoRepository.save(mantenimiento);
    }

    public Mantenimiento actualizar(Long id, MantenimientoDTO dto) {
        log.info("Actualizando mantenimiento con ID {}", id);

        validarHabitacion(dto.getHabitacionId());
        validarEmpleado(dto.getEmpleadoId());
        validarEstado(dto.getEstado());

        Mantenimiento mantenimiento = buscarPorId(id);
        mantenimiento.setHabitacionId(dto.getHabitacionId());
        mantenimiento.setEmpleadoId(dto.getEmpleadoId());
        mantenimiento.setDescripcion(dto.getDescripcion());
        mantenimiento.setEstado(dto.getEstado() != null ? dto.getEstado().toUpperCase() : mantenimiento.getEstado());
        mantenimiento.setFechaMantenimiento(dto.getFechaMantenimiento());

        return mantenimientoRepository.save(mantenimiento);
    }

    public void eliminar(Long id) {
        log.info("Eliminando mantenimiento con ID {}", id);

        Mantenimiento mantenimiento = buscarPorId(id);
        mantenimientoRepository.delete(mantenimiento);
    }

    private void validarHabitacion(Long habitacionId) {
        try {
            webClient.get()
                    .uri("/api/habitaciones/{id}", habitacionId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Habitación {} validada correctamente para mantenimiento", habitacionId);
        } catch (Exception e) {
            log.error("Error validando habitación {} para mantenimiento", habitacionId);
            throw new MantenimientoException("La habitación indicada no existe");
        }
    }

    private void validarEmpleado(Long empleadoId) {
        try {
            webClient.get()
                    .uri("/api/empleados/{id}", empleadoId)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Empleado {} validado correctamente para mantenimiento", empleadoId);
        } catch (Exception e) {
            log.error("Error validando empleado {} para mantenimiento", empleadoId);
            throw new MantenimientoException("El empleado indicado no existe");
        }
    }

    private void validarEstado(String estado) {
        if (estado == null) {
            return;
        }

        String estadoUpper = estado.toUpperCase();

        if (!estadoUpper.equals("PENDIENTE")
                && !estadoUpper.equals("EN_PROCESO")
                && !estadoUpper.equals("FINALIZADO")) {
            throw new MantenimientoException("Estado inválido. Use PENDIENTE, EN_PROCESO o FINALIZADO");
        }
    }
}