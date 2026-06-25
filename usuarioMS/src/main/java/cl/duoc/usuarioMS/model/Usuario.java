package cl.duoc.usuarioMS.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del usuario", example = "1")
    private Integer id;

    @Column(nullable = false)
    @Schema(description = "Nombre del usuario", example = "Juan Pérez")
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico del usuario", example = "juan.perez@ejemplo.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Contraseña del usuario", example = "contraseña123")
    private String password;

    @Column(nullable = false)
    @Schema(description = "RUT del usuario", example = "12.345.678-9")
    private String rut;

    @Column(nullable = false)
    @Schema(description = "Teléfono del usuario", example = "987654321")
    private String telefono;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    @Schema(description = "Rol del usuario")
    private Rol rol;
}
