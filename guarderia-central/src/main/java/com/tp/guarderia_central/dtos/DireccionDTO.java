package com.tp.guarderia_central.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DireccionDTO {

    @NotBlank(message = "El barrio no puede estar vacío")
    @Size(min= 3, max= 30, message = "El barrio debe tener entre 3 y 30 caracteres")
    private String barrio;

    @NotBlank(message = "La calle no puede estar vacía")
    @Size(min= 3, max= 30, message = "La calle debe tener entre 3 y 30 caracteres")
    private String calle;

    @Min(value = 1, message = "La altura minima no puede ser menor a 1")
    @Max(value = 40000, message = "La altura máxima no puede ser mayor a 40.000")
    private int altura;
}
