package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoZonaId implements Serializable {

    @Column(name = "empleado_codigo")
    private Long empleadoCodigo;

    @Column(name = "zona_codigo")
    private String zonaCodigo;
}
