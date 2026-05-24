package duoc.hotelpro.habitaciones;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HabitacionService {

    private final HabitacionRepository habitacionRepository;

    public List<Habitacion> listar() {
        log.info("Listando habitaciones");
        return habitacionRepository.findAll();
    }

    public Habitacion buscarPorId(Long id) {
        log.info("Buscando habitación con ID {}", id);
        return habitacionRepository.findById(id)
                .orElseThrow(() -> new HabitacionException("Habitación no encontrada con ID: " + id));
    }

    public Habitacion guardar(HabitacionDTO dto) {
        log.info("Guardando habitación número {}", dto.getNumero());

        if (habitacionRepository.existsByNumero(dto.getNumero())) {
            throw new HabitacionException("Ya existe una habitación con ese número");
        }

        Habitacion habitacion = new Habitacion();
        habitacion.setNumero(dto.getNumero());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setDisponible(dto.getDisponible());

        return habitacionRepository.save(habitacion);
    }

    public Habitacion actualizar(Long id, HabitacionDTO dto) {
        log.info("Actualizando habitación con ID {}", id);

        Habitacion habitacion = buscarPorId(id);
        habitacion.setNumero(dto.getNumero());
        habitacion.setTipo(dto.getTipo());
        habitacion.setPrecio(dto.getPrecio());
        habitacion.setDisponible(dto.getDisponible());

        return habitacionRepository.save(habitacion);
    }

    public void eliminar(Long id) {
        log.info("Eliminando habitación con ID {}", id);

        Habitacion habitacion = buscarPorId(id);

        try {
            habitacionRepository.delete(habitacion);
        } catch (Exception e) {
            log.error("No se puede eliminar habitación con reservas asociadas", e);
            throw new HabitacionException("No se puede eliminar la habitación porque tiene reservas asociadas");
        }
    }
}