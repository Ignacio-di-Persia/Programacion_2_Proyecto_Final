package com.guarderia.central.service;

import com.guarderia.central.entity.Garage;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GarageServiceImpl implements GarageService {

    private final GarageRepository garageRepository;

    @Override
    public List<Garage> listarGarages() {
        return garageRepository.findAll();
    }

    @Override
    public Garage obtenerGaragePorId(Long id) {
        return garageRepository.findById(id)
                .orElseThrow(() -> new GarageNotFoundException("Garage con ID '" + id + "' no encontrado"));
    }

    @Override
    public Garage guardarGarage(Garage garage) {
        log.info("Guardando garage: {}", garage);
        return garageRepository.save(garage);
    }

    @Override
    public void eliminarGarage(Long id) {
        if (!garageRepository.existsById(id)) {
            throw new GarageNotFoundException("No existe el garage con ID '" + id + "'");
        }
        garageRepository.deleteById(id);
    }

    @Override
    public boolean existeGarage(Long id) {
        return garageRepository.existsById(id);
    }

    @Override
    public List<Garage> listarGaragesDisponibles() {
        return garageRepository.findByOcupadoFalse();

}
