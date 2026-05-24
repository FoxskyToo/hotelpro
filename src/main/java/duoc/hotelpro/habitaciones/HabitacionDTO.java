package duoc.hotelpro.habitaciones;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class HabitacionDTO {

    @NotBlank(message = "El número de habitación es obligatorio")
    private String numero;

    @NotBlank(message = "El tipo de habitación es obligatorio")
    private String tipo;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "1.0", message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "Debe indicar si la habitación está disponible")
    private Boolean disponible;
}