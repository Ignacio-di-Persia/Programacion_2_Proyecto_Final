package com.guarderia.central.service;

import com.guarderia.central.entity.Socio;
import com.guarderia.central.exception.SocioException;
import com.guarderia.central.repository.SocioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class SocioServiceImpl implements SocioService {

    @Autowired
    private SocioRepository socioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Socio> obtenerTodosLosSocios() {
        log.info("Obteniendo todos los socios");
        return socioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Socio obtenerSocioPorDni(Integer dni) {
        log.info("Buscando socio con DNI: {}", dni);
        return socioRepository.findByDni(dni)
                .orElseThrow(() -> new SocioException("Socio con DNI " + dni + " no encontrado"));
    }

    @Override
    public Socio crearSocio(Socio socio) {
        log.info("Creando nuevo socio: {}", socio);
        
        // Verificar si el DNI ya existe
        if (socioRepository.existsByDni(socio.getDni())) {
            throw new SocioException("Ya existe un socio con el DNI " + socio.getDni());
        }
        
        // Verificar si el correo ya existe
        if (socioRepository.findByCorreo(socio.getCorreo()).isPresent()) {
            throw new SocioException("Ya existe un socio con el correo " + socio.getCorreo());
        }
        
        return socioRepository.save(socio);
    }

    @Override
    public Socio actualizarSocio(Integer dni, Socio socio) {
        log.info("Actualizando socio con DNI: {}", dni);
        
        Socio socioExistente = obtenerSocioPorDni(dni);
        
        // Verificar si el correo ya existe en otro socio
        socioRepository.findByCorreo(socio.getCorreo()).ifPresent(s -> {
            if (!s.getDni().equals(dni)) {
                throw new SocioException("El correo " + socio.getCorreo() + " ya está registrado en otro socio");
            }
        });
        
        // Actualizar campos
        socioExistente.setNombres(socio.getNombres());
        socioExistente.setApellidos(socio.getApellidos());
        socioExistente.setFechaNacimiento(socio.getFechaNacimiento());
        socioExistente.setCorreo(socio.getCorreo());
        socioExistente.setVehiculo(socio.getVehiculo());
        socioExistente.setGarage(socio.getGarage());
        
        return socioRepository.save(socioExistente);
    }

    @Override
    public void eliminarSocio(Integer dni) {
        log.info("Eliminando socio con DNI: {}", dni);
        
        Socio socio = obtenerSocioPorDni(dni);
        socioRepository.delete(socio);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDni(Integer dni) {
        return socioRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorApellido(String apellido) {
        log.info("Buscando socios por apellido: {}", apellido);
        return socioRepository.findByApellidosStartingWithIgnoreCase(apellido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorDniParcial(String dniPrefix) {
        log.info("Buscando socios por DNI parcial: {}", dniPrefix);
        return socioRepository.findByDniStartingWith(dniPrefix);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorCriterio(String criterio) {
        log.info("Buscando socios por criterio: {}", criterio);
        
        if (criterio == null || criterio.trim().isEmpty()) {
            return obtenerTodosLosSocios();
        }
        
        return socioRepository.buscarPorApellidoODni(criterio.trim());
    }
}