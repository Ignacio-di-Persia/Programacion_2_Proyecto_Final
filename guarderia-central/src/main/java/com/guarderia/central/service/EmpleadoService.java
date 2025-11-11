package com.guarderia.central.service;

import com.guarderia.central.dto.EmpleadoDTO;
import com.guarderia.central.entity.Empleado;

import java.util.List;

public interface EmpleadoService {

    // Métodos existentes
    List<Empleado> listar();
    Empleado buscarPorId(Long codigo);
    Empleado guardar(Empleado empleado);
    void eliminar(Long codigo);

    // Nuevos métodos para Thymeleaf y DTO
    Empleado actualizarEmpleado(Long codigo, EmpleadoDTO dto);
    List<EmpleadoDTO> listarDTO();               
    EmpleadoDTO buscarDTO(Long codigo);          
    Empleado guardarDTO(EmpleadoDTO empleadoDTO); 
}