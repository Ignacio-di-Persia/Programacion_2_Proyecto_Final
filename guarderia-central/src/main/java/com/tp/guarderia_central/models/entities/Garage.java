package com.tp.guarderia_central.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "garages")
@Getter
@Setter
@NoArgsConstructor
public class Garage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El número de garage no puede estar vacío")
    @Positive(message = "El número de garage debe ser positivo")
    @Column(name = "numero_garage", unique = true, nullable = false, length = 10)
    private Integer numeroGarage;

    @Column(name = "lectura_contador_luz", precision = 9, scale = 2)
    @PositiveOrZero(message = "La lectura del contador no puede ser negativa")
    private BigDecimal lecturaContadorLuz;

    @Column(name = "mantenimiento_contratado")
    private boolean mantenimientoContratado;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_ocupante_id", unique = true)
    private Vehiculo vehiculoOcupante;

    @PastOrPresent(message = "La asignación del vehiculo no puede ser posterior a la fecha actual")
    @Column(name = "fecha_asignacion_vehiculo")
    private LocalDateTime fechaAsignacionVehiculo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propietario_socio_id")
    private Socio propietario;
    
    @PastOrPresent(message = "La fecha de compra no puede ser posterior a hoy")
    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;
    
    @NotNull(message = "El garage debe pertenecer a una zona")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;
    
    @ManyToMany(mappedBy = "garagesAsignados", fetch = FetchType.LAZY)
    private List<Empleado> empleadosAsignados;
}
