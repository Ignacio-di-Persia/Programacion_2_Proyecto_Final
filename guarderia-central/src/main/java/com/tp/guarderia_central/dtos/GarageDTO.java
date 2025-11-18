package com.tp.guarderia_central.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class GarageDTO {

    private Long id;

    @NotNull(message = "El número de garage no puede ser nulo")
    @Positive(message = "El número de garage debe ser positivo")
    private Integer numeroGarage;
    
    private BigDecimal lecturaContadorLuz;
    private boolean mantenimientoContratado;

    @NotNull(message = "Debe seleccionar una zona")
    private Long zonaId;
    
    private String zonaLetra;

    private Long propietarioId;
    
    private String propietarioNombre;

    @PastOrPresent(message = "La fecha de compra no puede ser futura")
    private LocalDate fechaCompra;

    private Long vehiculoOcupanteId;
    
    private String vehiculoOcupanteMatricula;

    @PastOrPresent(message = "La asignación del vehiculo no puede ser posterior a la fecha actual")
    private LocalDateTime fechaAsignacionVehiculo;
}
