package duoc.hotelpro.clientes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void debeGuardarClienteCuandoCorreoNoExiste() {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Cliente Prueba");
        dto.setCorreo("cliente@test.com");
        dto.setTelefono("912345678");

        Cliente clienteGuardado = new Cliente();
        clienteGuardado.setId(1L);
        clienteGuardado.setNombre(dto.getNombre());
        clienteGuardado.setCorreo(dto.getCorreo());
        clienteGuardado.setTelefono(dto.getTelefono());

        when(clienteRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteGuardado);

        Cliente resultado = clienteService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("cliente@test.com", resultado.getCorreo());

        verify(clienteRepository, times(1)).save(any(Cliente.class));
    }

    @Test
    void debeLanzarErrorCuandoCorreoYaExiste() {
        ClienteDTO dto = new ClienteDTO();
        dto.setNombre("Cliente Prueba");
        dto.setCorreo("cliente@test.com");
        dto.setTelefono("912345678");

        when(clienteRepository.existsByCorreo(dto.getCorreo())).thenReturn(true);

        ClienteException exception = assertThrows(
                ClienteException.class,
                () -> clienteService.guardar(dto)
        );

        assertEquals("Ya existe un cliente con ese correo", exception.getMessage());
        verify(clienteRepository, never()).save(any(Cliente.class));
    }
}