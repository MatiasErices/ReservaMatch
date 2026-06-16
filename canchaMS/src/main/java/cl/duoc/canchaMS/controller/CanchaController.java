package cl.duoc.canchaMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.canchaMS.dto.CanchaDTO;
import cl.duoc.canchaMS.model.Cancha;
import cl.duoc.canchaMS.service.CanchaService;

@RestController
@RequestMapping("/api/v1/canchas")
public class CanchaController {

    @Autowired
    private CanchaService service;

    @GetMapping
    public ResponseEntity<List<Cancha>> listar() {
        List<Cancha> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<Cancha>> listarDisponibles() {
        List<Cancha> lista = service.listarDisponibles();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sede/{sedeId}")
    public ResponseEntity<List<Cancha>> listarPorSede(@PathVariable Integer sedeId) {
        List<Cancha> lista = service.listarPorSede(sedeId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sede/{sedeId}/disponibles")
    public ResponseEntity<List<Cancha>> listarPorSedeDisponibles(@PathVariable Integer sedeId) {
        List<Cancha> lista = service.listarPorSedeDisponibles(sedeId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancha> buscar(@PathVariable Integer id) {
        try {
            Cancha cancha = service.buscarPorId(id);
            return ResponseEntity.ok(cancha);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<CanchaDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            CanchaDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cancha> guardar(@RequestBody Cancha cancha) {
        Cancha nueva = service.guardar(cancha);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cancha> actualizar(@PathVariable Integer id, @RequestBody Cancha cancha) {
        try {
            Cancha actualizada = service.actualizar(id, cancha);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/no-disponible/{id}")
    public ResponseEntity<Cancha> marcarNoDisponible(@PathVariable Integer id) {
        try {
            Cancha cancha = service.marcarNoDisponible(id);
            return ResponseEntity.ok(cancha);
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