package cl.duoc.sedeMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.sedeMS.dto.SedeDTO;
import cl.duoc.sedeMS.model.Sede;
import cl.duoc.sedeMS.repository.SedeRepository;

@Service
public class SedeService {

    @Autowired
    private SedeRepository repository;

    public List<Sede> listar() {
        return repository.findAll();
    }

    public Sede guardar(Sede sede) {
        return repository.save(sede);
    }

    public Sede buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sede no encontrada"));
    }

    public Sede actualizar(Integer id, Sede sedeNueva) {
        Sede sedeExistente = buscarPorId(id);

        sedeExistente.setNombre(sedeNueva.getNombre());
        sedeExistente.setDireccion(sedeNueva.getDireccion());
        sedeExistente.setComuna(sedeNueva.getComuna());
        sedeExistente.setTelefono(sedeNueva.getTelefono());

        return repository.save(sedeExistente);
    }


    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public SedeDTO obtenerDTO(Integer id) {
        Sede sede = buscarPorId(id);

        return new SedeDTO(
                sede.getId(),
                sede.getNombre(),
                sede.getDireccion(),
                sede.getComuna(),
                sede.getTelefono()
        );
    }

    public List<Sede> listarPorComuna(String comuna) {
        return repository.findByComuna(comuna);
    }
}