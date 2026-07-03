package duoc.hotelpro.consumos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consumos")
@RequiredArgsConstructor
public class ConsumoController {

    private final ConsumoService consumoService;

    @GetMapping
    public ResponseEntity<List<Consumo>> listar() {
        return ResponseEntity.ok(consumoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consumo> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(consumoService.buscarPorId(id));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Consumo>> listarPorReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(consumoService.listarPorReserva(reservaId));
    }

    @PostMapping
    public ResponseEntity<Consumo> guardar(@Valid @RequestBody ConsumoDTO dto) {
        return ResponseEntity.ok(consumoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consumo> actualizar(@PathVariable Long id, @Valid @RequestBody ConsumoDTO dto) {
        return ResponseEntity.ok(consumoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        consumoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}