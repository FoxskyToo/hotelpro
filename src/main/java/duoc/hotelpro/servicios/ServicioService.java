package duoc.hotelpro.servicios;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> listar() {
        log.info("Listando servicios");
        return servicioRepository.findAll();
    }

    public Servicio buscarPorId(Long id) {
        log.info("Buscando servicio con ID {}", id);
        return servicioRepository.findById(id)
                .orElseThrow(() -> new ServicioException("Servicio no encontrado con ID: " + id));
    }

    public Servicio guardar(ServicioDTO dto) {
        log.info("Guardando servicio {}", dto.getNombre());

        if (servicioRepository.existsByNombre(dto.getNombre())) {
            throw new ServicioException("Ya existe un servicio con ese nombre");
        }

        Servicio servicio = new Servicio();
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return servicioRepository.save(servicio);
    }

    public Servicio actualizar(Long id, ServicioDTO dto) {
        log.info("Actualizando servicio con ID {}", id);

        Servicio servicio = buscarPorId(id);
        servicio.setNombre(dto.getNombre());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setActivo(dto.getActivo() != null ? dto.getActivo() : servicio.getActivo());

        return servicioRepository.save(servicio);
    }

    public void eliminar(Long id) {
        log.info("Eliminando servicio con ID {}", id);

        Servicio servicio = buscarPorId(id);

        try {
            servicioRepository.delete(servicio);
        } catch (Exception e) {
            log.error("No se puede eliminar servicio asociado a consumos", e);
            throw new ServicioException("No se puede eliminar el servicio porque tiene consumos asociados");
        }
    }
}