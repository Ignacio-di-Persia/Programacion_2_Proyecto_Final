package com.tp.guarderia_central.models.entities;

import com.tp.guarderia_central.models.embeddable.Dimension;
import com.tp.guarderia_central.models.enums.TipoVehiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehiculos")
@Getter
@Setter
@NoArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La matrícula no puede estar vacía")
    @Size(min = 6, max = 10, message = "La matrícula debe tener entre 6 y 10 caracteres")
    @Column(unique = true, nullable = false, length = 10)
    private String matricula;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    @Column(nullable = false, length = 30)
    private String nombre;

    @NotNull(message = "El tipo de vehículo no puede ser nulo")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20, updatable = false)
    private TipoVehiculo tipo;

    @Embedded
    @NotNull(message = "Las dimensiones no pueden ser nulas")
    private Dimension dimensiones;

    @NotNull(message = "El vehículo debe tener un socio propietario")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @OneToOne(mappedBy = "vehiculoOcupante")
    private Garage garage;
}