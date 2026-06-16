package cl.duoc.canchaMS.model;

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
    private Integer id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Double precioPorHora;

        @ManyToOne
    @JoinColumn(name = "tipo_cancha_id", nullable = false)
    private TipoCancha tipoCancha;

    
    @Column(nullable = false)
    private Integer sedeId;

    @Column(nullable = false)
    private Boolean disponible = true;

}