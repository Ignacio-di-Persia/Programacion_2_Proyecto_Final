package com.guarderia.central.service;

import com.guarderia.central.dto.EmpleadoDTO;
import com.guarderia.central.dto.EmpleadoZonaDTO;
import com.guarderia.central.entity.Empleado;
import com.guarderia.central.entity.EmpleadoZona;
import com.guarderia.central.entity.EmpleadoZonaId;
import com.guarderia.central.entity.Zona;
import com.guarderia.central.exception.EmpleadoException;
import com.guarderia.central.repository.EmpleadoRepository;
import com.guarderia.central.repository.ZonaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final ZonaRepository zonaRepository; 
    private final ZonaService zonaService;

    @Override
    public List<Empleado> listar() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarPorId(Long codigo) {
        return empleadoRepository.findById(codigo)
                .orElseThrow(() -> new EmpleadoException("Empleado no encontrado"));
    }

    @Override
    public Empleado guardar(Empleado empleado) {
        return empleadoRepository.save(empleado);
    }

    @Override
    public void eliminar(Long codigo) {
        if (!empleadoRepository.existsById(codigo)) {
            throw new EmpleadoException("El empleado no existe");
        }
        empleadoRepository.deleteById(codigo);
    }

    @Override
    public Empleado actualizarEmpleado(Long codigo, EmpleadoDTO dto) {
    Empleado existente = buscarPorId(codigo);

    // Actualizar datos básicos
    existente.setNombres(dto.getNombres());
    existente.setApellidos(dto.getApellidos());
    existente.setDireccion(dto.getDireccion());
    existente.setTelefono(dto.getTelefono());
    existente.setEspecialidad(dto.getEspecialidad());

    // Actualizar zonas
    if (dto.getZonasAsignadas() != null) {
        // Limpiar lista existente para evitar problemas de Hibernate
        existente.getZonasAsignadas().clear();

        List<EmpleadoZona> zonas = dto.getZonasAsignadas().stream()
                .map(ezDTO -> {
                    Zona zona = zonaService.obtenerZonaPorCodigo(ezDTO.getZonaCodigo());
                    return EmpleadoZona.builder()
                            .empleado(existente)
                            .zona(zona)
                            .vehiculosAsignados(ezDTO.getVehiculosAsignados())
                            .build();
                })
                .collect(Collectors.toCollection(ArrayList::new));

        existente.getZonasAsignadas().addAll(zonas);
    }

    return guardar(existente);
    }

    // DTO
    @Override
    public List<EmpleadoDTO> listarDTO() {
        return listar().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO buscarDTO(Long codigo) {
        return convertirADTO(buscarPorId(codigo));
    }

    @Override
    public Empleado guardarDTO(EmpleadoDTO dto) {

    Empleado empleado;

    if (dto.getCodigo() != null) {
        empleado = buscarPorId(dto.getCodigo());
    } else {
        empleado = new Empleado();
    }

    // Datos básicos
    empleado.setDni(dto.getDni());
    empleado.setNombres(dto.getNombres());
    empleado.setApellidos(dto.getApellidos());
    empleado.setDireccion(dto.getDireccion());
    empleado.setTelefono(dto.getTelefono());
    empleado.setEspecialidad(dto.getEspecialidad());

    // Limpiar asignaciones anteriores (si las hubiera)
    empleado.getZonasAsignadas().clear();

    if (dto.getZonasAsignadas() != null) {
        for (EmpleadoZonaDTO zonaDTO : dto.getZonasAsignadas()) {

            Zona zona = zonaRepository.findById(zonaDTO.getZonaCodigo())
                    .orElseThrow(() -> new RuntimeException("Zona no encontrada: " + zonaDTO.getZonaCodigo()));

            EmpleadoZona ez = new EmpleadoZona();
            ez.setEmpleado(empleado);
            ez.setZona(zona);
            ez.setVehiculosAsignados(zonaDTO.getVehiculosAsignados());

            empleado.getZonasAsignadas().add(ez);
        }
    }

    return empleadoRepository.save(empleado);
    }

    private EmpleadoDTO convertirADTO(Empleado e) {
    List<EmpleadoZonaDTO> zonas = e.getZonasAsignadas().stream()
            .map(ez -> new EmpleadoZonaDTO(ez.getZona().getCodigo(), ez.getVehiculosAsignados()))
            .toList();

    return EmpleadoDTO.builder()
            .codigo(e.getCodigo())
            .dni(e.getDni())
            .nombres(e.getNombres())
            .apellidos(e.getApellidos())
            .direccion(e.getDireccion())
            .telefono(e.getTelefono())
            .especialidad(e.getEspecialidad())
            .zonasAsignadas(zonas)
            .build();
    }
}
