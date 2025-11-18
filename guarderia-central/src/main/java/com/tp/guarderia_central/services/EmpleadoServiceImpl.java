package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.EmpleadoDTO;
import com.tp.guarderia_central.dtos.GarageDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.mappers.EmpleadoMapper;
import com.tp.guarderia_central.mappers.GarageMapper;
import com.tp.guarderia_central.models.entities.Empleado;
import com.tp.guarderia_central.models.entities.Garage;
import com.tp.guarderia_central.models.enums.RolNombre;
import com.tp.guarderia_central.models.security.Rol;
import com.tp.guarderia_central.models.security.Usuario;
import com.tp.guarderia_central.repositories.EmpleadoRepository;
import com.tp.guarderia_central.repositories.GarageRepository;
import com.tp.guarderia_central.repositories.RolRepository;
import com.tp.guarderia_central.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements IEmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final GarageRepository garageRepository;
    private final GarageMapper garageMapper;

    @Override
    @Transactional(readOnly = true)
    public List<EmpleadoDTO> findAll() {
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoDTO findById(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));
        return empleadoMapper.toDTO(empleado);
    }
    
    @Override
    @Transactional(readOnly = true)
    public EmpleadoDTO findByCodigo(String codigo) {
        Empleado empleado = empleadoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con código: " + codigo));
        return empleadoMapper.toDTO(empleado);
    }

    @Override
    @Transactional
    public EmpleadoDTO save(EmpleadoDTO empleadoDTO) {
        if (empleadoRepository.findByCodigo(empleadoDTO.getCodigo()).isPresent()) {
            throw new RuntimeException("Error: El código " + empleadoDTO.getCodigo() + " ya está en uso.");
        }
        String username = empleadoDTO.getCodigo(); // Por defecto el username es el codigo del empleado
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Error: El nombre de usuario " + username + " ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username); 
        usuario.setPassword(passwordEncoder.encode(empleadoDTO.getCodigo())); // Por defecto la contraseña es el codigo del empleado

        Rol rolEmpleado = rolRepository.findByNombre(RolNombre.ROLE_EMPLEADO)
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_EMPLEADO' no encontrado."));
        usuario.setRoles(List.of(rolEmpleado));

        Empleado empleado = empleadoMapper.toEntity(empleadoDTO);
        empleado.setId(null); // Para que la base de datos le inserte el auto_incremental
        empleado.setUsuario(usuario); 

        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        
        return empleadoMapper.toDTO(empleadoGuardado);
    }

    @Override
    @Transactional
    public EmpleadoDTO update(Long id, EmpleadoDTO empleadoDTO) {
        Empleado empleadoEncontrado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + id));

        Optional<Empleado> empleadoUsandoCodigo = empleadoRepository.findByCodigo(empleadoDTO.getCodigo());
        if (empleadoUsandoCodigo.isPresent() && !empleadoUsandoCodigo.get().getId().equals(id)) {
            throw new RuntimeException("Error: El código " + empleadoDTO.getCodigo() + " ya está en uso por otro empleado.");
        }
        
        String nuevoUsername = empleadoDTO.getCodigo(); // nuevo_username
        Usuario usuarioEmpleadoEncontrado = empleadoEncontrado.getUsuario(); //usuario anterior

        if (!usuarioEmpleadoEncontrado.getUsername().equals(nuevoUsername)) { // !(usuername_viejo==nuevo_username) es decir actualice el username
            if (usuarioRepository.findByUsername(nuevoUsername).isPresent()) { //Chequeamos que no este presente en otro usuario
                throw new RuntimeException("Error: El nuevo código (username) " + nuevoUsername + " ya está en uso.");
            }
            // Sino lo utilizamos
            usuarioEmpleadoEncontrado.setUsername(nuevoUsername);
        }

        empleadoMapper.updateEmpleadoFromDto(empleadoDTO, empleadoEncontrado);

        Empleado empleadoGuardado = empleadoRepository.save(empleadoEncontrado);
        
        return empleadoMapper.toDTO(empleadoGuardado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado con id: " + id);
        }
        empleadoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void actualizarGaragesAsignados(Long empleadoId, List<Long> garageIds) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + empleadoId));

        List<Garage> nuevosGaragesAsignados;

        if (garageIds == null || garageIds.isEmpty()) {
            nuevosGaragesAsignados = new ArrayList<>();
        } else {
            nuevosGaragesAsignados = garageRepository.findAllById(garageIds);

            if (nuevosGaragesAsignados.size() != garageIds.size()) {
                throw new RuntimeException("Error: Uno o más IDs de garage proporcionados no existen en la base de datos.");
            }
        }

        empleado.setGaragesAsignados(nuevosGaragesAsignados);
        empleadoRepository.save(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> obtenerIdsGaragesAsignados(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con id: " + empleadoId));
        
        // Transformamos la lista de entidades Garage a una lista de IDs (Long)
        return empleado.getGaragesAsignados().stream()
                .map(Garage::getId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GarageDTO> findGaragesByUsername(String username) {
        Empleado empleado = empleadoRepository.findByCodigo(username)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con código: " + username));

        return empleado.getGaragesAsignados().stream()
                .map(garageMapper::toDTO)
                .collect(Collectors.toList());
    }
}