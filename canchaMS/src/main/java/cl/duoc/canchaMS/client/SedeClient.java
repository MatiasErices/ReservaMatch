package cl.duoc.canchaMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.canchaMS.dto.SedeDTO;

@FeignClient(name = "sedeMS", url = "http://localhost:8082")
public interface SedeClient {

    @GetMapping("/api/v1/sedes/dto/{id}")
    SedeDTO obtenerSede(@PathVariable Integer id);
}