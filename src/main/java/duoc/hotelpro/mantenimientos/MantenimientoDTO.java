package duoc.hotelpro.mantenimientos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MantenimientoDTO {

    @NotNull(message = "La habitación es obligatoria")
    private Long habitacionId;

    @NotNull(message = "El empleado es obligatorio")
    private Long empleadoId;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    private String estado;

    @NotNull(message = "La fecha de mantenimiento es obligatoria")
    private LocalDate fechaMantenimiento;
}