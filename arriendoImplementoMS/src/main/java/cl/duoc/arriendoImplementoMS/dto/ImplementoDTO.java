package cl.duoc.arriendoImplementoMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImplementoDTO {

    private Integer id;
    private String nombre;
    private String descripcion;
    private Integer stock;
    private Double precioArriendo;
}