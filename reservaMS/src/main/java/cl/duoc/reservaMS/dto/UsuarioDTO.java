package cl.duoc.reservaMS.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Integer id;
    private String nombre;
    private String email;
    private String rut;
    private String telefono;
    private String rolNombre;
}