package cl.duoc.sedeMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.sedeMS.model.Sede;

@Repository
public interface SedeRepository extends JpaRepository<Sede, Integer> {

    List<Sede> findByComuna(String comuna);
}
