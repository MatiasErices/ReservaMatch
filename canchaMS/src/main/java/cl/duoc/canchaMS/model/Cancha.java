package cl.duoc.canchaMS.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cancha")
public class Cancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la cancha", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la cancha", example = "Cancha 1")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Capacidad de la cancha", example = "10")
    private Integer capacidad;

    @Column(nullable = false)
    @Schema(description = "Precio por hora de la cancha", example = "5000.0")
    private Double precioPorHora;

    @ManyToOne
    @Schema(description = "Tipo de cancha asociado a la cancha")
    @JoinColumn(name = "tipo_cancha_id", nullable = false)
    private TipoCancha tipoCancha;

    
    @Column(nullable = false)
    @Schema(description = "Identificador de la sede", example = "1")
    private Integer sedeId;

    @Column(nullable = false)
    @Schema(description = "Indica si la cancha está disponible", example = "true")
    private Boolean disponible = true;

}