package duoc.hotelpro.clientes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listar() {
        log.info("Listando clientes");
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        log.info("Buscando cliente con ID {}", id);
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteException("Cliente no encontrado con ID: " + id));
    }

    public Cliente guardar(ClienteDTO dto) {
        log.info("Guardando nuevo cliente con correo {}", dto.getCorreo());

        if (clienteRepository.existsByCorreo(dto.getCorreo())) {
            throw new ClienteException("Ya existe un cliente con ese correo");
        }

        Cliente cliente = new Cliente();
        cliente.setNombre(dto.getNombre());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());

        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, ClienteDTO dto) {
        log.info("Actualizando cliente con ID {}", id);

        Cliente cliente = buscarPorId(id);
        cliente.setNombre(dto.getNombre());
        cliente.setCorreo(dto.getCorreo());
        cliente.setTelefono(dto.getTelefono());

        return clienteRepository.save(cliente);
    }

    public void eliminar(Long id) {
        log.info("Eliminando cliente con ID {}", id);

        Cliente cliente = buscarPorId(id);

        try {
            clienteRepository.delete(cliente);
        } catch (Exception e) {
            log.error("No se puede eliminar cliente con reservas asociadas", e);
            throw new ClienteException("No se puede eliminar el cliente porque tiene reservas asociadas");
        }
    }
}