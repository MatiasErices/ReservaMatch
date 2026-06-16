package cl.duoc.usuarioMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.usuarioMS.model.Rol;
import cl.duoc.usuarioMS.model.Usuario;
import cl.duoc.usuarioMS.repository.RolRepository;
import cl.duoc.usuarioMS.repository.UsuarioRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void run(String... args) throws Exception {

        if (rolRepository.count() == 0) {

            Rol admin = rolRepository.save(new Rol(null, "ADMIN"));
            Rol cliente = rolRepository.save(new Rol(null, "CLIENTE"));

            usuarioRepository.save(new Usuario(null, "Juan Pérez", "juan@mail.com",
                    "1234", "12345678-9", "912345678", cliente));

            usuarioRepository.save(new Usuario(null, "María González", "maria@mail.com",
                    "1234", "98765432-1", "987654321", cliente));

            usuarioRepository.save(new Usuario(null, "Admin Sistema", "admin@mail.com",
                    "admin1234", "11111111-1", "911111111", admin));

            System.out.println("✅ UsuarioMS — datos cargados correctamente");
        } else {
            System.out.println("ℹ️ UsuarioMS — datos ya existentes, no se insertaron");
        }
    }
}