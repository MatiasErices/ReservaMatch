package cl.duoc.horarioMS.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "horario_bloque")
public class HorarioBloque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico del bloque de horario", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Fecha del bloque de horario", example = "2023-01-01")
    private Date fecha;

    @Column(nullable = false)
    @Schema(description = "Hora de inicio del bloque de horario", example = "09:00")
    private String horaInicio;

    @Column(nullable = false)
    @Schema(description = "Hora de fin del bloque de horario", example = "17:00")
    private String horaFin;

    @Column(nullable = false)
    @Schema(description = "Indica si el bloque de horario está disponible", example = "true")
    private Boolean disponible = true;
}