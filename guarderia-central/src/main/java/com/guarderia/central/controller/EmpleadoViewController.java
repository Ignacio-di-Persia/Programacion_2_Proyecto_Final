package com.guarderia.central.controller;

import com.guarderia.central.entity.Empleado;
import com.guarderia.central.service.EmpleadoService;
import com.guarderia.central.service.ZonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoViewController {

    private final EmpleadoService empleadoService;
    private final ZonaService zonaService;

    @GetMapping("/lista-empleado")
    public String listaEmpleado(Model model) {
        model.addAttribute("empleados", empleadoService.listar());
        return "lista-empleados";
    }

    @GetMapping("/agregar-empleado")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("zonas", zonaService.listarZonas()); // Para dropdown de zonas
        return "agregar-empleado";
    }

    @PostMapping
    public String guardar(@ModelAttribute Empleado empleado) {
        empleadoService.guardar(empleado);
        return "redirect:/empleados/lista-empleado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Long codigo) {
        empleadoService.eliminar(codigo);
        return "redirect:/empleados/lista-empleado";
    }
}
