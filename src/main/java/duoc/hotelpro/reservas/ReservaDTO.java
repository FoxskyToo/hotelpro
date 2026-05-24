package duoc.hotelpro.reservas;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaDTO {

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    @NotNull(message = "La habitación es obligatoria")
    private Long habitacionId;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @FutureOrPresent(message = "La fecha de ingreso no puede ser anterior a hoy")
    private LocalDate fechaIngreso;

    @NotNull(message = "La fecha de salida es obligatoria")
    private LocalDate fechaSalida;

    private String estado;
}