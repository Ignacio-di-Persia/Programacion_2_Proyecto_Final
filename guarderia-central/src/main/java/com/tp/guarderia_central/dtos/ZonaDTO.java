package com.tp.guarderia_central.dtos;

import com.tp.guarderia_central.models.enums.TipoVehiculo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ZonaDTO {

    private Long id;

    @NotBlank(message = "La letra no puede estar vacía")
    @Pattern(regexp = "^[A-Z]{1}$", message = "La zona debe ser una única letra mayúscula (A-Z)")
    private String letra;

    @NotNull(message = "El tipo de vehículos de la zona no puede ser nulo")
    private TipoVehiculo tipoVehiculosZona;

    @NotNull(message = "Las dimensiones de la zona no pueden ser nulas")
    @Valid
    private DimensionDTO dimensionZona;

}
