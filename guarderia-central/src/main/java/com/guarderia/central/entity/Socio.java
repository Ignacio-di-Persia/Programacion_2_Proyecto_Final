package com.guarderia.central.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "socios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo")
    private Long codigo;


    @Column(name = "dni", nullable = false)
    @NotNull(message = "El DNI es obligatorio")
    private Integer dni;

    @Column(name = "nombres", nullable = false, length = 100)
    @NotBlank
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    @NotBlank
    private String apellidos;

    @Column(name = "direccion", nullable = false, length = 200)
    @NotBlank
    private String direccion;

    @Column(name = "telefono", nullable = false, length = 15)
    @NotBlank
    private String telefono;

    @Column(name = "fecha_nacimiento", nullable = false)
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @Column(name = "correo", nullable = false, unique = true, length = 150)
    @NotBlank
    @Email
    private String correo;

    @Column(name = "vehiculo", length = 100)
    private String vehiculo;

    // Relación con SocioGarage
    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocioGarage> garagesPropios = new ArrayList<>();



    @PrePersist
    public void prePersist() {
        if (this.vehiculo == null || this.vehiculo.isEmpty()) {
            this.vehiculo = "Sin vehículo";
        }
    }
}