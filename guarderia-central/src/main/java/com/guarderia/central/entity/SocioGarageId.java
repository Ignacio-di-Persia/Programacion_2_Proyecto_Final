package com.guarderia.central.entity;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@embeddable
@data
@NoArgsConstructor
@AllArgsConstructor
public class SocioGarageId implements Serializable {

    @Column(name = "socio_codigo") // <-- este nombre debe matchear con la FK real
    private Long socioCodigo;

    @Column(name = "garage_codigo")
    private String garageCodigo;

}
