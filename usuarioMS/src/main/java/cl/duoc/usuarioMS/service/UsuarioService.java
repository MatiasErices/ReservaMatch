package cl.duoc.usuarioMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.usuarioMS.dto.UsuarioDTO;
import cl.duoc.usuarioMS.model.Usuario;
import cl.duoc.usuarioMS.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        return repository.save(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario actualizar(Integer id, Usuario usuarioNuevo) {
        Usuario usuarioExistente = buscarPorId(id);

        usuarioExistente.setNombre(usuarioNuevo.getNombre());
        usuarioExistente.setEmail(usuarioNuevo.getEmail());
        usuarioExistente.setPassword(usuarioNuevo.getPassword());
        usuarioExistente.setRut(usuarioNuevo.getRut());
        usuarioExistente.setTelefono(usuarioNuevo.getTelefono());
        usuarioExistente.setRol(usuarioNuevo.getRol());

        return repository.save(usuarioExistente);

    
    }

    public void eliminar(Integer id) {
        buscarPorId(id);
        repository.deleteById(id);
    }

    public UsuarioDTO obtenerDTO(Integer id) {
        Usuario usuario = buscarPorId(id);

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getRut(),
                usuario.getTelefono(),
                usuario.getRol().getNombre()
        );
    }
}