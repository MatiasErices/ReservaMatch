package cl.duoc.pagoMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.pagoMS.dto.PagoDTO;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    @Operation(summary = "Listar todos los pagos", description = "Obtiene una lista de todos los pagos registrados en el sistema.")
    public ResponseEntity<List<Pago>> listar() {
        List<Pago> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pago por ID", description = "Obtiene un pago específico por su identificador único.")
    public ResponseEntity<Pago> buscar(@PathVariable Integer id) {
        try {
            Pago pago = service.buscarPorId(id);
            return ResponseEntity.ok(pago);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Obtener pago como DTO", description = "Obtiene un pago específico en formato DTO por su identificador único.")
    public ResponseEntity<PagoDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            PagoDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/reserva/{reservaId}")
    @Operation(summary = "Listar pagos por reserva", description = "Obtiene una lista de pagos asociados a una reserva específica.")
    public ResponseEntity<List<Pago>> listarPorReserva(@PathVariable Integer reservaId) {
        List<Pago> lista = service.listarPorReserva(reservaId);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar pagos por estado", description = "Obtiene una lista de pagos según su estado.")
    public ResponseEntity<List<Pago>> listarPorEstado(@PathVariable String estado) {
        List<Pago> lista = service.listarPorEstado(estado);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Procesar pago", description = "Crea un nuevo pago en el sistema.")
    public ResponseEntity<Pago> procesar(@RequestBody Pago pago) {
        try {
            Pago procesado = service.procesar(pago);
            return ResponseEntity.ok(procesado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pago", description = "Elimina un pago específico por su identificador único.")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}