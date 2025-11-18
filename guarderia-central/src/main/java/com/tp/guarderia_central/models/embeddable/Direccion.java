package com.tp.guarderia_central.models.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Direccion {

    @Column(name = "barrio", nullable = false, length = 30)
    @NotBlank(message = "El barrio no puede estar vacío")
    @Size(min= 3, max= 30, message = "El barrio debe tener entre 3 y 30 caracteres")
    private String barrio;

    @Column(name = "calle", nullable = false, length = 30)
    @NotBlank(message = "La calle no puede estar vacía")
    @Size(min= 3, max= 30, message = "La calle debe tener entre 3 y 30 caracteres")
    private String calle;

    @Column(name = "altura", nullable = false)
    @Min(value = 1, message = "La altura minima no puede ser menor a 1")
    @Max(value = 40000, message = "La altura máxima no puede ser mayor a 40.000")
    private Integer altura;
}
