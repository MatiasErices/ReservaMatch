package cl.duoc.pagoMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cl.duoc.pagoMS.dto.ReservaDTO;

@FeignClient(name = "reservaMS", url = "http://localhost:8085")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}")
    ReservaDTO obtenerReserva(@PathVariable Integer id);

    @PutMapping("/api/v1/reservas/confirmar/{id}")
    void confirmarReserva(@PathVariable Integer id);
}