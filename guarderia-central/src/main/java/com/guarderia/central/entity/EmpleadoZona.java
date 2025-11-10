package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empleado_zona")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoZona {

    @EmbeddedId
    private EmpleadoZonaId id;

    @ManyToOne
    @MapsId("empleadoCodigo")
    @JoinColumn(name = "empleado_codigo", referencedColumnName = "codigo")
    private Empleado empleado;

    @ManyToOne
    @MapsId("zonaCodigo")
    @JoinColumn(name = "zona_codigo", referencedColumnName = "codigo")
    private Zona zona;

    @Column(name = "vehiculos_asignados")
    private Integer vehiculosAsignados;
}