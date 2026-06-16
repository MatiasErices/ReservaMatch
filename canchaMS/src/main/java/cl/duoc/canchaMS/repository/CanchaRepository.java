package cl.duoc.canchaMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.canchaMS.model.Cancha;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Integer>    {

    List<Cancha> findBySedeId(Integer sedeId);

    List<Cancha> findByDisponibleTrue();

    List<Cancha> findBySedeIdAndDisponibleTrue(Integer sedeId);


}
