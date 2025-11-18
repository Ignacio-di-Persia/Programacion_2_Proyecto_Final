package com.tp.guarderia_central.dtos;

import java.util.List;

import com.tp.guarderia_central.models.enums.EspecialidadEmpleado;
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
public class EmpleadoDTO {

    private Long id;

    @NotBlank(message = "El código no puede estar vacío")
    @Size(min = 3, max = 10, message = "El código debe tener entre 3 y 10 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 40, message = "El nombre debe tener entre 2 y 40 caracteres.")
    private String nombre;

    @NotNull(message = "La dirección no puede ser nula")
    @Valid 
    private DireccionDTO direccion;

    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    private String telefono;

    @NotNull(message = "La especialidad no puede ser nula")
    private EspecialidadEmpleado especialidad;

    private List<GarageDTO> garagesAsignados;
}