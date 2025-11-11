package com.guarderia.central.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "zonas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Zona {

    @Id
    @Column(name = "codigo", nullable = false, length = 1)
    @NotBlank(message = "El código de la zona es obligatorio")
    private String codigo; // Ej: "A" , "B"

    @Column(name = "tipo_vehiculos", nullable = false, length = 100)
    private String tipoVehiculos;

    @Column(name = "cantidad_vehiculos")
    private Integer cantidadVehiculos;

    @Column(name = "profundidad")
    private Double profundidad;

    @Column(name = "ancho")
    private Double ancho;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<EmpleadoZona> empleadosAsignados = new ArrayList<>();

    @OneToMany(mappedBy = "zona")
    private List<Garage> garages = new ArrayList<>();
}
