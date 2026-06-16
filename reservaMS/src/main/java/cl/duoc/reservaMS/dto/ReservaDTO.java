package cl.duoc.reservaMS.dto;

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

    private UsuarioDTO usuario;
    private CanchaDTO cancha;
    private HorarioBloqueDTO horarioBloque;
}