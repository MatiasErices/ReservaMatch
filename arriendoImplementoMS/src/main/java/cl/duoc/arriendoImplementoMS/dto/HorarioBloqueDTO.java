package cl.duoc.arriendoImplementoMS.dto;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HorarioBloqueDTO {

    private Integer id;
    private Date fecha;
    private String horaInicio;
    private String horaFin;
    private Boolean disponible;
}