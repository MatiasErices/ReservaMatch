package cl.duoc.pagoMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.duoc.pagoMS.repository.PagoRepository;

@Component
public class DataLoader {

    @Autowired
    private PagoRepository pagoRepository;

    public void run(String... args) throws Exception {

        if (pagoRepository.count() == 0) {
            System.out.println("✅ PagoMS — datos cargados correctamente");
        } else {
            System.out.println("ℹ️ PagoMS — datos ya existentes, no se insertaron");
        }
    }

}
