package duoc.hotelpro.mantenimientos;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mantenimientos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long habitacionId;

    private Long empleadoId;

    private String descripcion;

    private String estado;

    private LocalDate fechaMantenimiento;
}