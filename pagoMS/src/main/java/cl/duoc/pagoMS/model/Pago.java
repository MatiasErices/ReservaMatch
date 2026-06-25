package cl.duoc.pagoMS.model;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del pago", example = "1")
    private Integer id;
    
    @Column(nullable = false)
    @Schema(description = "Identificador de la reserva", example = "1")
    private Integer reservaId;

    @Column(nullable = false)
    @Schema(description = "Monto del pago", example = "10000.0")
    private Double monto;

    @Column(nullable = false)
    @Schema(description = "Fecha del pago", example = "2023-10-10")
    private Date fechaPago;

    @Column(nullable = false)
    @Schema(description = "Estado del pago", example = "pendiente")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", nullable = false)
    @Schema(description = "Tipo de pago asociado al pago")
    private TipoPago tipoPago;

}
