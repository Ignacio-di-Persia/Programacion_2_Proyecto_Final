package com.guarderia.central.controller;

import com.guarderia.central.entity.Empleado;
import com.guarderia.central.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/empleados")
public class EmpleadoRestController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }

    @GetMapping("/{id}")
    public Empleado buscar(@PathVariable("id") Long codigo) {
        return empleadoService.buscarPorId(codigo);
    }

    @PostMapping
    public Empleado crear(@RequestBody Empleado empleado) {
        return empleadoService.guardar(empleado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable("id") Long codigo) {
        empleadoService.eliminar(codigo);
        return ResponseEntity.ok().body("Empleado eliminado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") Long codigo) {
        empleadoService.actualizarEmpleado(codigo);
        return ResponseEntity.ok().body("Empleado actualizado");
    }

}