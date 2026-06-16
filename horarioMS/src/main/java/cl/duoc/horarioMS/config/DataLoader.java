package cl.duoc.horarioMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.horarioMS.model.HorarioBloque;
import cl.duoc.horarioMS.repository.HorarioBloqueRepository;

import java.util.Calendar;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private HorarioBloqueRepository repository;

    @Override
    public void run(String... args) throws Exception {

        if (repository.count() == 0) {

            Calendar cal = Calendar.getInstance();

            for (int dia = 0; dia < 3; dia++) {

                cal.add(Calendar.DAY_OF_MONTH, dia == 0 ? 0 : 1);
                Date fecha = cal.getTime();

                repository.save(new HorarioBloque(null, fecha, "08:00", "09:00", true));
                repository.save(new HorarioBloque(null, fecha, "09:00", "10:00", true));
                repository.save(new HorarioBloque(null, fecha, "10:00", "11:00", true));
                repository.save(new HorarioBloque(null, fecha, "11:00", "12:00", true));
                repository.save(new HorarioBloque(null, fecha, "12:00", "13:00", true));
                repository.save(new HorarioBloque(null, fecha, "13:00", "14:00", true));
                repository.save(new HorarioBloque(null, fecha, "14:00", "15:00", true));
                repository.save(new HorarioBloque(null, fecha, "15:00", "16:00", true));
                repository.save(new HorarioBloque(null, fecha, "16:00", "17:00", true));
                repository.save(new HorarioBloque(null, fecha, "17:00", "18:00", true));
                repository.save(new HorarioBloque(null, fecha, "18:00", "19:00", true));
                repository.save(new HorarioBloque(null, fecha, "19:00", "20:00", true));
                repository.save(new HorarioBloque(null, fecha, "20:00", "21:00", true));
                repository.save(new HorarioBloque(null, fecha, "21:00", "22:00", true));
            }

            System.out.println("✅ HorarioMS — datos cargados correctamente");
        } else {
            System.out.println("ℹ️ HorarioMS — datos ya existentes, no se insertaron");
        }
    }
}