package duoc.hotelpro.pagos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservaId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    private String metodoPago;

    private String estado;

    private LocalDate fechaPago;
}