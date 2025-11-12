package com.guarderia.central.controller;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.entity.Zona;
import com.guarderia.central.repository.ZonaRepository;
import com.guarderia.central.service.GarageService;
import com.guarderia.central.service.GarageServiceImpl;
import com.guarderia.central.service.ZonaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/zonas")
@RequiredArgsConstructor
@Slf4j
public class ZonaRestController {

    private final ZonaService zonaService;
    private final GarageService garageService;

    @GetMapping
    public List<Zona> listarZonas() {
        return zonaService.listarZonas();
    }

    @GetMapping("/{codigo}")
    public Zona obtenerZona(@PathVariable String codigo) {
        return zonaService.obtenerZonaPorCodigo(codigo);
    }

    @GetMapping("/existe/{codigo}")
    public Map<String, Boolean> existeZona(@PathVariable String codigo) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", zonaService.existeZona(codigo));
        return response;
    }

    @PostMapping
    public Zona guardarZona(@RequestBody Zona zona) {
        return zonaService.guardarZona(zona);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Map<String, String>> eliminarZona(@PathVariable String codigo) {
        zonaService.eliminarZona(codigo);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Zona eliminada con éxito");
        return ResponseEntity.ok(response);
    }

        // Método para mostrar la lista de garages de una zona
         @GetMapping("/{codigo}/lista-garage")
        public String BuscarGaragesZonas(@PathVariable String codigo, Model model) {
        // Obtener la zona por código
        Zona zona = zonaService.obtenerZonaPorCodigo(codigo);
       /* if (zona == null) */ 
            // Obtener los garages asociados a esa zona
            List<Garage> garages = garageService.obtenerGaragesPorZona(codigo); 
            model.addAttribute("zona", zona); // Agregar la zona al modelo
            model.addAttribute("garages", garages); // Agregar los garages al modelo
    
            return "lista-garage"; // Nombre de la vista Thymeleaf donde se mostrarán los garages
        

    }

}