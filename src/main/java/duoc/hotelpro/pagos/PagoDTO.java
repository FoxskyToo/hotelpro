package duoc.hotelpro.pagos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PagoDTO {

    @NotNull(message = "La reserva es obligatoria")
    private Long reservaId;

    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a cero")
    private BigDecimal monto;

    @NotBlank(message = "El método de pago es obligatorio")
    private String metodoPago;

    private String estado;

    private LocalDate fechaPago;
}