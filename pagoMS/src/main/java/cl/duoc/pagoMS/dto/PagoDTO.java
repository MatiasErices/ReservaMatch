package cl.duoc.pagoMS.dto;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Integer id;
    private Double monto;
    private Date fechaPago;
    private String estado;
    private String tipoPago;
    private ReservaDTO reserva;
}
