package cl.duoc.canchaMS.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.canchaMS.model.TipoCancha;

@Repository
public interface TipoCanchaRepository extends JpaRepository<TipoCancha, Integer> {


}
