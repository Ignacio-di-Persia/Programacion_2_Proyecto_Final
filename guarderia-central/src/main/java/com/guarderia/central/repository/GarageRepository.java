package com.guarderia.central.repository;

import com.guarderia.central.entity.Garage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage, String> {

    // Verificar si existe un garage por código
    boolean existsByCodigo(String codigo);

    List<Garage> findByDisponibleTrue();

}
