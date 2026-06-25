package cl.duoc.canchaMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.canchaMS.dto.CanchaDTO;
import cl.duoc.canchaMS.model.Cancha;
import cl.duoc.canchaMS.service.CanchaService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/canchas")
public class CanchaController {

    @Autowired
    private CanchaService service;

    @GetMapping
    @Operation(summary = "Listar todas las canchas", description = "Obtiene una lista de todas las canchas disponibles en el sistema.")
    public ResponseEntity<List<Cancha>> listar() {
        List<Cancha> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar canchas disponibles", description = "Obtiene una lista de todas las canchas que están disponibles para reserva.")
    public ResponseEntity<List<Cancha>> listarDisponibles() {
        List<Cancha> lista = service.listarDisponibles();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sede/{sedeId}")
    @Operation(summary = "Listar canchas por sede", description = "Obtiene una lista de todas las canchas asociadas a una sede específica.")
    public ResponseEntity<List<Cancha>> listarPorSede(@PathVariable Integer sedeId) {
        List<Cancha> lista = service.listarPorSede(sedeId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/sede/{sedeId}/disponibles")
    @Operation(summary = "Listar canchas disponibles por sede", description = "Obtiene una lista de todas las canchas disponibles asociadas a una sede específica.")
    public ResponseEntity<List<Cancha>> listarPorSedeDisponibles(@PathVariable Integer sedeId) {
        List<Cancha> lista = service.listarPorSedeDisponibles(sedeId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cancha por ID", description = "Obtiene los detalles de una cancha específica por su ID.")
    public ResponseEntity<Cancha> buscar(@PathVariable Integer id) {
        try {
            Cancha cancha = service.buscarPorId(id);
            return ResponseEntity.ok(cancha);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener DTO de cancha", description = "Obtiene el DTO de una cancha específica por su ID.")
    public ResponseEntity<CanchaDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            CanchaDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear nueva cancha", description = "Crea una nueva cancha en el sistema.")
    public ResponseEntity<Cancha> guardar(@RequestBody Cancha cancha) {
        Cancha nueva = service.guardar(cancha);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cancha", description = "Actualiza los datos de una cancha específica por su ID.")
    public ResponseEntity<Cancha> actualizar(@PathVariable Integer id, @RequestBody Cancha cancha) {
        try {
            Cancha actualizada = service.actualizar(id, cancha);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/no-disponible/{id}")
    @Operation(summary = "Marcar cancha como no disponible", description = "Marca una cancha específica como no disponible.")
    public ResponseEntity<Cancha> marcarNoDisponible(@PathVariable Integer id) {
        try {
            Cancha cancha = service.marcarNoDisponible(id);
            return ResponseEntity.ok(cancha);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cancha", description = "Elimina una cancha específica por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}