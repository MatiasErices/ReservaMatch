package cl.duoc.pagoMS.dto;

import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {

    private Integer id;
    private Date fechaReserva;
    private String estado;
    private Double montoTotal;
    private Integer usuarioId;
    private Integer canchaId;
    private Integer sedeId;
    private Integer horaBloqueId;
}