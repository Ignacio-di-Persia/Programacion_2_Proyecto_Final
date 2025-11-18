package com.tp.guarderia_central.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.tp.guarderia_central.models.enums.EspecialidadEmpleado;
import com.tp.guarderia_central.models.embeddable.Direccion;
import com.tp.guarderia_central.models.security.Usuario;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "empleados")
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Habilitamos la auditoría
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código no puede estar vacío")
    @Size(min = 3, max = 10, message = "El código debe tener entre 3 y 10 caracteres")
    @Column(unique = true, nullable = false, length = 10)
    private String codigo;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max=40, message = "El nombre debe tener entre 2 y 40 caracteres.")
    @Column(nullable = false, length = 40)
    private String nombre;

    @Embedded
    private Direccion direccion;

    @Size(min = 8, max = 20, message = "El teléfono debe tener entre 8 y 20 caracteres")
    @Column(length = 20, unique = true)
    private String telefono;

    @NotNull(message = "La especialidad no puede estar vacía")
    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private EspecialidadEmpleado especialidad;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "empleado_garages_asignados", joinColumns = @JoinColumn(name = "empleado_id"),inverseJoinColumns = @JoinColumn(name = "garage_id"))
    private List<Garage> garagesAsignados;

    @CreatedDate
    @Column(name = "fecha_registro", updatable = false, nullable = false)
    private LocalDate fechaRegistro;

    @CreatedBy
    @Column(name = "registrado_por", updatable = false, nullable = false)
    private String registradoPor;

    @LastModifiedBy
    @Column(name = "ultima_modificacion_por")
    private String ultimaModificacionPor;

    @LastModifiedDate
    @Column(name = "fecha_ultima_modificacion")
    private LocalDate fechaUltimaModificacion;
}