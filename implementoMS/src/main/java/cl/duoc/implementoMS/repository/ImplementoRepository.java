package cl.duoc.implementoMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.implementoMS.model.Implemento;

import java.util.List;

@Repository
public interface ImplementoRepository extends JpaRepository<Implemento, Integer> {

    List<Implemento> findByStockGreaterThan(Integer stock);
}