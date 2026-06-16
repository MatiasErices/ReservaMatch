package cl.duoc.usuarioMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.usuarioMS.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {

}