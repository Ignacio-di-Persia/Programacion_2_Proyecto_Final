package com.tp.guarderia_central.repositories;

import com.tp.guarderia_central.models.entities.Socio;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {
    Optional<Socio> findByDni(String dni);
    Optional<Socio> findByUsuarioUsername(String username);
}