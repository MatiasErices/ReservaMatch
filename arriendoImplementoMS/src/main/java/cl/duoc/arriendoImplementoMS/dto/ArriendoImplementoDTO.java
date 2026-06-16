package cl.duoc.arriendoImplementoMS.dto;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArriendoImplementoDTO {

    private Integer id;
    private Integer cantidad;
    private Date fechaArriendo;
    private Double montoTotal;
    private ImplementoDTO implemento;
    private ReservaDTO reserva;
}