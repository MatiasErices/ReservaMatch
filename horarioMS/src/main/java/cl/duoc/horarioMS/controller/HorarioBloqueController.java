package cl.duoc.horarioMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.horarioMS.dto.HorarioBloqueDTO;
import cl.duoc.horarioMS.model.HorarioBloque;
import cl.duoc.horarioMS.service.HorarioBloqueService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/horarios")

public class HorarioBloqueController {

    @Autowired
    private HorarioBloqueService service;

    @GetMapping
    @Operation(summary = "Listar todos los bloques de horario", description = "Obtiene una lista de todos los bloques de horario disponibles en el sistema.")
    public ResponseEntity<List<HorarioBloque>> listar() {
        List<HorarioBloque> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar bloques de horario disponibles", description = "Obtiene una lista de todos los bloques de horario que están disponibles en el sistema.")
    public ResponseEntity<List<HorarioBloque>> listarDisponibles() {
        List<HorarioBloque> lista = service.listarDisponibles();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bloque de horario por ID", description = "Obtiene un bloque de horario específico por su identificador.")
    public ResponseEntity<HorarioBloque> buscar(@PathVariable Integer id) {
        try {
            HorarioBloque bloque = service.buscarPorId(id);
            return ResponseEntity.ok(bloque);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener DTO de bloque de horario", description = "Obtiene el DTO de un bloque de horario específico por su identificador.")
    public ResponseEntity<HorarioBloqueDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            HorarioBloqueDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Guardar bloque de horario", description = "Crea un nuevo bloque de horario en el sistema.")
    public ResponseEntity<HorarioBloque> guardar(@RequestBody HorarioBloque bloque) {
        HorarioBloque nuevo = service.guardar(bloque);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar bloque de horario", description = "Actualiza un bloque de horario específico por su identificador.")
    public ResponseEntity<HorarioBloque> actualizar(@PathVariable Integer id, @RequestBody HorarioBloque bloque) {
        try {
            HorarioBloque actualizado = service.actualizar(id, bloque);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/no-disponible/{id}")
    @Operation(summary = "Marcar bloque de horario como no disponible", description = "Marca un bloque de horario específico como no disponible por su identificador.")
    public ResponseEntity<HorarioBloque> marcarNoDisponible(@PathVariable Integer id) {
        try {
            HorarioBloque bloque = service.marcarNoDisponible(id);
            return ResponseEntity.ok(bloque);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar bloque de horario", description = "Elimina un bloque de horario específico por su identificador.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
