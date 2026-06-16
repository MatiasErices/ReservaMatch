package cl.duoc.implementoMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.implementoMS.dto.ImplementoDTO;
import cl.duoc.implementoMS.model.Implemento;
import cl.duoc.implementoMS.repository.ImplementoRepository;

@Service
public class ImplementoService {

    @Autowired
    private ImplementoRepository implementoRepository;


    public List<Implemento> listar() {
        return implementoRepository.findAll();
    }

    public List<Implemento> listarDisponibles() {
        return implementoRepository.findByStockGreaterThan(0);
    }

    public Implemento buscarPorId(Integer id) {
        return implementoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Implemento no encontrado"));
    }

    public Implemento guardar(Implemento implemento) {
        return implementoRepository.save(implemento);
    }

    public Implemento actualizar(Integer id, Implemento implementoNuevo) {
        Implemento implementoExistente = buscarPorId(id);

        implementoExistente.setNombre(implementoNuevo.getNombre());
        implementoExistente.setDescripcion(implementoNuevo.getDescripcion());
        implementoExistente.setStock(implementoNuevo.getStock());
        implementoExistente.setPrecioArriendo(implementoNuevo.getPrecioArriendo());

        return implementoRepository.save(implementoExistente);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        implementoRepository.deleteById(id);
    }

    public ImplementoDTO obtenerDTO(Integer id) {
        Implemento implemento = buscarPorId(id);

        return new ImplementoDTO(
                implemento.getId(),
                implemento.getNombre(),
                implemento.getDescripcion(),
                implemento.getStock(),
                implemento.getPrecioArriendo()
        );
    }

    public Implemento descontarStock(Integer id, Integer cantidad) {
        Implemento implemento = buscarPorId(id);

        if (implemento.getStock() < cantidad) {
            throw new RuntimeException("Stock insuficiente");
        }

        implemento.setStock(implemento.getStock() - cantidad);
        return implementoRepository.save(implemento);
    }

    public Implemento devolverStock(Integer id, Integer cantidad) {
        Implemento implemento = buscarPorId(id);
        implemento.setStock(implemento.getStock() + cantidad);
        return implementoRepository.save(implemento);
    }
}