package cl.duoc.implementoMS.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "implemento")
public class Implemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del implemento", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del implemento", example = "Balón de fútbol")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Descripción del implemento", example = "Balón de fútbol oficial")
    private String descripcion;

    @Column(nullable = false)
    @Schema(description = "Stock disponible del implemento", example = "10")
    private Integer stock;

    @Column(nullable = false)
    @Schema(description = "Precio de arriendo del implemento", example = "5000.0")
    private Double precioArriendo;
}