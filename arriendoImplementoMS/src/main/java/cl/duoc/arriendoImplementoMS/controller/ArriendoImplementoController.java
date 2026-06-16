package cl.duoc.arriendoImplementoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.arriendoImplementoMS.dto.ArriendoImplementoDTO;
import cl.duoc.arriendoImplementoMS.model.ArriendoImplemento;
import cl.duoc.arriendoImplementoMS.service.ArriendoImplementoService;

@RestController
@RequestMapping("/api/v1/arriendos-implementos")
public class ArriendoImplementoController {

    @Autowired
    private ArriendoImplementoService service;

    @GetMapping
    public ResponseEntity<List<ArriendoImplemento>> listar() {
        List<ArriendoImplemento> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArriendoImplemento> buscar(@PathVariable Integer id) {
        try {
            ArriendoImplemento arriendo = service.buscarPorId(id);
            return ResponseEntity.ok(arriendo);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    public ResponseEntity<ArriendoImplementoDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            ArriendoImplementoDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reserva/{reservaId}")
    public ResponseEntity<List<ArriendoImplemento>> listarPorReserva(@PathVariable Integer reservaId) {
        List<ArriendoImplemento> lista = service.listarPorReserva(reservaId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/implemento/{implementoId}")
    public ResponseEntity<List<ArriendoImplemento>> listarPorImplemento(@PathVariable Integer implementoId) {
        List<ArriendoImplemento> lista = service.listarPorImplemento(implementoId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    public ResponseEntity<ArriendoImplemento> crear(@RequestBody ArriendoImplemento arriendo) {
        try {
            ArriendoImplemento nuevo = service.crear(arriendo);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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