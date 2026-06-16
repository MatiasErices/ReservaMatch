package cl.duoc.horarioMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.horarioMS.dto.HorarioBloqueDTO;
import cl.duoc.horarioMS.model.HorarioBloque;
import cl.duoc.horarioMS.service.HorarioBloqueService;

@RestController
@RequestMapping("/api/v1/horarios")
public class HorarioBloqueController {

    @Autowired
    private HorarioBloqueService service;

    @GetMapping
    public ResponseEntity<List<HorarioBloque>> listar() {
        List<HorarioBloque> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponibles")
    public ResponseEntity<List<HorarioBloque>> listarDisponibles() {
        List<HorarioBloque> lista = service.listarDisponibles();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioBloque> buscar(@PathVariable Integer id) {
        try {
            HorarioBloque bloque = service.buscarPorId(id);
            return ResponseEntity.ok(bloque);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<HorarioBloqueDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            HorarioBloqueDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<HorarioBloque> guardar(@RequestBody HorarioBloque bloque) {
        HorarioBloque nuevo = service.guardar(bloque);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioBloque> actualizar(@PathVariable Integer id, @RequestBody HorarioBloque bloque) {
        try {
            HorarioBloque actualizado = service.actualizar(id, bloque);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/no-disponible/{id}")
    public ResponseEntity<HorarioBloque> marcarNoDisponible(@PathVariable Integer id) {
        try {
            HorarioBloque bloque = service.marcarNoDisponible(id);
            return ResponseEntity.ok(bloque);
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
