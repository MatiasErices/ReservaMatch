package cl.duoc.horarioMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.horarioMS.model.HorarioBloque;

import java.util.List;

@Repository
public interface HorarioBloqueRepository extends JpaRepository<HorarioBloque, Integer> {

    List<HorarioBloque> findByDisponibleTrue();

}