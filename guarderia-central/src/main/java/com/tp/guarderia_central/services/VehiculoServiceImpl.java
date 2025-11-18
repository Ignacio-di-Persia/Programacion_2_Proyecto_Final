package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.VehiculoDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.mappers.VehiculoMapper;
import com.tp.guarderia_central.models.entities.Socio;
import com.tp.guarderia_central.models.entities.Vehiculo;
import com.tp.guarderia_central.repositories.SocioRepository;
import com.tp.guarderia_central.repositories.VehiculoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculoServiceImpl implements IVehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;
    private final SocioRepository socioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> findAll() {
        return vehiculoRepository.findAll().stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> findBySocioId(Long socioId) {
        return vehiculoRepository.findBySocioId(socioId).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculoDTO findById(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));
        return vehiculoMapper.toDTO(vehiculo);
    }

    @Override
    @Transactional
    public VehiculoDTO save(VehiculoDTO vehiculoDTO) {
        if (vehiculoRepository.findByMatricula(vehiculoDTO.getMatricula()).isPresent()) {
            throw new RuntimeException("Error: La matrícula " + vehiculoDTO.getMatricula() + " ya está registrada.");
        }

        Socio socio = socioRepository.findById(vehiculoDTO.getSocioId())
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con id: " + vehiculoDTO.getSocioId()));

  
        Vehiculo vehiculo = vehiculoMapper.toEntity(vehiculoDTO);
        vehiculo.setId(null);
        vehiculo.setSocio(socio);

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo);
        return vehiculoMapper.toDTO(vehiculoGuardado);
    }

    @Override
    @Transactional
    public VehiculoDTO update(Long id, VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculoAActualizar = vehiculoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehículo no encontrado con id: " + id));

        Optional<Vehiculo> conMismaMatricula = vehiculoRepository.findByMatricula(vehiculoDTO.getMatricula());
        
        if (conMismaMatricula.isPresent() && !conMismaMatricula.get().getId().equals(id)) {
            throw new RuntimeException("Error: La matrícula " + vehiculoDTO.getMatricula() + " ya está registrada en otro vehículo.");
        }

        if (!vehiculoAActualizar.getSocio().getId().equals(vehiculoDTO.getSocioId())) {
            Socio nuevoSocio = socioRepository.findById(vehiculoDTO.getSocioId())
                    .orElseThrow(() -> new ResourceNotFoundException("Nuevo socio no encontrado"));
            vehiculoAActualizar.setSocio(nuevoSocio);
        }

        vehiculoMapper.updateVehiculoFromDto(vehiculoDTO, vehiculoAActualizar);

        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculoAActualizar);
        return vehiculoMapper.toDTO(vehiculoGuardado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehículo no encontrado con id: " + id);
        }
        vehiculoRepository.deleteById(id);
    }
}