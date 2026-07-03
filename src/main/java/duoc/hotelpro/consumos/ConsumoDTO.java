package duoc.hotelpro.consumos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ConsumoDTO {

    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;

    @NotNull(message = "El servicio es obligatorio")
    private Long servicioId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero")
    private Integer cantidad;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El total debe ser mayor a cero")
    private BigDecimal total;
}