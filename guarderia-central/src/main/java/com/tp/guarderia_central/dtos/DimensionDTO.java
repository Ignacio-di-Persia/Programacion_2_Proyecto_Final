package com.tp.guarderia_central.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DimensionDTO {

    @NotNull(message = "El ancho no puede ser nulo")
    @Positive(message = "El ancho debe ser un valor positivo")
    private BigDecimal ancho;

    @NotNull(message = "El alto no puede ser nulo")
    @Positive(message = "El alto debe ser un valor positivo")
    private BigDecimal alto;
}