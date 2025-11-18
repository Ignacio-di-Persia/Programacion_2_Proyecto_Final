package com.tp.guarderia_central.controllers;

import com.tp.guarderia_central.dtos.SocioDTO;
import com.tp.guarderia_central.exceptions.ResourceNotFoundException;
import com.tp.guarderia_central.services.ISocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/socios")
@RequiredArgsConstructor 
public class SocioController {

    private final ISocioService socioService;

    @GetMapping("/listar")
    public String listarSocios(Model model) {
        List<SocioDTO> socios = socioService.findAll();
        model.addAttribute("socios", socios);
        return "socios/lista-socios";
    }

    @GetMapping("/agregar")
    public String agregarSocio(Model model) {
        model.addAttribute("socio", new SocioDTO());
        model.addAttribute("titulo", "Nuevo Socio");
        model.addAttribute("actionUrl", "/socios/guardar");
        return "socios/form-socio";
    }

    @GetMapping("/editar/{id}")
    public String editarSocio(@PathVariable Long id, Model model) {
        SocioDTO socio = socioService.findById(id); 
        model.addAttribute("socio", socio);
        model.addAttribute("titulo", "Editar Socio");
        model.addAttribute("actionUrl", "/socios/actualizar/" + id);
        return "socios/form-socio";
    }

    @PostMapping("/guardar")
    public String guardarSocio(@Valid @ModelAttribute("socio") SocioDTO socioDTO, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/socios/guardar"); 
            return "socios/form-socio";
        }
        try {
            socioService.save(socioDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/socios/guardar");
            model.addAttribute("error", e.getMessage());
            return "socios/form-socio";
        }
        flash.addFlashAttribute("success", "Socio creado correctamente");
        return "redirect:/socios/listar";
    }

    @PostMapping("/actualizar/{id}") 
    public String actualizarSocio(@PathVariable Long id, @Valid @ModelAttribute("socio") SocioDTO socioDTO, BindingResult result, Model model, RedirectAttributes flash) {
        
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/socios/actualizar/" + id);
            return "socios/form-socio";
        }
        try {
            socioService.update(id, socioDTO);
        } catch (RuntimeException e) {
            model.addAttribute("titulo", "Error - Corregir formulario");
            model.addAttribute("actionUrl", "/socios/actualizar/" + id);
            model.addAttribute("error", e.getMessage());
            return "socios/form-socio";
        }
        flash.addFlashAttribute("success", "Socio actualizado correctamente");
        return "redirect:/socios/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarSocio(@PathVariable Long id, RedirectAttributes flash) {
        try {
            socioService.deleteById(id);
            flash.addFlashAttribute("success", "Socio eliminado correctamente");
        } catch (ResourceNotFoundException e) {
            flash.addFlashAttribute("error", "Error: No se encontró el socio a eliminar.");
        }
        return "redirect:/socios/listar";
    }
}