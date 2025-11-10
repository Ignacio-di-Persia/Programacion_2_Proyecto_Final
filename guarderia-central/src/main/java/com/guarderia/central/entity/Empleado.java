package com.guarderia.central.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;


    @Column(name = "dni", nullable = false)
    @NotNull(message = "El DNI es obligatorio")
    @Min(value = 10000000, message = "El DNI debe tener al menos 8 dígitos")
    @Max(value = 999999999, message = "El DNI no puede tener más de 9 dígitos")
    private Integer dni;

    @Column(name = "nombres", nullable = false, length = 100)
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 100, message = "El apellido debe tener entre 2 y 100 caracteres")
    private String apellidos;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private List<EmpleadoZona> zonasAsignadas = new ArrayList<>();
}
