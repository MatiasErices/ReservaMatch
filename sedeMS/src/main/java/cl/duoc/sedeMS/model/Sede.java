package cl.duoc.sedeMS.model;

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
@Table(name = "sede")
@Schema(description = "Representa una Sede dentro del sistema")

public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador unico de la Sede", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre de la Sede", example = "Sede Central")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Direccion de la Sede", example = "Calle Principal 123")
    private String direccion;

    @Column(nullable = false)
    @Schema(description = "Comuna donde se encuentra la Sede", example = "Santiago")
    private String comuna;

    @Column(nullable = false)
    @Schema(description = "Numero de telefono de la Sede", example = "+56 2 12345678")
    private String telefono;
}


