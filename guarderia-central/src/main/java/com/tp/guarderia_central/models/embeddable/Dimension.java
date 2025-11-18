package com.tp.guarderia_central.models.embeddable;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dimension {
    @NotNull(message = "El ancho no puede ser nulo")
    @Positive(message = "El ancho debe ser un valor positivo")
    @Column(name = "dimension_ancho", nullable = false, precision = 5, scale = 2)
    private BigDecimal ancho;

    @NotNull(message = "El alto no puede ser nulo")
    @Positive(message = "El alto debe ser un valor positivo")
    @Column(name = "dimension_alto", nullable = false,  precision = 5, scale = 2)
    private BigDecimal alto;
}
