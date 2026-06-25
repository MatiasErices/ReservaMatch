package cl.duoc.usuarioMS.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rol")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del rol", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del rol", example = "Administrador")
    private String nombre;


}
