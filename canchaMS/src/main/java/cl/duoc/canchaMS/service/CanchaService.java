package cl.duoc.canchaMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.canchaMS.client.SedeClient;
import cl.duoc.canchaMS.dto.CanchaDTO;
import cl.duoc.canchaMS.dto.SedeDTO;
import cl.duoc.canchaMS.model.Cancha;
import cl.duoc.canchaMS.repository.CanchaRepository;

@Service
public class CanchaService {

    @Autowired
    private CanchaRepository repository;

    @Autowired
    private SedeClient sedeClient;


    public List<Cancha> listar() {
        return repository.findAll();
    }

    public Cancha guardar(Cancha cancha) {
        return repository.save(cancha);
    }

    public Cancha buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cancha no encontrada"));
    }

    public Cancha actualizar(Integer id, Cancha canchaNueva) {
        Cancha canchaExistente = buscarPorId(id);

        canchaExistente.setNombre(canchaNueva.getNombre());
        canchaExistente.setCapacidad(canchaNueva.getCapacidad());
        canchaExistente.setPrecioPorHora(canchaNueva.getPrecioPorHora());
        canchaExistente.setTipoCancha(canchaNueva.getTipoCancha());
        canchaExistente.setSedeId(canchaNueva.getSedeId());
        canchaExistente.setDisponible(canchaNueva.getDisponible());

        return repository.save(canchaExistente);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public List<Cancha> listarDisponibles() {
        return repository.findByDisponibleTrue();
    }

    public List<Cancha> listarPorSede(Integer sedeId) {
        return repository.findBySedeId(sedeId);
    }

    public List<Cancha> listarPorSedeDisponibles(Integer sedeId) {
        return repository.findBySedeIdAndDisponibleTrue(sedeId);
    }

    public Cancha marcarNoDisponible(Integer id) {
        Cancha cancha = buscarPorId(id);
        cancha.setDisponible(false);
        return repository.save(cancha);
    }

    public CanchaDTO obtenerDTO(Integer id) {
        Cancha cancha = buscarPorId(id);

        SedeDTO sede = sedeClient.obtenerSede(cancha.getSedeId());

        return new CanchaDTO(
                cancha.getId(),
                cancha.getNombre(),
                cancha.getCapacidad(),
                cancha.getPrecioPorHora(),
                cancha.getDisponible(),
                cancha.getTipoCancha().getNombre(),
                sede
        );
    }
}