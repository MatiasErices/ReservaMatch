package cl.duoc.arriendoImplementoMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.arriendoImplementoMS.dto.ReservaDTO;

@FeignClient(name = "reservaMS", url = "http://localhost:8085")
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/dto/{id}")
    ReservaDTO obtenerReserva(@PathVariable Integer id);
}