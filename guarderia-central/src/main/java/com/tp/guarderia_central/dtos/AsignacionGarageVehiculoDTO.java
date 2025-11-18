package com.tp.guarderia_central.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AsignacionGarageVehiculoDTO {

    @NotNull(message = "El ID del garage es obligatorio")
    private Long garageId;

    @NotNull(message = "El ID del vehículo es obligatorio")
    private Long vehiculoId;
}