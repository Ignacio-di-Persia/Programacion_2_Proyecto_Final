package com.tp.guarderia_central.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

import com.tp.guarderia_central.models.embeddable.Dimension;
import com.tp.guarderia_central.models.enums.TipoVehiculo;

@Entity
@Table(name = "zonas")
@Getter
@Setter
@NoArgsConstructor
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La letra de la zona no puede ser nula")
    @Size(min = 1, max = 1, message = "Las zonas estan definidas por una única letra")
    @Pattern(regexp = "^[A-Z]{1}$", message = "La zona debe ser una única letra mayúscula (A-Z)")
    @Column(unique = true, nullable = false, length = 1)
    private String letra;

    @NotNull(message = "El tipo de vehículos de la zona no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_vehiculos_zona", nullable = false, length = 20)
    private TipoVehiculo tipoVehiculosZona;

    @NotNull(message = "Las dimensiones de la zona no pueden ser nulas")
    @Embedded
    private Dimension dimensionZona;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Garage> garages;
}