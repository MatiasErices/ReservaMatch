package cl.duoc.reservaMS.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer usuarioId;

    @Column(nullable = false)
    private Integer canchaId;

    @Column(nullable = false)
    private Integer sedeId;

    @Column(nullable = false)
    private Integer horaBloqueId;

    @Column(nullable = false)
    private Date fechaReserva;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private Double montoTotal;
}