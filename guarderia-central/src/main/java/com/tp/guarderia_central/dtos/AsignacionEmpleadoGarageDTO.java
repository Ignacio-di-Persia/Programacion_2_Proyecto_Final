package com.tp.guarderia_central.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AsignacionEmpleadoGarageDTO {

    @NotNull(message = "El ID del empleado es obligatorio")
    private Long empleadoId;

    private List<Long> garageIds;
}
