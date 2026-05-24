package duoc.hotelpro.reservas;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;

    private Long habitacionId;

    private LocalDate fechaIngreso;

    private LocalDate fechaSalida;

    private String estado;
}