package cl.duoc.reservaMS.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la reserva", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Identificador del usuario que realiza la reserva", example = "1")
    private Integer usuarioId;

    @Column(nullable = false)
    @Schema(description = "Identificador de la cancha", example = "1")
    private Integer canchaId;

    @Column(nullable = false)
    @Schema(description = "Identificador de la sede", example = "1")
    private Integer sedeId;

    @Column(nullable = false)
    @Schema(description = "Identificador del bloque horario", example = "1")
    private Integer horaBloqueId;

    @Column(nullable = false)
    @Schema(description = "Fecha de la reserva", example = "2023-10-10")
    private Date fechaReserva;

    @Column(nullable = false)
    @Schema(description = "Estado de la reserva", example = "Activa")
    private String estado;

    @Column(nullable = false)
    @Schema(description = "Monto total de la reserva", example = "10000.0")
    private Double montoTotal;
}