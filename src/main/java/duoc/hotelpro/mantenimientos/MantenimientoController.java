package duoc.hotelpro.mantenimientos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimientos")
@RequiredArgsConstructor
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    @GetMapping
    public ResponseEntity<List<Mantenimiento>> listar() {
        return ResponseEntity.ok(mantenimientoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(mantenimientoService.buscarPorId(id));
    }

    @GetMapping("/habitacion/{habitacionId}")
    public ResponseEntity<List<Mantenimiento>> listarPorHabitacion(@PathVariable Long habitacionId) {
        return ResponseEntity.ok(mantenimientoService.listarPorHabitacion(habitacionId));
    }

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Mantenimiento>> listarPorEmpleado(@PathVariable Long empleadoId) {
        return ResponseEntity.ok(mantenimientoService.listarPorEmpleado(empleadoId));
    }

    @PostMapping
    public ResponseEntity<Mantenimiento> guardar(@Valid @RequestBody MantenimientoDTO dto) {
        return ResponseEntity.ok(mantenimientoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mantenimiento> actualizar(@PathVariable Long id,
                                                    @Valid @RequestBody MantenimientoDTO dto) {
        return ResponseEntity.ok(mantenimientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        mantenimientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}