package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.entity.Socio;
import com.guarderia.central.exception.SocioException;
import com.guarderia.central.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SocioServiceImpl implements SocioService {

    private final SocioRepository socioRepository;
    private final SocioGarageService socioGarageService;
    private final GarageService garageService; // si tenés servicios de Garage

    @Override
    @Transactional(readOnly = true)
    public List<Socio> obtenerTodosLosSocios() {
        log.info("Obteniendo todos los socios");
        return socioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Socio obtenerSocioPorDni(Integer dni) {
        return socioRepository.findByDni(dni)
                .orElseThrow(() -> new SocioException("Socio con DNI " + dni + " no encontrado"));
    }

    @Override
    public Socio crearSocio(Socio socio, String garageCodigo) {
        log.info("Creando socio: {}", socio);

        if (socioRepository.existsByDni(socio.getDni())) {
            throw new SocioException("Ya existe un socio con DNI " + socio.getDni());
        }
        if (socioRepository.findByCorreo(socio.getCorreo()).isPresent()) {
            throw new SocioException("Ya existe un socio con correo " + socio.getCorreo());
        }

        Socio nuevoSocio = socioRepository.save(socio);

        Garage garage = garageService.obtenerGaragePorCodigo(garageCodigo);
        socioGarageService.asignarGarage(nuevoSocio, garage);

        return nuevoSocio;
    }

    @Override
    public Socio actualizarSocio(Integer dni, Socio socioNuevo, String garageCodigo) {
        Socio socioExistente = socioRepository.findById(dni)
                .orElseThrow(() -> new SocioException("No existe un socio con DNI " + dni));

        if (!socioExistente.getCorreo().equals(socioNuevo.getCorreo())
                && socioRepository.findByCorreo(socioNuevo.getCorreo()).isPresent()) {
            throw new SocioException("Ya existe un socio con correo " + socioNuevo.getCorreo());
        }

        // Actualizar datos del socio
        socioExistente.setNombres(socioNuevo.getNombres());
        socioExistente.setApellidos(socioNuevo.getApellidos());
        socioExistente.setCorreo(socioNuevo.getCorreo());
        socioExistente.setVehiculo(socioNuevo.getVehiculo());

        Socio actualizado = socioRepository.save(socioExistente);

        // Actualizar garage
        Garage garageNuevo = garageService.obtenerGaragePorCodigo(garageCodigo);
        socioGarageService.cambiarGarage(actualizado, garageNuevo);

        return actualizado;
    }

    @Override
    public void eliminarSocio(Integer dni) {
        Socio socio = socioRepository.findById(dni)
                .orElseThrow(() -> new SocioException("No existe un socio con DNI " + dni));

        socioGarageService.liberarGarage(socio);
        socioRepository.delete(socio);

        log.info("Socio eliminado correctamente: {}", dni);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDni(Integer dni) {
        return socioRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorApellido(String apellido) {
        return socioRepository.findByApellidosStartingWithIgnoreCase(apellido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorDniParcial(String dniPrefix) {
        return socioRepository.findByDniStartingWith(dniPrefix);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return obtenerTodosLosSocios();
        }
        return socioRepository.buscarPorApellidoODni(criterio.trim());
    }
}
