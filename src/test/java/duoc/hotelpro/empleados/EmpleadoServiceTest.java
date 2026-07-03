package duoc.hotelpro.empleados;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoService empleadoService;

    @Test
    void debeGuardarEmpleadoCuandoCorreoNoExiste() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre("Empleado Prueba");
        dto.setCargo("Mantencion");
        dto.setCorreo("empleado@test.com");
        dto.setTelefono("911111111");
        dto.setActivo(true);

        Empleado empleadoGuardado = new Empleado();
        empleadoGuardado.setId(1L);
        empleadoGuardado.setNombre(dto.getNombre());
        empleadoGuardado.setCargo(dto.getCargo());
        empleadoGuardado.setCorreo(dto.getCorreo());
        empleadoGuardado.setTelefono(dto.getTelefono());
        empleadoGuardado.setActivo(dto.getActivo());

        when(empleadoRepository.existsByCorreo(dto.getCorreo())).thenReturn(false);
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleadoGuardado);

        Empleado resultado = empleadoService.guardar(dto);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("empleado@test.com", resultado.getCorreo());

        verify(empleadoRepository, times(1)).save(any(Empleado.class));
    }

    @Test
    void debeLanzarErrorCuandoCorreoYaExiste() {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setNombre("Empleado Prueba");
        dto.setCargo("Mantencion");
        dto.setCorreo("empleado@test.com");

        when(empleadoRepository.existsByCorreo(dto.getCorreo())).thenReturn(true);

        EmpleadoException exception = assertThrows(
                EmpleadoException.class,
                () -> empleadoService.guardar(dto)
        );

        assertEquals("Ya existe un empleado con ese correo", exception.getMessage());
        verify(empleadoRepository, never()).save(any(Empleado.class));
    }
}