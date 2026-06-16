package cl.duoc.implementoMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.implementoMS.model.Implemento;
import cl.duoc.implementoMS.repository.ImplementoRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ImplementoRepository implementoRepository;

    @Override
    public void run(String... args) throws Exception {

        if (implementoRepository.count() == 0) {

            implementoRepository.save(new Implemento(null,
                    "Pelota de Fútbol",
                    "Pelota oficial de cuero tamaño 5",
                    10,
                    2000.0));

            implementoRepository.save(new Implemento(null,
                    "Peto",
                    "Peto de colores para distinguir equipos",
                    20,
                    500.0));

            implementoRepository.save(new Implemento(null,
                    "Arco Portátil",
                    "Arco plegable de aluminio",
                    4,
                    3000.0));

            implementoRepository.save(new Implemento(null,
                    "Bomba de Aire",
                    "Bomba manual para inflar pelotas",
                    5,
                    500.0));

            implementoRepository.save(new Implemento(null,
                    "Conos de Entrenamiento",
                    "Set de 10 conos de colores",
                    8,
                    1000.0));

            System.out.println("✅ ImplementoMS — datos cargados correctamente");
        } else {
            System.out.println("ℹ️ ImplementoMS — datos ya existentes, no se insertaron");
        }
    }
}