package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.entity.Socio;
import com.guarderia.central.entity.SocioGarage;
import com.guarderia.central.exception.SocioException;
import com.guarderia.central.repository.GarageRepository;
import com.guarderia.central.repository.SocioGarageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SocioGarageServiceImpl implements SocioGarageService {

    private final SocioGarageRepository socioGarageRepository;
    private final GarageRepository garageRepository;

    @Override
    public void asignarGarage(Socio socio, Garage garage) {
        if (garage.isOcupado()) {
            throw new SocioException("El garage " + garage.getCodigo() + " ya está ocupado");
        }

        SocioGarage asignacion = SocioGarage.builder()
                .socio(socio)
                .garage(garage)
                .fechaAsignacion(LocalDate.now())
                .build();

        socioGarageRepository.save(asignacion);

        garage.setOcupado(true);
        garageRepository.save(garage);

        log.info("Garage {} asignado a socio {}", garage.getCodigo(), socio.getDni());
    }

    @Override
    public void cambiarGarage(Socio socio, Garage garageNuevo) {
        List<SocioGarage> asignaciones = socioGarageRepository.findAllBySocio(socio);

        if (asignaciones.isEmpty()) {
            asignarGarage(socio,garageNuevo);
            return;
        }
        
            SocioGarage asignacionActual = asignaciones.get(0);
            Garage garageActual = asignacionActual.getGarage();

            if (garageActual.getCodigo().equals(garageNuevo.getCodigo())) {
                log.info("El socio {} ya tiene asignado el garage {}", socio.getDni(), garageNuevo.getCodigo());
                return; // No hacer nada si es el mismo garage
            }

            // Liberar garage actual
            garageActual.setOcupado(false);
            garageRepository.save(garageActual);

            // Verificar disponibilidad de nuevo garage
            if (garageNuevo.isOcupado()) {
                throw new SocioException("El garage " + garageNuevo.getCodigo() + " ya está ocupado");
            }

            // Asignar nuevo garage
            asignacionActual.setGarage(garageNuevo);
            asignacionActual.setFechaAsignacion(LocalDate.now());
            socioGarageRepository.save(asignacionActual);

            garageNuevo.setOcupado(true);
            garageRepository.save(garageNuevo);

            log.info("Garage del socio {} cambiado de {} a {}",socio.getDni(), garageActual.getCodigo(), garageNuevo.getCodigo());

        
    }

    @Override
    public void liberarGarage(Socio socio) {
        List<SocioGarage> asignaciones = socioGarageRepository.findAllBySocio(socio);
       
        for (SocioGarage sg : asignaciones) {
            Garage g = sg.getGarage();
            if (g != null) {
                g.setOcupado(false);
                garageRepository.save(g);
                log.info("Garage {} liberado para socio {}", g.getCodigo(), socio.getDni());
            }
            socioGarageRepository.delete(sg);
        }
    }
}


