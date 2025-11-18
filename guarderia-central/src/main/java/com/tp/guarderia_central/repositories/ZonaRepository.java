package com.tp.guarderia_central.repositories;

import com.tp.guarderia_central.models.entities.Zona;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZonaRepository extends JpaRepository<Zona, Long> {

    Optional<Zona> findByLetra(String upperCase);
    
}