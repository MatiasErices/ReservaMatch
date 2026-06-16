package cl.duoc.pagoMS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.pagoMS.client.ReservaClient;
import cl.duoc.pagoMS.dto.PagoDTO;
import cl.duoc.pagoMS.dto.ReservaDTO;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.repository.PagoRepository;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    @Autowired
    private ReservaClient reservaClient;


    public List<Pago> listar() {
        return repository.findAll();
    }

    public Pago buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public List<Pago> listarPorReserva(Integer reservaId) {
        return repository.findByReservaId(reservaId);
    }

    public List<Pago> listarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public Pago procesar(Pago pago) {

        ReservaDTO reserva = reservaClient.obtenerReserva(pago.getReservaId());
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }

        if (!reserva.getEstado().equals("PENDIENTE")) {
            throw new RuntimeException("La reserva no está en estado PENDIENTE");
        }

        pago.setMonto(reserva.getMontoTotal());

        pago.setFechaPago(new Date());

        pago.setEstado("COMPLETADO");

        Pago pagoProcesado = repository.save(pago);

        reservaClient.confirmarReserva(pago.getReservaId());

        return pagoProcesado;
    }

        public PagoDTO obtenerDTO(Integer id) {
        Pago pago = buscarPorId(id);

        ReservaDTO reserva = reservaClient.obtenerReserva(pago.getReservaId());

        return new PagoDTO(
                pago.getId(),
                pago.getMonto(),
                pago.getFechaPago(),
                pago.getEstado(),
                pago.getTipoPago().getNombre(),
                reserva
        );
    }
}