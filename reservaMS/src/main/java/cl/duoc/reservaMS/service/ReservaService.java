package cl.duoc.reservaMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.reservaMS.client.CanchaClient;
import cl.duoc.reservaMS.client.HorarioClient;
import cl.duoc.reservaMS.client.UsuarioClient;
import cl.duoc.reservaMS.dto.CanchaDTO;
import cl.duoc.reservaMS.dto.HorarioBloqueDTO;
import cl.duoc.reservaMS.dto.ReservaDTO;
import cl.duoc.reservaMS.dto.UsuarioDTO;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private CanchaClient canchaClient;

    @Autowired
    private HorarioClient horarioClient;


    public List<Reserva> listar() {
        return repository.findAll();
    }

    public Reserva buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));
    }

    public List<Reserva> listarPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> listarPorEstado(String estado) {
        return repository.findByEstado(estado);
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }



    public Reserva guardar(Reserva reserva) {

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(reserva.getUsuarioId());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        CanchaDTO cancha = canchaClient.obtenerCancha(reserva.getCanchaId());
        if (cancha == null || !cancha.getDisponible()) {
            throw new RuntimeException("Cancha no disponible");
        }

        HorarioBloqueDTO horario = horarioClient.obtenerHorario(reserva.getHoraBloqueId());
        if (horario == null || !horario.getDisponible()) {
            throw new RuntimeException("Horario no disponible");
        }


        reserva.setSedeId(cancha.getSede().getId());

        reserva.setMontoTotal(cancha.getPrecioPorHora());

        reserva.setEstado("PENDIENTE");

        return repository.save(reserva);
    }

    public Reserva confirmar(Integer id) {
        Reserva reserva = buscarPorId(id);

        canchaClient.marcarNoDisponible(reserva.getCanchaId());
        horarioClient.marcarNoDisponible(reserva.getHoraBloqueId());

        reserva.setEstado("CONFIRMADA");
        return repository.save(reserva);
    }

    public Reserva cancelar(Integer id) {
        Reserva reserva = buscarPorId(id);
        reserva.setEstado("CANCELADA");
        return repository.save(reserva);
    }

    public ReservaDTO obtenerDTO(Integer id) {
        Reserva reserva = buscarPorId(id);

        UsuarioDTO usuario = usuarioClient.obtenerUsuario(reserva.getUsuarioId());
        CanchaDTO cancha = canchaClient.obtenerCancha(reserva.getCanchaId());
        HorarioBloqueDTO horario = horarioClient.obtenerHorario(reserva.getHoraBloqueId());

        return new ReservaDTO(
                reserva.getId(),
                reserva.getFechaReserva(),
                reserva.getEstado(),
                reserva.getMontoTotal(),
                reserva.getSedeId(),
                usuario,
                cancha,
                horario
        );
    }
}