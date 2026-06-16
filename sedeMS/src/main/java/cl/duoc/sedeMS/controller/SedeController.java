package cl.duoc.sedeMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.duoc.sedeMS.dto.SedeDTO;
import cl.duoc.sedeMS.model.Sede;
import cl.duoc.sedeMS.service.SedeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/sedes")
@Tag(name = "Sedes", description = "Operacion sobre las Sedes del sistema")
public class SedeController {

    @Autowired
    private SedeService service;

    @GetMapping
    @Operation(summary = "Lista todas las Sedes",
            description = "Devuelve una lista de todas las Sedes registradas en el sistema")
    public ResponseEntity<List<Sede>> listar() {
        List<Sede> lista = service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Busca una Sede por ID",
                description = "Busca una Sede por ID y devuelve su informacion completa")
    @ApiResponses(value ={@ApiResponse(responseCode = "200", description = "Sede encontrada"),
                        @ApiResponse(responseCode = "404", description = "Sede no encontrada"),
                        @ApiResponse(responseCode = "500", description = "Error interno del servidor")

                        }
                )
    public ResponseEntity<Sede> buscar(@PathVariable Integer id) {
        try {
            Sede sede = service.buscarPorId(id);
            return ResponseEntity.ok(sede);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/dto/{id}")
    @Operation(summary = "Busca una Sede por Id y devuelve un DTO",
            description = "Busca una Sede por ID y devuelve un DTO con informacion resumida")
    public ResponseEntity<SedeDTO> obtenerDTO(@PathVariable Integer id) {
        try {
            SedeDTO dto = service.obtenerDTO(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/comuna/{comuna}")
    @Operation(summary = "Busca una Sede por Comuna",
            description = "Busca una Sede por Comuna y devuelve su informacion completa")
    public ResponseEntity<List<Sede>> listarPorComuna(@PathVariable String comuna) {
        List<Sede> lista = service.listarPorComuna(comuna);

        if (lista.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(lista);
    }

    @PostMapping
    @Operation(summary = "Crea una nueva Sede",
            description = "Crea una nueva Sede con la informacion proporcionada en el cuerpo de la solicitud")
    public ResponseEntity<Sede> guardar(@RequestBody Sede sede) {
        Sede nueva = service.guardar(sede);
        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualiza una Sede existente",
            description = "Actualiza una Sede existente con la informacion proporcionada en el cuerpo de la solicitud")
    public ResponseEntity<Sede> actualizar(@PathVariable Integer id,@RequestBody Sede sede) {
        try {
            Sede actualizada = service.actualizar(id, sede);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Elimina una Sede por ID",
            description = "Elimina una Sede por ID y devuelve una respuesta sin contenido si la eliminacion fue exitosa")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        try {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}