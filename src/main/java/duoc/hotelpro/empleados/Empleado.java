package duoc.hotelpro.empleados;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String cargo;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;

    private Boolean activo;
}