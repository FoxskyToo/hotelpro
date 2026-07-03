package duoc.hotelpro.pagos;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.buscarPorId(id));
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<Pago>> listarPorReserva(@PathVariable Long reservaId) {
        return ResponseEntity.ok(pagoService.listarPorReserva(reservaId));
    }

    @PostMapping
    public ResponseEntity<Pago> guardar(@Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(pagoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pago> actualizar(@PathVariable Long id, @Valid @RequestBody PagoDTO dto) {
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}