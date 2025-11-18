package com.tp.guarderia_central.dtos;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class SocioDTO {

    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max=40, message = "El nombre debe tener entre 2 y 40 caracteres.")
    private String nombre;

    @NotNull(message = "La dirección no puede ser nula")
    @Valid
    private DireccionDTO direccion;

    @NotBlank(message = "El DNI no puede estar vacío")
    @Size(min = 8, max = 8, message = "El dni debe tener 8 digitos exactamente")
    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    private String telefono;

    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    @PastOrPresent(message = "La fecha de ingreso no puede ser posterior al día de hoy")
    private LocalDate fechaIngreso;

}