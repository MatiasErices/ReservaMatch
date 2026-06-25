package cl.duoc.canchaMS.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_cancha")    
public class TipoCancha {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del tipo de cancha", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del tipo de cancha", example = "Fútbol 5")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Descripción del tipo de cancha", example = "Cancha de fútbol 5")
    private String descripcion;
}
