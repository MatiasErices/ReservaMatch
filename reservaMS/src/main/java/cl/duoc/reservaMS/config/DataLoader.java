package cl.duoc.reservaMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.repository.ReservaRepository;

import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void run(String... args) throws Exception {

        if (reservaRepository.count() == 0) {

            Reserva reserva = new Reserva();
            reserva.setUsuarioId(1);
            reserva.setCanchaId(1);
            reserva.setSedeId(1);
            reserva.setHoraBloqueId(1);
            reserva.setFechaReserva(new Date());
            reserva.setEstado("CONFIRMADA");
            reserva.setMontoTotal(30000.0);

            reservaRepository.save(reserva);

            System.out.println("✅ ReservaMS — reserva ficticia cargada correctamente");
        } else {
            System.out.println("ℹ️ ReservaMS — datos ya existentes, no se insertaron");
        }
    }
}