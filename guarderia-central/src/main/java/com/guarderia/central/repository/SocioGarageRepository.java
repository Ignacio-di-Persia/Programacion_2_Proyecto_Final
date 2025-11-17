package com.guarderia.central.repository;

import com.guarderia.central.entity.SocioGarage;
import com.guarderia.central.entity.SocioGarageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.guarderia.central.entity.Socio;
import java.util.List;

@Repository
public interface SocioGarageRepository extends JpaRepository<SocioGarage, SocioGarageId>{

    // Garages asignados a un socio
    List<SocioGarage> findBySocioCodigo(Long socioCodigo);

    // Socios asignados a un garage
    List<SocioGarage> findByGarageCodigo(String garageCodigo);

    // Se buscans todos los garages de un socio
    List<SocioGarage> findAllBySocio(Socio socio);

    List<SocioGarage> findBySocio(Socio socio);

    // Buscar asignación específica
    SocioGarage findBySocioCodigoAndGarageCodigo(Long socioCodigo, String garageCodigo);
}
