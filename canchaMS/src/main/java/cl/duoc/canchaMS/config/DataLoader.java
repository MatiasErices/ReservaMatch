package cl.duoc.canchaMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.canchaMS.model.Cancha;
import cl.duoc.canchaMS.model.TipoCancha;
import cl.duoc.canchaMS.repository.CanchaRepository;
import cl.duoc.canchaMS.repository.TipoCanchaRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private TipoCanchaRepository tipoCanchaRepository;

    @Autowired
    private CanchaRepository canchaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (tipoCanchaRepository.count() == 0) {

            TipoCancha indoor = tipoCanchaRepository.save(
                    new TipoCancha(null, "Indoor", "Cancha techada con pasto sintético"));

            TipoCancha outdoor = tipoCanchaRepository.save(
                    new TipoCancha(null, "Outdoor", "Cancha al aire libre con pasto natural"));

            canchaRepository.save(new Cancha(null, "Cancha 1", 7, 30000.0, indoor, 1, true));
            canchaRepository.save(new Cancha(null, "Cancha 2", 7, 30000.0, outdoor, 1, true));

            canchaRepository.save(new Cancha(null, "Cancha 3", 7, 30000.0, indoor, 2, true));
            canchaRepository.save(new Cancha(null, "Cancha 4", 7, 30000.0, outdoor, 2, true));

            canchaRepository.save(new Cancha(null, "Cancha 5", 7, 30000.0, indoor, 3, true));
            canchaRepository.save(new Cancha(null, "Cancha 6", 7, 30000.0, outdoor, 3, true));

            System.out.println("✅ CanchaMS — datos cargados correctamente");
        } else {
            System.out.println("ℹ️ CanchaMS — datos ya existentes, no se insertaron");
        }
    }
}