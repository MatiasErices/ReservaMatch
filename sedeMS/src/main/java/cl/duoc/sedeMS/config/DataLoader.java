package cl.duoc.sedeMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.sedeMS.model.Sede;
import cl.duoc.sedeMS.repository.SedeRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private SedeRepository sedeRepository;

    @Override
    public void run(String... args) throws Exception {

        if (sedeRepository.count() == 0) {

            sedeRepository.save(new Sede(null, "Sede Santiago Centro",
                    "Av. Libertador 1234", "Santiago", "222345678"));

            sedeRepository.save(new Sede(null, "Sede Providencia",
                    "Av. Providencia 5678", "Providencia", "222987654"));

            sedeRepository.save(new Sede(null, "Sede Maipú",
                    "Av. Pajaritos 9012", "Maipú", "222456789"));

            System.out.println("✅ SedeMS — datos cargados correctamente");
        } else {
            System.out.println("ℹ️ SedeMS — datos ya existentes, no se insertaron");
        }
    }
}