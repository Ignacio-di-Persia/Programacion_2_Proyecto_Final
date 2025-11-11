package com.guarderia.central.controller;

import com.guarderia.central.dto.EmpleadoDTO;
import com.guarderia.central.service.EmpleadoService;
import com.guarderia.central.service.ZonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/empleados")
public class EmpleadoViewController {

    private final EmpleadoService empleadoService;
    private final ZonaService zonaService;

    @GetMapping("/lista-empleado")
    public String listaEmpleado(Model model) {
        List<EmpleadoDTO> empleados = empleadoService.listarDTO();
        model.addAttribute("empleados", empleados);
        return "lista-empleados";
    }

    @GetMapping("/agregar-empleado")
    public String mostrarFormulario(Model model) {
        model.addAttribute("empleado", new EmpleadoDTO());
        model.addAttribute("zonas", zonaService.listarZonas());
        return "agregar-empleado";
    }

    @GetMapping("/editar-empleado/{id}")
    public String mostrarEditar(@PathVariable Long id, Model model) {
        model.addAttribute("empleado", empleadoService.buscarDTO(id));
        model.addAttribute("zonas", zonaService.listarZonas());
        return "agregar-empleado";
    }

    @PostMapping
    public String guardar(@ModelAttribute EmpleadoDTO empleadoDTO) {
        empleadoService.guardarDTO(empleadoDTO); // Guarda empleado y zonas en un solo paso
        return "redirect:/empleados/lista-empleado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        empleadoService.eliminar(id);
        return "redirect:/empleados/lista-empleado";
    }
}
