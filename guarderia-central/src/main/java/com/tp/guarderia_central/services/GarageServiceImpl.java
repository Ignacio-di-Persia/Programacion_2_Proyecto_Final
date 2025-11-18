package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.CompraGarageDTO;
import com.tp.guarderia_central.dtos.GarageDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.mappers.GarageMapper;
import com.tp.guarderia_central.models.entities.Garage;
import com.tp.guarderia_central.models.entities.Socio;
import com.tp.guarderia_central.models.entities.Vehiculo;
import com.tp.guarderia_central.models.entities.Zona;
import com.tp.guarderia_central.repositories.GarageRepository;
import com.tp.guarderia_central.repositories.SocioRepository;
import com.tp.guarderia_central.repositories.VehiculoRepository;
import com.tp.guarderia_central.repositories.ZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GarageServiceImpl implements IGarageService {

    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;
    private final ZonaRepository zonaRepository; 
    private final VehiculoRepository vehiculoRepository;
    private final SocioRepository socioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<GarageDTO> findAll() {
        return garageRepository.findAll().stream()
                .map(garageMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public GarageDTO findById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado con id: " + id));
        return garageMapper.toDTO(garage);
    }

    @Override
    @Transactional
    public GarageDTO save(GarageDTO garageDTO) {
        if (garageRepository.findByNumeroGarage(garageDTO.getNumeroGarage()).isPresent()) {
            throw new RuntimeException("Error: El número de garage '" + garageDTO.getNumeroGarage() + "' ya existe.");
        }

        Zona zona = zonaRepository.findById(garageDTO.getZonaId())
                .orElseThrow(() -> new ResourceNotFoundException("Zona no encontrada con id: " + garageDTO.getZonaId()));

        Garage garage = garageMapper.toEntity(garageDTO);
        garage.setId(null);

        garage.setZona(zona);
        garage.setPropietario(null);
        garage.setVehiculoOcupante(null);

        Garage garageGuardado = garageRepository.save(garage);
        return garageMapper.toDTO(garageGuardado);
    }

    @Override
    @Transactional
    public GarageDTO update(Long id, GarageDTO garageDTO) {
        Garage garageAActualizar = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado con id: " + id));
        Optional<Garage> conMismoNumero = garageRepository.findByNumeroGarage(garageDTO.getNumeroGarage());
        if (conMismoNumero.isPresent() && !conMismoNumero.get().getId().equals(id)) {
            throw new RuntimeException("Error: El número de garage '" + garageDTO.getNumeroGarage() + "' ya está en uso.");
        }
        garageMapper.updateGarageFromDto(garageDTO, garageAActualizar);
        Garage garageGuardado = garageRepository.save(garageAActualizar);
        return garageMapper.toDTO(garageGuardado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Garage garage = garageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado con id: " + id));

        if (garage.getVehiculoOcupante() != null) {
            throw new RuntimeException("Error: No se puede eliminar el garage. Está ocupado por el vehículo: " + 
                                     garage.getVehiculoOcupante().getMatricula());
        }
        if (garage.getPropietario() != null) {
            throw new RuntimeException("Error: No se puede eliminar el garage. Pertenece al socio: " + 
                                     garage.getPropietario().getNombre());
        }
        if (garage.getEmpleadosAsignados() != null && !garage.getEmpleadosAsignados().isEmpty()) {
            throw new RuntimeException("Error: No se puede eliminar el garage. Tiene " + 
                                     garage.getEmpleadosAsignados().size() + " empleados asignados.");
        }

        garageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void asignarVehiculo(Long garageId, Long vehiculoId) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado"));
        
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));

        if (garage.getVehiculoOcupante() != null) {
            throw new RuntimeException("El garage ya está ocupado por el vehículo: " + 
                                     garage.getVehiculoOcupante().getMatricula());
        }
        
        if (vehiculo.getGarage() != null) {
             throw new RuntimeException("El vehículo ya está asignado al garage: " + 
                                      vehiculo.getGarage().getNumeroGarage());
        }

        if (garage.getZona().getTipoVehiculosZona() != vehiculo.getTipo()) {
            throw new RuntimeException("Incompatible: La zona es para " + 
                                     garage.getZona().getTipoVehiculosZona() + 
                                     " pero el vehículo es " + vehiculo.getTipo());
        }

        boolean entraAncho = vehiculo.getDimensiones().getAncho()
                .compareTo(garage.getZona().getDimensionZona().getAncho()) <= 0;
                
        boolean entraAlto = vehiculo.getDimensiones().getAlto()
                .compareTo(garage.getZona().getDimensionZona().getAlto()) <= 0;

        if (!entraAncho || !entraAlto) {
            throw new RuntimeException("El vehículo es demasiado grande para este garage/zona.");
        }

        garage.setVehiculoOcupante(vehiculo);
        garage.setFechaAsignacionVehiculo(LocalDateTime.now());
        garageRepository.save(garage);
    }

    @Override
    @Transactional
    public void liberarGarage(Long garageId) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado"));
        
        if (garage.getVehiculoOcupante() == null) {
            throw new RuntimeException("El garage ya está libre.");
        }

        garage.setVehiculoOcupante(null);
        garage.setFechaAsignacionVehiculo(null);
        
        garageRepository.save(garage);
    }

    @Override
    @Transactional
    public void cambiarGarage(Long vehiculoId, Long nuevoGarageId) {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado"));
        if (vehiculo.getGarage() != null) {
            Long garageAnteriorId = vehiculo.getGarage().getId();
            liberarGarage(garageAnteriorId);
        }
        asignarVehiculo(nuevoGarageId, vehiculoId);
    }

    @Override
    @Transactional
    public void asignarPropietario(CompraGarageDTO compraDTO) {
        Long garageId = compraDTO.getGarageId();
        Long socioId = compraDTO.getSocioId();
        LocalDate fechaCompra = compraDTO.getFechaCompra();

        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado"));
        
        Socio socio = socioRepository.findById(socioId)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado"));

        if (garage.getPropietario() != null) {
             throw new RuntimeException("El garage ya pertenece a: " + garage.getPropietario().getNombre());
        }

        garage.setPropietario(socio);
        garage.setFechaCompra(fechaCompra);
        garageRepository.save(garage);
    }

    @Override
    @Transactional
    public void liberarPropietario(Long garageId) {
        Garage garage = garageRepository.findById(garageId)
                .orElseThrow(() -> new ResourceNotFoundException("Garage no encontrado"));

        if (garage.getPropietario() == null) {
            throw new RuntimeException("El garage no tiene propietario asignado.");
        }

        garage.setPropietario(null);
        garage.setFechaCompra(null);
        
        garageRepository.save(garage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GarageDTO> findByPropietarioId(Long socioId) {
        return garageRepository.findByPropietarioId(socioId).stream()
                .map(garageMapper::toDTO)
                .collect(Collectors.toList());
    }
}