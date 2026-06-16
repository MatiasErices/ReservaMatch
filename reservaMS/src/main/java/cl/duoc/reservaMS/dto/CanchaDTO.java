package cl.duoc.reservaMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CanchaDTO {

    private Integer id;
    private String nombre;
    private Integer capacidad;
    private Double precioPorHora;
    private Boolean disponible;
    private String tipoCancha;
    private SedeDTO sede;
}