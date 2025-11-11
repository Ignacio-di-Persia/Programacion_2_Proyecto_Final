package com.guarderia.central.controller;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.service.GarageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/garages") 
@RequiredArgsConstructor
@Slf4j
public class GarageRestController {

    private final GarageService garageService;

    @GetMapping
    public List<Garage> listarGarages() {
        return garageService.listarGarages();
    }

    @GetMapping("/disponibles")
    public List<Garage> listarGarageDisponibles(){
        return garageService.listarGaragesDisponibles();
    }

    @GetMapping("/{id}")
    public Garage obtenerGarage(@PathVariable Long id) {
        return garageService.obtenerGaragePorId(id);
    }
}
