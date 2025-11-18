package com.tp.guarderia_central.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CompraGarageDTO {

    @NotNull(message = "El ID del garage es obligatorio")
    private Long garageId;

    @NotNull(message = "Debe seleccionar un socio comprador")
    private Long socioId;

    @NotNull(message = "La fecha de compra es obligatoria")
    @PastOrPresent(message = "La fecha de compra no puede ser futura")
    private LocalDate fechaCompra;
}