package cl.duoc.arriendoImplementoMS.controller;

import java.util.List;

import org.hibernate.annotations.OptimisticLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.arriendoImplementoMS.dto.ArriendoImplementoDTO;
import cl.duoc.arriendoImplementoMS.model.ArriendoImplemento;
import cl.duoc.arriendoImplementoMS.service.ArriendoImplementoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/arriendos-implementos")
public class ArriendoImplementoController {

    @Autowired
    private ArriendoImplementoService service;

    @GetMapping
    @Operation(summary = "Listar todos los arriendos de implementos", description = "Obtiene una lista de todos los arriendos de implementos disponibles en el sistema.")
    public ResponseEntity<List<ArriendoImplemento>> listar() {
        List<ArriendoImplemento> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar arriendo de implemento por ID", description = "Obtiene un arriendo de implemento específico por su identificador.")
    public ResponseEntity<ArriendoImplemento> buscar(@PathVariable Integer id) {
        try {
            ArriendoImplemento arriendo = service.buscarPorId(id);
            return ResponseEntity.ok(arriendo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener DTO de arriendo de implemento", description = "Obtiene el DTO de un arriendo de implemento específico por su identificador.")
    public ResponseEntity<ArriendoImplementoDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            ArriendoImplementoDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Listar arriendos de implementos por reserva", description = "Obtiene una lista de arriendos de implementos asociados a una reserva específica.")  
    public ResponseEntity<List<ArriendoImplemento>> listarPorReserva(@PathVariable Integer reservaId) {
        List<ArriendoImplemento> lista = service.listarPorReserva(reservaId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/implemento/{implementoId}")
    @Operation(summary = "Listar arriendos de implementos por implemento", description = "Obtiene una lista de arriendos de implementos asociados a un implemento específico.")
    public ResponseEntity<List<ArriendoImplemento>> listarPorImplemento(@PathVariable Integer implementoId) {
        List<ArriendoImplemento> lista = service.listarPorImplemento(implementoId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crear arriendo de implemento", description = "Crea un nuevo arriendo de implemento en el sistema.")
    public ResponseEntity<ArriendoImplemento> crear(@RequestBody ArriendoImplemento arriendo) {
        try {
            ArriendoImplemento nuevo = service.crear(arriendo);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar arriendo de implemento", description = "Elimina un arriendo de implemento específico por su identificador.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}