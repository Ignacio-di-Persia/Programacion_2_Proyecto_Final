package com.guarderia.central.service;

import com.guarderia.central.entity.Empleado;
import com.guarderia.central.exception.EmpleadoException;
import com.guarderia.central.repository.EmpleadoRepository;
import com.guarderia.central.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

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
    public Empleado actualizarEmpleado(Long codigo, Empleado empleado){
        log.info("Actualizando empleado con DNI: {}", codigo);
        Empleado empleadoExistente = buscarPorId(codigo);
        
        // Verificar si el telefono ya existe en otro empleado
        empleadoRepository.findByTelefono(empleado.getTelefono()).ifPresent(s -> {
            if (!s.getDni().equals(codigo)) {
                throw new EmpleadoException("El telefono " + empleado.getTelefono() + " ya está registrado en otro empleado");
            }
        });
        
        // Actualizar campos
        empleadoExistente.setNombres(empleado.getNombres());
        empleadoExistente.setApellidos(empleado.getApellidos());
        empleadoExistente.setDireccion(empleado.getDireccion());
        empleadoExistente.setTelefono(empleado.getTelefono());
        empleadoExistente.setEspecialidad(empleado.getEspecialidad());
        
        return empleadoRepository.save(empleadoExistente);
    }
}
