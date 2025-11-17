package com.guarderia.central.repository;

import com.guarderia.central.entity.Socio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {

    // Buscar por ID
    Optional<Socio> findById(Long id);

    // Buscar por DNI
    Optional<Socio> findByDni(Integer dni);

    // Buscar por correo
    Optional<Socio> findByCorreo(String correo);

    // Verificar si existe un DNI
    boolean existsByDni(Integer dni);

    // Buscar socios por apellido (búsqueda parcial, case insensitive)
    @Query("SELECT s FROM Socio s WHERE LOWER(s.apellidos) LIKE LOWER(CONCAT(:apellido, '%'))")
    List<Socio> findByApellidosStartingWithIgnoreCase(@Param("apellido") String apellido);

    // Buscar socios por DNI que comience con ciertos dígitos
    @Query("SELECT s FROM Socio s WHERE CAST(s.dni AS string) LIKE CONCAT(:dniPrefix, '%')")
    List<Socio> findByDniStartingWith(@Param("dniPrefix") String dniPrefix);

    // Buscar por apellido o DNI (búsqueda combinada)
    @Query("SELECT s FROM Socio s WHERE " +
           "LOWER(s.apellidos) LIKE LOWER(CONCAT(:criterio, '%')) OR " +
           "CAST(s.dni AS string) LIKE CONCAT(:criterio, '%')")
    List<Socio> buscarPorApellidoODni(@Param("criterio") String criterio);
}