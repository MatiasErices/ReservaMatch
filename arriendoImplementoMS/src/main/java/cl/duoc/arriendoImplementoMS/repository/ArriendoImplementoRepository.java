package cl.duoc.arriendoImplementoMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.arriendoImplementoMS.model.ArriendoImplemento;

import java.util.List;

@Repository
public interface ArriendoImplementoRepository extends JpaRepository<ArriendoImplemento, Integer> {

    List<ArriendoImplemento> findByReservaId(Integer reservaId);

    List<ArriendoImplemento> findByImplementoId(Integer implementoId);
}