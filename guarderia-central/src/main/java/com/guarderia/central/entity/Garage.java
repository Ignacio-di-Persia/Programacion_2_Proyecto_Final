package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;
import java.util.arraylist;
import java.util.list;
import com.fasterxml.jackson.annotation.jsonignore;

@entity
@Table(name = "garage")
@data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Garage {

    @Id
    @Column(name = "codigo", nullable = false, length = 10)
    @NotBlank(message = "El código del garage es obligatorio")
    private String codigo; // Ej: "GARAGE001"

    @Column(name = "ocupado", nullable = false)
    private boolean Ocupado;

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<SocioGarage> sociosAsignados = new ArrayList<>();
}
