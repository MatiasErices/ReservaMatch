package cl.duoc.reservaMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.reservaMS.model.Reserva;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByUsuarioId(Integer usuarioId);

    List<Reserva> findByCanchaId(Integer canchaId);

    List<Reserva> findByEstado(String estado);

    List<Reserva> findByUsuarioIdAndEstado(Integer usuarioId, String estado);
}
