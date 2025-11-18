// ESTA ES LA VERSIÓN CORREGIDA Y ACTUALIZADA
package com.tp.guarderia_central.services;

import com.tp.guarderia_central.dtos.SocioDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.mappers.DireccionMapper;
import com.tp.guarderia_central.mappers.SocioMapper;
import com.tp.guarderia_central.models.entities.Socio;
import com.tp.guarderia_central.models.enums.RolNombre;
import com.tp.guarderia_central.models.security.Rol;
import com.tp.guarderia_central.models.security.Usuario;
import com.tp.guarderia_central.repositories.RolRepository;
import com.tp.guarderia_central.repositories.SocioRepository;
import com.tp.guarderia_central.repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SocioServiceImpl implements ISocioService {

    private final SocioRepository socioRepository;
    private final SocioMapper socioMapper;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<SocioDTO> findAll() {
        List<Socio> socios = socioRepository.findAll();
        return socios.stream()
                .map(socioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SocioDTO findById(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con id: " + id));
        
        return socioMapper.toDTO(socio);
    }

    @Override
    @Transactional
    public SocioDTO save(SocioDTO socioDTO) {
        Optional<Socio> socioExistente = socioRepository.findByDni(socioDTO.getDni());
        if (socioExistente.isPresent()) {
            throw new RuntimeException("El DNI " + socioDTO.getDni() + " ya está en uso.");
        }

        String username = socioDTO.getDni();
        if (usuarioRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Error: El nombre de usuario (DNI) " + username + " ya está en uso.");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setPassword(passwordEncoder.encode(socioDTO.getDni())); 

        Rol rolSocio = rolRepository.findByNombre(RolNombre.ROLE_SOCIO)
                .orElseThrow(() -> new RuntimeException("Error: Rol 'ROLE_SOCIO' no encontrado."));
        usuario.setRoles(List.of(rolSocio));
        
        Socio socio = socioMapper.toEntity(socioDTO);
        socio.setId(null); // Forzar INSERT
        socio.setUsuario(usuario); 

        Socio socioGuardado = socioRepository.save(socio);
        
        return socioMapper.toDTO(socioGuardado);
    }


    @Override
    @Transactional
    public SocioDTO update(Long id, SocioDTO socioDTO) {
        Socio socioAActualizar = socioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Socio no encontrado con id: " + id));
        Optional<Socio> socioConMismoDni = socioRepository.findByDni(socioDTO.getDni());
        if (socioConMismoDni.isPresent() && !socioConMismoDni.get().getId().equals(id)) {
            throw new RuntimeException("Error: El DNI " + socioDTO.getDni() + " ya está en uso por otro socio.");
        }
        
        String nuevoUsername = socioDTO.getDni();
        Usuario usuarioAsociado = socioAActualizar.getUsuario();

        if (!usuarioAsociado.getUsername().equals(nuevoUsername)) { 
            if (usuarioRepository.findByUsername(nuevoUsername).isPresent()) { 
                throw new RuntimeException("Error: El nuevo DNI (username) " + nuevoUsername + " ya está en uso.");
            }
            usuarioAsociado.setUsername(nuevoUsername);
        }

        socioMapper.updateSocioFromDto(socioDTO, socioAActualizar);
        Socio socioGuardado = socioRepository.save(socioAActualizar);
        return socioMapper.toDTO(socioGuardado);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!socioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Socio no encontrado con id: " + id);
        }
        socioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public SocioDTO findByUsername(String username) {
        Socio socio = socioRepository.findByUsuarioUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un socio vinculado al usuario: " + username));
        return socioMapper.toDTO(socio);
    }
}