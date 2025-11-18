package com.tp.guarderia_central.dtos;

import com.tp.guarderia_central.models.enums.TipoVehiculo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VehiculoDTO {

    private Long id;

    @NotBlank(message = "La matrícula no puede estar vacía")
    @Size(min = 6, max = 10, message = "La matrícula debe tener entre 6 y 10 caracteres")
    private String matricula;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String nombre;

    @NotNull(message = "El tipo de vehículo no puede ser nulo")
    private TipoVehiculo tipo;

    @NotNull(message = "Las dimensiones no pueden ser nulas")
    @Valid
    private DimensionDTO dimensiones;

    @NotNull(message = "Debe seleccionar un socio propietario")
    private Long socioId;
    
    private String socioNombre;

    private Long garageId;
    private String garageNumero;
    private String garageZona;

    public void setMatricula(String matricula) {
        if (matricula != null) {
            this.matricula = matricula.replaceAll("\\s+", "").toUpperCase();
        } else {
            this.matricula = null;
        }
    }
}