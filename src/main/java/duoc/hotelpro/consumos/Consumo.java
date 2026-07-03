package duoc.hotelpro.consumos;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "consumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reservaId;

    private Long servicioId;

    private Integer cantidad;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;
}