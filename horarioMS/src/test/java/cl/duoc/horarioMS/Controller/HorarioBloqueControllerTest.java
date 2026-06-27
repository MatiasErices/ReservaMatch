package cl.duoc.horarioMS.Controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.duoc.horarioMS.controller.HorarioBloqueController;
import cl.duoc.horarioMS.model.HorarioBloque;
import cl.duoc.horarioMS.service.HorarioBloqueService;

@ExtendWith(MockitoExtension.class)
public class HorarioBloqueControllerTest {

    @Mock
    private HorarioBloqueService service; // service simulado para pruebas unitarias

    @InjectMocks
    private HorarioBloqueController horarioBloqueController; // controller real, con el Mock del service inyectado

    private HorarioBloque bloqueEjemplo;
    private Date fechaEjemplo;

    @BeforeEach
    void setUp() {

        fechaEjemplo = new Date(); // fecha actual para el bloque de ejemplo

        bloqueEjemplo = new HorarioBloque();
        bloqueEjemplo.setId(1);
        bloqueEjemplo.setFecha(fechaEjemplo);
        bloqueEjemplo.setHoraInicio("08:00");
        bloqueEjemplo.setHoraFin("09:00");
        bloqueEjemplo.setDisponible(true);

    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaConBloques() {

        // ARRANGE
        List<HorarioBloque> listaEjemplo = List.of(bloqueEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<HorarioBloque>> respuesta = horarioBloqueController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listar_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<HorarioBloque>> respuesta = horarioBloqueController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ LISTAR DISPONIBLES ============

    @Test
    void listarDisponibles_devuelveListaConBloques() {

        // ARRANGE
        List<HorarioBloque> listaEjemplo = List.of(bloqueEjemplo);
        when(service.listarDisponibles()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<HorarioBloque>> respuesta = horarioBloqueController.listarDisponibles();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarDisponibles_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarDisponibles()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<HorarioBloque>> respuesta = horarioBloqueController.listarDisponibles();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ BUSCAR ============

    @Test
    void buscar_encontrado() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(bloqueEjemplo);

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("08:00", respuesta.getBody().getHoraInicio());
    }

    @Test
    void buscar_noEncontrado() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Bloque horario no encontrado"));

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_creaBloqueCorrectamente() {

        // ARRANGE
        when(service.guardar(bloqueEjemplo)).thenReturn(bloqueEjemplo);

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.guardar(bloqueEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("08:00", respuesta.getBody().getHoraInicio());
        verify(service, times(1)).guardar(bloqueEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        when(service.actualizar(1, bloqueEjemplo)).thenReturn(bloqueEjemplo);

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.actualizar(1, bloqueEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("08:00", respuesta.getBody().getHoraInicio());
    }

    @Test
    void actualizar_noEncontrado() {

        // ARRANGE
        when(service.actualizar(99, bloqueEjemplo)).thenThrow(new RuntimeException("Bloque horario no encontrado"));

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.actualizar(99, bloqueEjemplo);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ MARCAR NO DISPONIBLE ============

    @Test
    void marcarNoDisponible_marcaCorrectamente() {

        // ARRANGE
        when(service.marcarNoDisponible(1)).thenReturn(bloqueEjemplo);

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.marcarNoDisponible(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void marcarNoDisponible_noEncontrado() {

        // ARRANGE
        when(service.marcarNoDisponible(99)).thenThrow(new RuntimeException("Bloque horario no encontrado"));

        // ACT
        ResponseEntity<HorarioBloque> respuesta = horarioBloqueController.marcarNoDisponible(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = horarioBloqueController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        doThrow(new RuntimeException("Bloque horario no encontrado")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = horarioBloqueController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
