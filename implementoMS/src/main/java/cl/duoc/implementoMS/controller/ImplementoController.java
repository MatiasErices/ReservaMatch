package cl.duoc.implementoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.implementoMS.dto.ImplementoDTO;
import cl.duoc.implementoMS.model.Implemento;
import cl.duoc.implementoMS.service.ImplementoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/implementos")
public class ImplementoController {

    @Autowired
    private ImplementoService service;

    @GetMapping
    @Operation(summary = "Listar todos los implementos", description = "Obtiene una lista de todos los implementos disponibles en el sistema.")
    public ResponseEntity<List<Implemento>> listar() {
        List<Implemento> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar implementos disponibles", description = "Obtiene una lista de todos los implementos disponibles en el sistema.")
    public ResponseEntity<List<Implemento>> listarDisponibles() {
        List<Implemento> lista = service.listarDisponibles();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar implemento por ID", description = "Obtiene los detalles de un implemento específico por su identificador.")
    public ResponseEntity<Implemento> buscar(@PathVariable Integer id) {
        try {
            Implemento implemento = service.buscarPorId(id);
            return ResponseEntity.ok(implemento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener implemento como DTO", description = "Obtiene los detalles de un implemento específico en formato DTO.")
    public ResponseEntity<ImplementoDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            ImplementoDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Crear implemento", description = "Crea un nuevo implemento en el sistema.")
    public ResponseEntity<Implemento> guardar(@RequestBody Implemento implemento) {
        Implemento nuevo = service.guardar(implemento);
        return ResponseEntity.ok(nuevo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar implemento", description = "Actualiza los detalles de un implemento específico por su identificador.")
    public ResponseEntity<Implemento> actualizar(@PathVariable Integer id, @RequestBody Implemento implemento) {
        try {
            Implemento actualizado = service.actualizar(id, implemento);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/descontar-stock/{id}/{cantidad}")
    @Operation(summary = "Descontar stock de implemento", description = "Descuenta una cantidad específica del stock de un implemento.")
    public ResponseEntity<Implemento> descontarStock(@PathVariable Integer id, @PathVariable Integer cantidad) {
        try {
            Implemento implemento = service.descontarStock(id, cantidad);
            return ResponseEntity.ok(implemento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/devolver-stock/{id}/{cantidad}")
    @Operation(summary = "Devolver stock de implemento", description = "Devuelve una cantidad específica del stock de un implemento.")
    public ResponseEntity<Implemento> devolverStock(@PathVariable Integer id, @PathVariable Integer cantidad) {
        try {
            Implemento implemento = service.devolverStock(id, cantidad);
            return ResponseEntity.ok(implemento);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar implemento", description = "Elimina un implemento específico por su identificador.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}