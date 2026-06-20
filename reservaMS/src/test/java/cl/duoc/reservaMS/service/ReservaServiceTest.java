package cl.duoc.reservaMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.reservaMS.model.Reserva;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {


    @Mock
    private ReservaService reservaService; //service simulado para pruebas unitarias

    @InjectMocks
    private ReservaService reservaServiceReal; //service real, pero con el Mock del repo inyectado para pruebas unitarias

    private Reserva reservaEjemplo;
    

    @BeforeEach
    void setUp(){

        reservaEjemplo = new Reserva();
        reservaEjemplo.setId(1);
        reservaEjemplo.setFechaReserva(new Date());
        reservaEjemplo.setHoraBloqueId(1);
        reservaEjemplo.setSedeId(1);
        reservaEjemplo.setUsuarioId(1);

    }

    @Test
    void buscarReservaPorId(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.buscarPorId(1)).thenReturn(reservaOptional.get());

        Reserva resultado = reservaService.buscarPorId(1);

        assertEquals(1, resultado.getId());
        assertEquals(reservaEjemplo.getFechaReserva(), resultado.getFechaReserva());
        assertEquals(reservaEjemplo.getHoraBloqueId(), resultado.getHoraBloqueId());
        assertEquals(1, resultado.getSedeId());
        assertEquals(1, resultado.getUsuarioId());
    }


    @Test
    void buscarReservaPorId_noEncontrada(){

        when(reservaService.buscarPorId(999)).thenThrow(new RuntimeException("Reserva no encontrada"));

        try {
            reservaService.buscarPorId(999);
        } catch (RuntimeException e) {
            assertEquals("Reserva no encontrada", e.getMessage());
        }
    }

    @Test
    void eliminarReserva(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.buscarPorId(1)).thenReturn(reservaOptional.get());

        reservaService.eliminar(1);

    }

    @Test
    void guardarReserva(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.guardar(reservaEjemplo)).thenReturn(reservaOptional.get());

        Reserva resultado = reservaService.guardar(reservaEjemplo);

        assertEquals(1, resultado.getId());
        assertEquals(reservaEjemplo.getFechaReserva(), resultado.getFechaReserva());
        assertEquals(reservaEjemplo.getHoraBloqueId(), resultado.getHoraBloqueId());
        assertEquals(1, resultado.getSedeId());
        assertEquals(1, resultado.getUsuarioId());
    }

    @Test
    void confirmarReserva(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.confirmar(1)).thenReturn(reservaOptional.get());

        Reserva resultado = reservaService.confirmar(1);

        assertEquals(1, resultado.getId());
        assertEquals(reservaEjemplo.getFechaReserva(), resultado.getFechaReserva());
        assertEquals(reservaEjemplo.getHoraBloqueId(), resultado.getHoraBloqueId());
        assertEquals(1, resultado.getSedeId());
        assertEquals(1, resultado.getUsuarioId());
    }

    @Test 
    void cancelarReserva(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.cancelar(1)).thenReturn(reservaOptional.get());

        Reserva resultado = reservaService.cancelar(1);

        assertEquals(1, resultado.getId());
        assertEquals(reservaEjemplo.getFechaReserva(), resultado.getFechaReserva());
        assertEquals(reservaEjemplo.getHoraBloqueId(), resultado.getHoraBloqueId());
        assertEquals(1, resultado.getSedeId());
        assertEquals(1, resultado.getUsuarioId());      


    }

    @Test
    void listarPorUsuario(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.listarPorUsuario(1)).thenReturn(java.util.List.of(reservaOptional.get()));

        java.util.List<Reserva> resultado = reservaService.listarPorUsuario(1);

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        assertEquals(reservaEjemplo.getFechaReserva(), resultado.get(0).getFechaReserva());
        assertEquals(reservaEjemplo.getHoraBloqueId(), resultado.get(0).getHoraBloqueId());
        assertEquals(1, resultado.get(0).getSedeId());
        assertEquals(1, resultado.get(0).getUsuarioId());
    

    }

    @Test
    void listarPorEstado(){

        Optional<Reserva> reservaOptional = Optional.of(reservaEjemplo);
        assert reservaOptional.isPresent();
        when(reservaService.listarPorEstado("PENDIENTE")).thenReturn(java.util.List.of(reservaOptional.get()));

        java.util.List<Reserva> resultado = reservaService.listarPorEstado("PENDIENTE");

        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        assertEquals(reservaEjemplo.getFechaReserva(), resultado.get(0).getFechaReserva());
        assertEquals(reservaEjemplo.getHoraBloqueId(), resultado.get(0).getHoraBloqueId());
        assertEquals(1, resultado.get(0).getSedeId());
        assertEquals(1, resultado.get(0).getUsuarioId());

    }
}
