package com.guarderia.central.repository;

import com.guarderia.central.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    // Buscar por DNI
    Optional<Empleado> findByDni(Integer dni);

    // Verificar si existe un DNI
    boolean existsByDni(Integer dni);

    // Buscar por teléfono
    Optional<Empleado> findByTelefono(String telefono);

    // Buscar por apellidos (búsqueda parcial, case insensitive)
    @Query("SELECT e FROM Empleado e WHERE LOWER(e.apellidos) LIKE LOWER(CONCAT(:apellido, '%'))")
    List<Empleado> findByApellidosStartingWithIgnoreCase(@Param("apellido") String apellido);

    // Buscar por nombre o apellido (búsqueda combinada)
    @Query("SELECT e FROM Empleado e WHERE " +
           "LOWER(e.nombres) LIKE LOWER(CONCAT(:criterio, '%')) OR " +
           "LOWER(e.apellidos) LIKE LOWER(CONCAT(:criterio, '%'))")
    List<Empleado> buscarPorNombreOApellido(@Param("criterio") String criterio);
}
