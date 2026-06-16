package cl.duoc.arriendoImplementoMS.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "arriendo_implemento")
public class ArriendoImplemento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer reservaId;

    @Column(nullable = false)
    private Integer implementoId;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Date fechaArriendo;

    @Column(nullable = false)
    private Double montoTotal;
}