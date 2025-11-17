package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import com.guarderia.central.entity.Socio;
import com.guarderia.central.exception.SocioException;
import com.guarderia.central.repository.SocioRepository;
import com.guarderia.central.repository.GarageRepository;
import com.guarderia.central.dto.SocioDTO;
import com.guarderia.central.dto.SocioGarageDTO;
import com.guarderia.central.entity.SocioGarage;
import com.guarderia.central.service.GarageService; 
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SocioServiceImpl implements SocioService {

    private final SocioRepository socioRepository;
    private final GarageRepository garageRepository;
    
    @Override
    public List<Socio> listar(){
        return socioRepository.findAll();
    }

    @Override
    public Socio buscarPorId(Long codigo){
        return socioRepository.findById(codigo)
                .orElseThrow(() -> new SocioException("Socio no encontrado")); 
    }

    @Override
    public Socio guardar(Socio socio){
        return socioRepository.save(socio);
    }

    @Override
    public void eliminar(Long codigo){
        if (!socioRepository.existsById(codigo)){
            throw new SocioException("El socio no existe");
        }
        socioRepository.deleteById(codigo);
    }   

    @Override
    public List<SocioDTO> listarDTO (){
        return listar().stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    @Override
    public SocioDTO buscarDTO (Long codigo){
        return convertirADTO(buscarPorId(codigo));
    }

    @Override
    public SocioDTO guardarDTO (SocioDTO dto){

        Socio socio;

        if(dto.getCodigo() != null) {
            socio = buscarPorId(dto.getCodigo());
        } else {
            socio = new Socio();
        }

        //Datos basicos
        socio.setNombres(dto.getNombres());
        socio.setApellidos(dto.getApellidos());
        socio.setDni(dto.getDni());
        socio.setCorreo(dto.getCorreo());
        socio.setVehiculo(dto.getVehiculo());

        socio.getGaragesPropios().clear();

        if(dto.getGaragePropios()!=null){
            for(SocioGarageDTO garageDTO : dto.getGaragePropios()){

                Garage garage = garageRepository.findById(garageDTO.getGarageCodigo())
                        .orElseThrow(() -> new RuntimeException("Garage no encontrado"+ garageDTO.getGarageCodigo()));

                SocioGarage sg = new SocioGarage();
                sg.setSocio(socio);
                sg.setGarage(garage);

                socio.getGaragesPropios().add(sg);
            }
        }

        return convertirADTO(socioRepository.save(socio));
    }

    private SocioDTO convertirADTO(Socio s){

        List<SocioGarageDTO> garages = s.getGaragesPropios().stream()
                .map(sg -> new SocioGarageDTO(sg.getGarage().getCodigo()))
                .toList();

        return SocioDTO.builder()
                .codigo(s.getCodigo())
                .dni(s.getDni())
                .nombres(s.getNombres())
                .apellidos(s.getApellidos())
                .direccion(s.getDireccion())
                .telefono(s.getTelefono())
                .correo(s.getCorreo())
                .vehiculo(s.getVehiculo())
                .garagePropios(garages)
                .build();
    }




    /* Servicios a implementar anteriores 
    @Override
    @Transactional(readOnly = true)
    public List<Socio> obtenerTodosLosSocios() {
        log.info("Obteniendo todos los socios");
        return socioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Socio obtenerSocioPorDni(Integer dni) {
        return socioRepository.findByDni(dni)
                .orElseThrow(() -> new SocioException("Socio con DNI " + dni + " no encontrado"));
    }

    @Override
    public Socio crearSocio(Socio socio, String garageCodigo) {
        log.info("Creando socio: {}", socio);

        if (socioRepository.existsByDni(socio.getDni())) {
            throw new SocioException("Ya existe un socio con DNI " + socio.getDni());
        }
        if (socioRepository.findByCorreo(socio.getCorreo()).isPresent()) {
            throw new SocioException("Ya existe un socio con correo " + socio.getCorreo());
        }

        Socio nuevoSocio = socioRepository.save(socio);

        Garage garage = garageService.obtenerGaragePorCodigo(garageCodigo);
        socioGarageService.asignarGarage(nuevoSocio, garage);

        return nuevoSocio;
    }

    @Override
    public Socio actualizarSocio(Integer dni, Socio socioNuevo, String garageCodigo) {
        Socio socioExistente = socioRepository.findById(dni)
                .orElseThrow(() -> new SocioException("No existe un socio con DNI " + dni));

        if (!socioExistente.getCorreo().equals(socioNuevo.getCorreo())
                && socioRepository.findByCorreo(socioNuevo.getCorreo()).isPresent()) {
            throw new SocioException("Ya existe un socio con correo " + socioNuevo.getCorreo());
        }

        // Actualizar datos del socio
        socioExistente.setNombres(socioNuevo.getNombres());
        socioExistente.setApellidos(socioNuevo.getApellidos());
        socioExistente.setCorreo(socioNuevo.getCorreo());
        socioExistente.setVehiculo(socioNuevo.getVehiculo());

        Socio actualizado = socioRepository.save(socioExistente);

        // Actualizar garage
        Garage garageNuevo = garageService.obtenerGaragePorCodigo(garageCodigo);
        socioGarageService.cambiarGarage(actualizado, garageNuevo);

        return actualizado;
    }

    @Override
    public void eliminarSocio(Integer dni) {
        Socio socio = socioRepository.findById(dni)
                .orElseThrow(() -> new SocioException("No existe un socio con DNI " + dni));

        socioGarageService.liberarGarage(socio);
        socioRepository.delete(socio);

        log.info("Socio eliminado correctamente: {}", dni);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeDni(Integer dni) {
        return socioRepository.existsByDni(dni);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorApellido(String apellido) {
        return socioRepository.findByApellidosStartingWithIgnoreCase(apellido);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorDniParcial(String dniPrefix) {
        return socioRepository.findByDniStartingWith(dniPrefix);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarPorCriterio(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return obtenerTodosLosSocios();
        }
        return socioRepository.buscarPorApellidoODni(criterio.trim());
    }
        */
}
