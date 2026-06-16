package cl.duoc.reservaMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cl.duoc.reservaMS.dto.CanchaDTO;

@FeignClient(name = "canchaMS", url = "http://localhost:8083")
public interface CanchaClient {

    @GetMapping("/api/v1/canchas/dto/{id}")
    CanchaDTO obtenerCancha(@PathVariable Integer id);

    @PutMapping("/api/v1/canchas/no-disponible/{id}")
    void marcarNoDisponible(@PathVariable Integer id);
}