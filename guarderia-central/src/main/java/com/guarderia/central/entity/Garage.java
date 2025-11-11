package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "garage")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Garage {

    @Id
    @Column(name = "codigo", nullable = false, length = 10)
    @NotBlank
    private String codigo; // Ej: "GARAGE001"

    @Column(name = "ocupado", nullable = false)
    private boolean ocupado;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<SocioGarage> sociosAsignados = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "zona_codigo")
    private Zona zona;
}