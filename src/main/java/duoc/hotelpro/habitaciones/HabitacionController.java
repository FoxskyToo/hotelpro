package duoc.hotelpro.habitaciones;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habitaciones")
@RequiredArgsConstructor
public class HabitacionController {

    private final HabitacionService habitacionService;

    @GetMapping
    public ResponseEntity<List<Habitacion>> listar() {
        return ResponseEntity.ok(habitacionService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Habitacion> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(habitacionService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Habitacion> guardar(@Valid @RequestBody HabitacionDTO dto) {
        return ResponseEntity.ok(habitacionService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Habitacion> actualizar(@PathVariable Long id, @Valid @RequestBody HabitacionDTO dto) {
        return ResponseEntity.ok(habitacionService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        habitacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}