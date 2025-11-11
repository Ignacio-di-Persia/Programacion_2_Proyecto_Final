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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "empleado_codigo", referencedColumnName = "codigo")
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "zona_codigo", referencedColumnName = "codigo")
    private Zona zona;

    @Column(name = "vehiculos_asignados")
    private Integer vehiculosAsignados;
}