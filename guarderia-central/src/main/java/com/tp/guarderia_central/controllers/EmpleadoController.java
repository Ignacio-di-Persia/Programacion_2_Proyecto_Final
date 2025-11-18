package com.tp.guarderia_central.controllers;

import com.tp.guarderia_central.dtos.AsignacionEmpleadoGarageDTO;
import com.tp.guarderia_central.dtos.EmpleadoDTO;
import com.tp.guarderia_central.dtos.GarageDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.services.IEmpleadoService;
import com.tp.guarderia_central.services.IGarageService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoController {

    private final IEmpleadoService empleadoService;
    private final IGarageService garageService;
    

    @GetMapping("/listar")
    public String listarEmpleados(Model model) {
        List<EmpleadoDTO> empleados = empleadoService.findAll();
        model.addAttribute("empleados", empleados);
        return "empleados/lista-empleados";
    }

    @GetMapping("/agregar")
    public String agregarEmpleado(Model model) {
        model.addAttribute("empleado", new EmpleadoDTO());
        model.addAttribute("titulo", "Nuevo Empleado");
        model.addAttribute("actionUrl", "/empleados/guardar");
        return "empleados/form-empleado";
    }

    @GetMapping("/editar/{id}")
    public String editarEmpleado(@PathVariable Long id, Model model) {
        EmpleadoDTO empleado = empleadoService.findById(id); 
        model.addAttribute("empleado", empleado);
        model.addAttribute("titulo", "Editar Empleado");
        model.addAttribute("actionUrl", "/empleados/actualizar/" + id);
        return "empleados/form-empleado";
    }

    @PostMapping("/guardar")
    public String guardarEmpleado(@Valid @ModelAttribute("empleado") EmpleadoDTO empleadoDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/empleados/guardar");
            return "empleados/form-empleado";
        }
        try {
            empleadoService.save(empleadoDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/empleados/guardar");
            model.addAttribute("error", e.getMessage());
            return "empleados/form-empleado";
        }
        flash.addFlashAttribute("success", "Empleado creado correctamente");
        return "redirect:/empleados/listar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarEmpleado(@PathVariable Long id, @Valid @ModelAttribute("empleado") EmpleadoDTO empleadoDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/empleados/actualizar/" + id);
            return "empleados/form-empleado";
        }

        try {
            empleadoService.update(id, empleadoDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/empleados/actualizar/" + id);
            model.addAttribute("error", e.getMessage());
            return "empleados/form-empleado";
        }

        flash.addFlashAttribute("success", "Empleado actualizado correctamente");
        return "redirect:/empleados/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id, RedirectAttributes flash) {
        try {
            empleadoService.deleteById(id);
            flash.addFlashAttribute("success", "Empleado eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            flash.addFlashAttribute("error", "Error: No se encontró el empleado a eliminar.");
        }
        return "redirect:/empleados/listar";
    }


    // Asignacion de empleados a garages
    
    @GetMapping("/asignar-garages/{id}")
    public String mostrarAsignacionGarages(@PathVariable Long id, Model model) {
        EmpleadoDTO empleado = empleadoService.findById(id);
        
        AsignacionEmpleadoGarageDTO asignacion = new AsignacionEmpleadoGarageDTO();
        asignacion.setEmpleadoId(id);

        List<Long> idsActuales = empleadoService.obtenerIdsGaragesAsignados(id);
        asignacion.setGarageIds(idsActuales);

        model.addAttribute("empleado", empleado);
        model.addAttribute("asignacion", asignacion); // Ahora lleva los IDs cargados
        model.addAttribute("titulo", "Asignar Garages a: " + empleado.getNombre());
        
        List<GarageDTO> todosGarages = garageService.findAll();
        model.addAttribute("todosGarages", todosGarages);
        
        return "empleados/asignar-garages";
    }

    @PostMapping("/asignar-garages")
    public String procesarAsignacionGarages(@Valid @ModelAttribute("asignacion") AsignacionEmpleadoGarageDTO dto, BindingResult result, RedirectAttributes flash) {
        if (result.hasErrors()) {
            flash.addFlashAttribute("error", "Error: Datos inválidos en la asignación.");
            return "redirect:/empleados/asignar-garages/" + dto.getEmpleadoId();
        }
        try {
            empleadoService.actualizarGaragesAsignados(dto.getEmpleadoId(), dto.getGarageIds());
        } catch (RuntimeException e) {
            flash.addFlashAttribute("error", "Error de asignación: " + e.getMessage());
            return "redirect:/empleados/asignar-garages/" + dto.getEmpleadoId();
        }
        
        flash.addFlashAttribute("success", "Garages asignados correctamente.");
        return "redirect:/empleados/listar";
    }

    @GetMapping("/ver-garages/{id}")
    public String verGaragesAsignados(@PathVariable Long id, Model model) {
        // findById carga la entidad Empleado (con sus garages, gracias a FetchType.EAGER)
        EmpleadoDTO empleado = empleadoService.findById(id); 
        model.addAttribute("empleado", empleado);
        return "empleados/ver-garages";
    }
}
