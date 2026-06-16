package cl.duoc.horarioMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.horarioMS.dto.HorarioBloqueDTO;
import cl.duoc.horarioMS.model.HorarioBloque;
import cl.duoc.horarioMS.repository.HorarioBloqueRepository;

@Service
public class HorarioBloqueService {

    @Autowired
    private HorarioBloqueRepository repository;

    public List<HorarioBloque> listar() {
        return repository.findAll();
    }

    public HorarioBloque guardar(HorarioBloque bloque) {
        return repository.save(bloque);
    }

    public HorarioBloque buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado"));
    }

    public HorarioBloque actualizar(Integer id, HorarioBloque bloqueNuevo) {
        HorarioBloque bloqueExistente = buscarPorId(id);

        bloqueExistente.setFecha(bloqueNuevo.getFecha());
        bloqueExistente.setHoraInicio(bloqueNuevo.getHoraInicio());
        bloqueExistente.setHoraFin(bloqueNuevo.getHoraFin());
        bloqueExistente.setDisponible(bloqueNuevo.getDisponible());

        return repository.save(bloqueExistente);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public HorarioBloqueDTO obtenerDTO(Integer id) {
        HorarioBloque bloque = buscarPorId(id);

        return new HorarioBloqueDTO(
                bloque.getId(),
                bloque.getFecha(),
                bloque.getHoraInicio(),
                bloque.getHoraFin(),
                bloque.getDisponible()
        );
    }

    public List<HorarioBloque> listarDisponibles() {
        return repository.findByDisponibleTrue();
    }

    public HorarioBloque marcarNoDisponible(Integer id) {
        HorarioBloque bloque = buscarPorId(id);
        bloque.setDisponible(false);
        return repository.save(bloque);
    }
}