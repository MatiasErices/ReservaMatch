package cl.duoc.reservaMS.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import static org.mockito.Mockito.doThrow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.service.ReservaService;

@ExtendWith(MockitoExtension.class)
public class ReservaControllerTest {


    @Mock
    private ReservaService service; // service simulado para pruebas unitarias

    @InjectMocks
    private ReservaController reservaController; // controller real, pero con el Mock del service inyectado

    private Reserva reservaEjemplo;


    @BeforeEach
    void setUp() {
        reservaEjemplo = new Reserva();
        reservaEjemplo.setId(1);
        reservaEjemplo.setUsuarioId(1);
        reservaEjemplo.setCanchaId(1);
        reservaEjemplo.setSedeId(1);
        reservaEjemplo.setHoraBloqueId(1);
        reservaEjemplo.setFechaReserva(new Date());
        reservaEjemplo.setEstado("CONFIRMADA");
        reservaEjemplo.setMontoTotal(10000.0);
    }


    @Test
    void listar_devuelveListaConReservas() {

        // ARRANGE
        List<Reserva> listaEjemplo = List.of(reservaEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Reserva>> respuesta = reservaController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(listaEjemplo, respuesta.getBody());
        
    }
    
    @Test
    void listar_devuelveNoContentCuandoNoHayReservas() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Reserva>> respuesta = reservaController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertEquals(null, respuesta.getBody());
        
    }

    @Test
    void buscar_devuelveReservaExistente() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(reservaEjemplo);

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(reservaEjemplo, respuesta.getBody());
        
    }

    @Test 
    void buscar_devuelveNotFoundCuandoNoExiste() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Reserva no encontrada"));

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
        assertEquals(null, respuesta.getBody());
    }


    @Test
    void listarPorUsuario_devuelveListaConReservasDelUsuario() {

        // ARRANGE
        List<Reserva> listaEjemplo = List.of(reservaEjemplo);
        when(service.listarPorUsuario(1)).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Reserva>> respuesta = reservaController.listarPorUsuario(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(listaEjemplo, respuesta.getBody());
        
    }

    @Test
    void listarPorEstado_devuelveListaConReservasDelEstado() {

        // ARRANGE
        List<Reserva> listaEjemplo = List.of(reservaEjemplo);
        when(service.listarPorEstado("CONFIRMADA")).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Reserva>> respuesta = reservaController.listarPorEstado("CONFIRMADA");

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(listaEjemplo, respuesta.getBody());
        
    }

    @Test
    void guardar_HacerReservaCorrectamente() {

        // ARRANGE
        when(service.guardar(reservaEjemplo)).thenReturn(reservaEjemplo);

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.guardar(reservaEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(reservaEjemplo, respuesta.getBody());
        
    }

    @Test
    void guardar_devuelveBadRequestCuandoFalla() {

        // ARRANGE
        when(service.guardar(reservaEjemplo)).thenThrow(new RuntimeException("Cancha no disponible"));

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.guardar(reservaEjemplo);

        // ASSERT
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    @Test
    void confirmarReserva_devuelveReservaConfirmada() {

        // ARRANGE
        when(service.confirmar(1)).thenReturn(reservaEjemplo);

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.confirmar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(reservaEjemplo, respuesta.getBody());
        
    }

    @Test
    void confirmarReserva_devuelveNotFoundCuandoNoExiste() {

        // ARRANGE
        when(service.confirmar(99)).thenThrow(new RuntimeException("Reserva no encontrada"));

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.confirmar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
}


    @Test
    void cancelarReserva_devuelveReservaCancelada() {

        // ARRANGE
        when(service.cancelar(1)).thenReturn(reservaEjemplo);

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.cancelar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(reservaEjemplo, respuesta.getBody());
        
    }

    @Test
    void cancelarReserva_devuelveNotFoundCuandoNoExiste() {

        // ARRANGE
        when(service.cancelar(99)).thenThrow(new RuntimeException("Reserva no encontrada"));

        // ACT
        ResponseEntity<Reserva> respuesta = reservaController.cancelar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void eliminarReserva_devuelveReservaEliminada() {

        // ARRANGE
        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = reservaController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertEquals(null, respuesta.getBody());  
        
    }

    @Test
    void eliminarReserva_devuelveNotFoundCuandoNoExiste() {

        // ARRANGE
        doThrow(new RuntimeException("Reserva no encontrada")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = reservaController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }









}

