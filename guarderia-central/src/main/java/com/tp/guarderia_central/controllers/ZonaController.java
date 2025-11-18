package com.tp.guarderia_central.controllers;

import com.tp.guarderia_central.dtos.ZonaDTO;
import com.tp.guarderia_central.services.IZonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/zonas")
@RequiredArgsConstructor
public class ZonaController {

    private final IZonaService zonaService;

    @GetMapping("/listar")
    public String listarZonas(Model model) {
        List<ZonaDTO> zonas = zonaService.findAll();
        model.addAttribute("zonas", zonas);
        return "zonas/lista-zonas";
    }

    @GetMapping("/agregar")
    public String agregarZona(Model model) {
        model.addAttribute("zona", new ZonaDTO());
        model.addAttribute("titulo", "Nueva Zona");
        model.addAttribute("actionUrl", "/zonas/guardar");
        return "zonas/form-zona";
    }

    @GetMapping("/editar/{id}")
    public String editarZona(@PathVariable Long id, Model model) {
        ZonaDTO zona = zonaService.findById(id); 
        model.addAttribute("zona", zona);
        model.addAttribute("titulo", "Editar Zona");
        model.addAttribute("actionUrl", "/zonas/actualizar/" + id);
        return "zonas/form-zona";
    }

    @PostMapping("/guardar")
    public String guardarZona(@Valid @ModelAttribute("zona") ZonaDTO zonaDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/zonas/guardar");
            return "zonas/form-zona";
        }

        try {
            zonaService.save(zonaDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/zonas/guardar");
            model.addAttribute("error", e.getMessage());
            return "zonas/form-zona";
        }

        flash.addFlashAttribute("success", "Zona creada correctamente");
        return "redirect:/zonas/listar";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarZona(@PathVariable Long id, @Valid @ModelAttribute("zona") ZonaDTO zonaDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/zonas/actualizar/" + id);
            return "zonas/form-zona";
        }

        try {
            zonaService.update(id, zonaDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/zonas/actualizar/" + id);
            model.addAttribute("error", e.getMessage());
            return "zonas/form-zona";
        }

        flash.addFlashAttribute("success", "Zona actualizada correctamente");
        return "redirect:/zonas/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarZona(@PathVariable Long id, RedirectAttributes flash) {
        try {
            zonaService.deleteById(id);
            flash.addFlashAttribute("success", "Zona eliminada correctamente");
        } catch (RuntimeException e) { 
            flash.addFlashAttribute("error", e.getMessage());
        }
        
        return "redirect:/zonas/listar";
    }
}