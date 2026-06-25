package cl.duoc.reservaMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.reservaMS.dto.ReservaDTO;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.service.ReservaService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping
    @Operation(summary = "Listar todas las reservas", description = "Obtiene una lista de todas las reservas registradas en el sistema.")
    public ResponseEntity<List<Reserva>> listar() {
        List<Reserva> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar reserva por ID", description = "Obtiene los detalles de una reserva específica por su ID.")
    public ResponseEntity<Reserva> buscar(@PathVariable Integer id) {
        try {
            Reserva reserva = service.buscarPorId(id);
            return ResponseEntity.ok(reserva);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener reserva como DTO", description = "Obtiene los detalles de una reserva específica en formato DTO.")
    public ResponseEntity<ReservaDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            ReservaDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Listar reservas por usuario", description = "Obtiene una lista de reservas realizadas por un usuario específico.")
    public ResponseEntity<List<Reserva>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<Reserva> lista = service.listarPorUsuario(usuarioId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar reservas por estado", description = "Obtiene una lista de reservas según su estado.")
    public ResponseEntity<List<Reserva>> listarPorEstado(@PathVariable String estado) {
        List<Reserva> lista = service.listarPorEstado(estado);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crear nueva reserva", description = "Crea una nueva reserva en el sistema.")
    public ResponseEntity<Reserva> guardar(@RequestBody Reserva reserva) {
        try {
            Reserva nueva = service.guardar(reserva);
            return ResponseEntity.ok(nueva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/confirmar/{id}")
    @Operation(summary = "Confirmar reserva", description = "Confirma una reserva específica por su ID.")
    public ResponseEntity<Reserva> confirmar(@PathVariable Integer id) {
        try {
            Reserva confirmada = service.confirmar(id);
            return ResponseEntity.ok(confirmada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cancelar/{id}")
    @Operation(summary = "Cancelar reserva", description = "Cancela una reserva específica por su ID.")
    public ResponseEntity<Reserva> cancelar(@PathVariable Integer id) {
        try {
            Reserva cancelada = service.cancelar(id);
            return ResponseEntity.ok(cancelada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar reserva", description = "Elimina una reserva específica por su ID.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}