package cl.duoc.reservaMS.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import cl.duoc.reservaMS.dto.HorarioBloqueDTO;

@FeignClient(name = "horarioMS", url = "http://localhost:8084")
public interface HorarioClient {

    @GetMapping("/api/v1/horarios/dto/{id}")
    HorarioBloqueDTO obtenerHorario(@PathVariable Integer id);

    @PutMapping("/api/v1/horarios/no-disponible/{id}")
    void marcarNoDisponible(@PathVariable Integer id);
}