package duoc.hotelpro.config;

import duoc.hotelpro.auth.AuthException;
import duoc.hotelpro.clientes.ClienteException;
import duoc.hotelpro.consumos.ConsumoException;
import duoc.hotelpro.empleados.EmpleadoException;
import duoc.hotelpro.habitaciones.HabitacionException;
import duoc.hotelpro.mantenimientos.MantenimientoException;
import duoc.hotelpro.pagos.PagoException;
import duoc.hotelpro.reservas.ReservaException;
import duoc.hotelpro.servicios.ServicioException;
import duoc.hotelpro.usuarios.UsuarioException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            AuthException.class,
            ClienteException.class,
            HabitacionException.class,
            ReservaException.class,
            UsuarioException.class,
            ServicioException.class,
            PagoException.class,
            ConsumoException.class,
            EmpleadoException.class,
            MantenimientoException.class
    })
    public ResponseEntity<Map<String, Object>> manejarErroresNegocio(RuntimeException ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("fecha", LocalDateTime.now());
        error.put("estado", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Error de negocio");
        error.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> manejarValidaciones(MethodArgumentNotValidException ex) {

        Map<String, Object> error = new HashMap<>();
        Map<String, String> validaciones = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                validaciones.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        error.put("fecha", LocalDateTime.now());
        error.put("estado", HttpStatus.BAD_REQUEST.value());
        error.put("error", "Error de validación");
        error.put("validaciones", validaciones);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> manejarErrorGeneral(Exception ex) {

        Map<String, Object> error = new HashMap<>();

        error.put("fecha", LocalDateTime.now());
        error.put("estado", HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error", "Error interno del servidor");
        error.put("mensaje", ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}