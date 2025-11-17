package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "socio_garage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocioGarage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "socio_codigo", referencedColumnName = "codigo")
    private Socio socio;

    @ManyToOne
    @JoinColumn(name = "garage_codigo", referencedColumnName = "codigo")
    private Garage garage;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;
}
