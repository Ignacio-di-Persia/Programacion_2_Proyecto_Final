package com.guarderia.central.controller;

import com.guarderia.central.entity.Socio;
import com.guarderia.central.service.SocioService;
import com.guarderia.central.service.GarageService;
import com.guarderia.central.dto.SocioDTO;
import com.guarderia.central.dto.SocioGarageDTO;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/socios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Slf4j
public class SocioRestController {

    private final SocioService socioService;
    private final GarageService garageService;

    
    @GetMapping
    public List<SocioDTO> listar() {
        return socioService.listarDTO();
    }

    @GetMapping("/{id}")
    public SocioDTO buscar(@PathVariable Long id) {
        return socioService.buscarDTO(id);
    }

    @PostMapping
    public ResponseEntity<SocioDTO> crear(@RequestBody @Valid SocioDTO socioDTO) {
        SocioDTO guardado = socioService.guardarDTO(socioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @PostMapping("/guardar")
    public ResponseEntity<SocioDTO> guardar(@RequestBody SocioDTO dto){
        return ResponseEntity.ok(socioService.guardarDTO(dto)); 
    }

    @PutMapping("/{id}")
    public SocioDTO editar(@PathVariable Long id, @RequestBody SocioDTO socioDTO) {
        socioDTO.setCodigo(id);
        return socioService.guardarDTO(socioDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.ok("Socio eliminado");
    }

    private SocioDTO convertToDTO(Socio s) {
        List<SocioGarageDTO> garages = s.getGaragesPropios().stream()
                .map(sg -> new SocioGarageDTO(
                        sg.getGarage().getCodigo()
                ))
                .toList();

        return SocioDTO.builder()
                .codigo(s.getCodigo())
                .dni(s.getDni())
                .nombres(s.getNombres())
                .apellidos(s.getApellidos())
                .direccion(s.getDireccion())
                .telefono(s.getTelefono())
                .garagePropios(garages)
                .build();
    }

  /* Metodos sin DTO
    // GET - Obtener todos los socios
    @GetMapping
    public ResponseEntity<List<Socio>> obtenerTodos() {
        log.info("GET /api/socios - Obteniendo todos los socios");
        List<Socio> socios = socioService.obtenerTodosLosSocios();
        return ResponseEntity.ok(socios);
    }

    // GET - Obtener socio por DNI
    @GetMapping("/{dni}")
    public ResponseEntity<Socio> obtenerPorDni(@PathVariable Integer dni) {
        log.info("GET /api/socios/{} - Obteniendo socio", dni);
        Socio socio = socioService.obtenerSocioPorDni(dni);
        return ResponseEntity.ok(socio);
    }

    // POST - Crear nuevo socio con garage
    @PostMapping
    public ResponseEntity<Socio> crearSocio(
            @Valid @RequestBody Socio socio,
            @RequestParam String garageCodigo) {

        log.info("POST /api/socios - Creando socio: {}, Garage: {}", socio, garageCodigo);
        Socio nuevoSocio = socioService.crearSocio(socio, garageCodigo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoSocio);
    }
    

    // PUT - Actualizar socio con garage
    @PutMapping("/{dni}")
    public ResponseEntity<Socio> actualizarSocio(
            @PathVariable Integer dni,
            @Valid @RequestBody Socio socio,
            @RequestParam String garageCodigo) {

        log.info("PUT /api/socios/{} - Actualizando socio: {}, Garage: {}", dni, socio, garageCodigo);
        Socio socioActualizado = socioService.actualizarSocio(dni, socio, garageCodigo);
        return ResponseEntity.ok(socioActualizado);
    }

    // DELETE - Eliminar socio
    @DeleteMapping("/{dni}")
    public ResponseEntity<Map<String, String>> eliminarSocio(@PathVariable Integer dni) {
        log.info("DELETE /api/socios/{} - Eliminando socio", dni);
        socioService.eliminarSocio(dni);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Socio eliminado correctamente");

        return ResponseEntity.ok(response);
    }*/

    // GET - Verificar si existe DNI
    @GetMapping("/existe/{dni}")
    public ResponseEntity<Map<String, Boolean>> existeDni(@PathVariable Integer dni) {
        log.info("GET /api/socios/existe/{} - Verificando DNI", dni);
        boolean existe = socioService.existeDni(dni);

        Map<String, Boolean> response = new HashMap<>();
        response.put("existe", existe);

        return ResponseEntity.ok(response);
    }

    // GET - Buscar por criterio (apellido o DNI)
    @GetMapping("/buscar")
    public ResponseEntity<List<Socio>> buscarPorCriterio(@RequestParam String criterio) {
        log.info("GET /api/socios/buscar?criterio={}", criterio);
        List<Socio> socios = socioService.buscarPorCriterio(criterio);
        return ResponseEntity.ok(socios);
    }

    // GET - Buscar por apellido
    @GetMapping("/buscar/apellido/{apellido}")
    public ResponseEntity<List<Socio>> buscarPorApellido(@PathVariable String apellido) {
        log.info("GET /api/socios/buscar/apellido/{}", apellido);
        List<Socio> socios = socioService.buscarPorApellido(apellido);
        return ResponseEntity.ok(socios);
    }

    // GET - Buscar por DNI parcial
    @GetMapping("/buscar/dni/{dniPrefix}")
    public ResponseEntity<List<Socio>> buscarPorDniParcial(@PathVariable String dniPrefix) {
        log.info("GET /api/socios/buscar/dni/{}", dniPrefix);
        List<Socio> socios = socioService.buscarPorDniParcial(dniPrefix);
        return ResponseEntity.ok(socios);
    }
 
}