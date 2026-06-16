package cl.duoc.arriendoImplementoMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SedeDTO {

    private Integer id;
    private String nombre;
    private String direccion;
    private String comuna;
    private String telefono;

}
