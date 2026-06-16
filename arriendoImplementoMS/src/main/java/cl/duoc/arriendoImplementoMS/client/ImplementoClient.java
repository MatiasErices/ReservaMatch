package cl.duoc.arriendoImplementoMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cl.duoc.arriendoImplementoMS.dto.ImplementoDTO;

@FeignClient(name = "implementoMS", url = "http://localhost:8087")
public interface ImplementoClient {

    @GetMapping("/api/v1/implementos/dto/{id}")
    ImplementoDTO obtenerImplemento(@PathVariable Integer id);

    @PutMapping("/api/v1/implementos/descontar-stock/{id}/{cantidad}")
    void descontarStock(@PathVariable Integer id, @PathVariable Integer cantidad);

    @PutMapping("/api/v1/implementos/devolver-stock/{id}/{cantidad}")
    void devolverStock(@PathVariable Integer id, @PathVariable Integer cantidad);
}