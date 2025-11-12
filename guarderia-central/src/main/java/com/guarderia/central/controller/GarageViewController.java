package com.guarderia.central.controller;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.service.GarageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/garages")
@RequiredArgsConstructor
public class GarageViewController {

    private final GarageService GarageService;

    @GetMapping("/listar")
    public String listaGarage(Model model) {
        model.addAttribute("garage", GarageService.listarGarages());
        return "lista-garage";
     }   


}
