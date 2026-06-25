package cl.duoc.arriendoImplementoMS.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "arriendo_implemento")
public class ArriendoImplemento {

    @Id
    @Schema(name = "arriendo_implemento_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Schema(name = "reserva_id")
    private Integer reservaId;

    @Column(nullable = false)
    @Schema(name = "implemento_id")
    private Integer implementoId;

    @Column(nullable = false)
    @Schema(name = "cantidad")
    private Integer cantidad;

    @Column(nullable = false)
    @Schema(name = "fecha_arriendo")
    private Date fechaArriendo;

    @Column(nullable = false)
    @Schema(name = "monto_total")
    private Double montoTotal;
}