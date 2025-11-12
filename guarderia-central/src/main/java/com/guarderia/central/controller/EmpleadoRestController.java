package com.guarderia.central.controller;

import com.guarderia.central.dto.EmpleadoDTO;
import com.guarderia.central.entity.Empleado;
import com.guarderia.central.dto.EmpleadoZonaDTO;
import com.guarderia.central.entity.Empleado;
import com.guarderia.central.entity.EmpleadoZona;
import com.guarderia.central.entity.Zona;
import com.guarderia.central.service.EmpleadoService;
import com.guarderia.central.service.ZonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empleados")
public class EmpleadoRestController {

    private final EmpleadoService empleadoService;
    private final ZonaService zonaService;

    @GetMapping
    public List<EmpleadoDTO> listar() {
        return empleadoService.listarDTO();
    }

    @GetMapping("/{id}")
    public EmpleadoDTO buscar(@PathVariable Long id) {
        return empleadoService.buscarDTO(id);
    }

    @PostMapping
    public EmpleadoDTO crear(@RequestBody EmpleadoDTO empleadoDTO) {
     return convertToDTO(empleadoService.guardarDTO(empleadoDTO));
    }

    @PutMapping("/{id}")
    public EmpleadoDTO editar(@PathVariable Long id, @RequestBody EmpleadoDTO empleadoDTO) {
        empleadoDTO.setCodigo(id);
        return convertToDTO(empleadoService.guardarDTO(empleadoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return ResponseEntity.ok("Empleado eliminado");
    }

    private EmpleadoDTO convertToDTO(Empleado e) {
    List<EmpleadoZonaDTO> zonas = e.getZonasAsignadas().stream()
            .map(ez -> new EmpleadoZonaDTO(
                    ez.getZona().getCodigo(),
                    ez.getVehiculosAsignados()
            ))
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