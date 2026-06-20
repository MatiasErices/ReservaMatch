package cl.duoc.arriendoImplementoMS.Controller;

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

import cl.duoc.arriendoImplementoMS.controller.ArriendoImplementoController;
import cl.duoc.arriendoImplementoMS.dto.ArriendoImplementoDTO;
import cl.duoc.arriendoImplementoMS.dto.ImplementoDTO;
import cl.duoc.arriendoImplementoMS.dto.ReservaDTO;
import cl.duoc.arriendoImplementoMS.model.ArriendoImplemento;
import cl.duoc.arriendoImplementoMS.service.ArriendoImplementoService;

@ExtendWith(MockitoExtension.class)
public class ArriendoImplementoControllerTest {

    @Mock
    private ArriendoImplementoService service; // service simulado para pruebas unitarias

    @InjectMocks
    private ArriendoImplementoController arriendoImplementoController; // controller real, con el Mock del service inyectado

    private ArriendoImplemento arriendoEjemplo;
    private ArriendoImplementoDTO dtoEjemplo;

    @BeforeEach
    void setUp() {

        arriendoEjemplo = new ArriendoImplemento();
        arriendoEjemplo.setId(1);
        arriendoEjemplo.setReservaId(1);
        arriendoEjemplo.setImplementoId(1);
        arriendoEjemplo.setCantidad(3);
        arriendoEjemplo.setFechaArriendo(new Date(1_700_000_000_000L));
        arriendoEjemplo.setMontoTotal(6000.0);

        ImplementoDTO implementoDTO = new ImplementoDTO(1, "Pelota de Fútbol", "Pelota oficial talla 5", 7, 2000.0);
        ReservaDTO reservaDTO = new ReservaDTO(1, new Date(1_699_000_000_000L), "CONFIRMADA", 5000.0, null, null, null);

        dtoEjemplo = new ArriendoImplementoDTO(1, 3, arriendoEjemplo.getFechaArriendo(), 6000.0, implementoDTO, reservaDTO);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaConArriendos() {

        // ARRANGE
        List<ArriendoImplemento> listaEjemplo = List.of(arriendoEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<ArriendoImplemento>> respuesta = arriendoImplementoController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listar_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<ArriendoImplemento>> respuesta = arriendoImplementoController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ BUSCAR ============

    @Test
    void buscar_encontrado() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(arriendoEjemplo);

        // ACT
        ResponseEntity<ArriendoImplemento> respuesta = arriendoImplementoController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(3, respuesta.getBody().getCantidad());
    }

    @Test
    void buscar_noEncontrado() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Arriendo no encontrado"));

        // ACT
        ResponseEntity<ArriendoImplemento> respuesta = arriendoImplementoController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ OBTENER DTO ============

    @Test
    void obtenerDTO_encontrado() {

        // ARRANGE
        when(service.obtenerDTO(1)).thenReturn(dtoEjemplo);

        // ACT
        ResponseEntity<ArriendoImplementoDTO> respuesta = arriendoImplementoController.obtenerDTO(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(6000.0, respuesta.getBody().getMontoTotal());
    }

    @Test
    void obtenerDTO_noEncontrado() {

        // ARRANGE
        when(service.obtenerDTO(99)).thenThrow(new RuntimeException("Arriendo no encontrado"));

        // ACT
        ResponseEntity<ArriendoImplementoDTO> respuesta = arriendoImplementoController.obtenerDTO(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ LISTAR POR RESERVA / POR IMPLEMENTO ============

    @Test
    void listarPorReserva_devuelveListaConArriendos() {

        // ARRANGE
        List<ArriendoImplemento> listaEjemplo = List.of(arriendoEjemplo);
        when(service.listarPorReserva(1)).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<ArriendoImplemento>> respuesta = arriendoImplementoController.listarPorReserva(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarPorReserva_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarPorReserva(1)).thenReturn(List.of());

        // ACT
        ResponseEntity<List<ArriendoImplemento>> respuesta = arriendoImplementoController.listarPorReserva(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    @Test
    void listarPorImplemento_devuelveListaConArriendos() {

        // ARRANGE
        List<ArriendoImplemento> listaEjemplo = List.of(arriendoEjemplo);
        when(service.listarPorImplemento(1)).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<ArriendoImplemento>> respuesta = arriendoImplementoController.listarPorImplemento(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarPorImplemento_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarPorImplemento(1)).thenReturn(List.of());

        // ACT
        ResponseEntity<List<ArriendoImplemento>> respuesta = arriendoImplementoController.listarPorImplemento(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ CREAR ============

    @Test
    void crear_creaArriendoCorrectamente() {

        // ARRANGE
        when(service.crear(arriendoEjemplo)).thenReturn(arriendoEjemplo);

        // ACT
        ResponseEntity<ArriendoImplemento> respuesta = arriendoImplementoController.crear(arriendoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(6000.0, respuesta.getBody().getMontoTotal());
    }

    @Test
    void crear_devuelveBadRequestSiReglaDeNegocioFalla() {

        // ARRANGE: el catch de este método responde badRequest, no notFound
        when(service.crear(arriendoEjemplo)).thenThrow(new RuntimeException("Stock insuficiente"));

        // ACT
        ResponseEntity<ArriendoImplemento> respuesta = arriendoImplementoController.crear(arriendoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = arriendoImplementoController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        doThrow(new RuntimeException("Arriendo no encontrado")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = arriendoImplementoController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
