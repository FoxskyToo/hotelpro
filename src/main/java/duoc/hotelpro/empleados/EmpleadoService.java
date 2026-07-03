package duoc.hotelpro.empleados;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> listar() {
        log.info("Listando empleados");
        return empleadoRepository.findAll();
    }

    public Empleado buscarPorId(Long id) {
        log.info("Buscando empleado con ID {}", id);

        return empleadoRepository.findById(id)
                .orElseThrow(() ->
                        new EmpleadoException("Empleado no encontrado con ID: " + id));
    }

    public Empleado guardar(EmpleadoDTO dto) {

        if (empleadoRepository.existsByCorreo(dto.getCorreo())) {
            throw new EmpleadoException("Ya existe un empleado con ese correo");
        }

        Empleado empleado = new Empleado();
        empleado.setNombre(dto.getNombre());
        empleado.setCargo(dto.getCargo());
        empleado.setCorreo(dto.getCorreo());
        empleado.setTelefono(dto.getTelefono());
        empleado.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return empleadoRepository.save(empleado);
    }

    public Empleado actualizar(Long id, EmpleadoDTO dto) {

        Empleado empleado = buscarPorId(id);

        empleado.setNombre(dto.getNombre());
        empleado.setCargo(dto.getCargo());
        empleado.setCorreo(dto.getCorreo());
        empleado.setTelefono(dto.getTelefono());
        empleado.setActivo(dto.getActivo());

        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id) {

        Empleado empleado = buscarPorId(id);

        empleadoRepository.delete(empleado);
    }
}