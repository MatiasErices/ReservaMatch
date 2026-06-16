package cl.duoc.implementoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.implementoMS.dto.ImplementoDTO;
import cl.duoc.implementoMS.model.Implemento;
import cl.duoc.implementoMS.service.ImplementoService;

@RestController
@RequestMapping("/api/v1/implementos")
public class ImplementoController {

    @Autowired
    private ImplementoService service;

    @GetMapping
    public ResponseEntity<List<Implemento>> listar() {
        List<Implemento> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Implemento>> listarDisponibles() {
        List<Implemento> lista = service.listarDisponibles();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Implemento> buscar(@PathVariable Integer id) {
        try {
            Implemento implemento = service.buscarPorId(id);
            return ResponseEntity.ok(implemento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ImplementoDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            ImplementoDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Implemento> guardar(@RequestBody Implemento implemento) {
        Implemento nuevo = service.guardar(implemento);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Implemento> actualizar(@PathVariable Integer id, @RequestBody Implemento implemento) {
        try {
            Implemento actualizado = service.actualizar(id, implemento);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/descontar-stock/{id}/{cantidad}")
    public ResponseEntity<Implemento> descontarStock(@PathVariable Integer id, @PathVariable Integer cantidad) {
        try {
            Implemento implemento = service.descontarStock(id, cantidad);
            return ResponseEntity.ok(implemento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/devolver-stock/{id}/{cantidad}")
    public ResponseEntity<Implemento> devolverStock(@PathVariable Integer id, @PathVariable Integer cantidad) {
        try {
            Implemento implemento = service.devolverStock(id, cantidad);
            return ResponseEntity.ok(implemento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}