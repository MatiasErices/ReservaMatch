package cl.duoc.arriendoImplementoMS.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.arriendoImplementoMS.client.ImplementoClient;
import cl.duoc.arriendoImplementoMS.client.ReservaClient;
import cl.duoc.arriendoImplementoMS.dto.ArriendoImplementoDTO;
import cl.duoc.arriendoImplementoMS.dto.ImplementoDTO;
import cl.duoc.arriendoImplementoMS.dto.ReservaDTO;
import cl.duoc.arriendoImplementoMS.model.ArriendoImplemento;
import cl.duoc.arriendoImplementoMS.repository.ArriendoImplementoRepository;

@Service
public class ArriendoImplementoService {

    @Autowired
    private ArriendoImplementoRepository repository;

    @Autowired
    private ReservaClient reservaClient;

    @Autowired
    private ImplementoClient implementoClient;


    public List<ArriendoImplemento> listar() {
        return repository.findAll();
    }

    public ArriendoImplemento buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Arriendo no encontrado"));
    }

    public List<ArriendoImplemento> listarPorReserva(Integer reservaId) {
        return repository.findByReservaId(reservaId);
    }

    public List<ArriendoImplemento> listarPorImplemento(Integer implementoId) {
        return repository.findByImplementoId(implementoId);
    }

    public ArriendoImplemento crear(ArriendoImplemento arriendo) {

        ReservaDTO reserva = reservaClient.obtenerReserva(arriendo.getReservaId());
        if (reserva == null) {
            throw new RuntimeException("Reserva no encontrada");
        }

        if (!reserva.getEstado().equals("CONFIRMADA")) {
            throw new RuntimeException("La reserva debe estar CONFIRMADA para arrendar implementos");
        }

        ImplementoDTO implemento = implementoClient.obtenerImplemento(arriendo.getImplementoId());
        if (implemento == null) {
            throw new RuntimeException("Implemento no encontrado");
        }

        if (implemento.getStock() < arriendo.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }

        arriendo.setMontoTotal(implemento.getPrecioArriendo() * arriendo.getCantidad());

        arriendo.setFechaArriendo(new Date());

        ArriendoImplemento nuevoArriendo = repository.save(arriendo);

        implementoClient.descontarStock(arriendo.getImplementoId(), arriendo.getCantidad());

        return nuevoArriendo;
    }

    public void eliminar(Integer id) {
        ArriendoImplemento arriendo = buscarPorId(id);

        implementoClient.devolverStock(arriendo.getImplementoId(), arriendo.getCantidad());

        repository.deleteById(id);
    }

    public ArriendoImplementoDTO obtenerDTO(Integer id) {
        ArriendoImplemento arriendo = buscarPorId(id);

        ImplementoDTO implemento = implementoClient.obtenerImplemento(arriendo.getImplementoId());
        ReservaDTO reserva = reservaClient.obtenerReserva(arriendo.getReservaId());

        return new ArriendoImplementoDTO(
                arriendo.getId(),
                arriendo.getCantidad(),
                arriendo.getFechaArriendo(),
                arriendo.getMontoTotal(),
                implemento,
                reserva
        );
    }
}