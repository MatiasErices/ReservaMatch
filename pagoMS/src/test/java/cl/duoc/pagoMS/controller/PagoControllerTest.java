package cl.duoc.pagoMS.controller;

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

import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.model.TipoPago;
import cl.duoc.pagoMS.service.PagoService;

@ExtendWith(MockitoExtension.class)
public class PagoControllerTest {

    @Mock
    private PagoService service; // service simulado para pruebas unitarias

    @InjectMocks
    private PagoController pagoController; // controller real, con el Mock del service inyectado

    private Pago pagoEjemplo;

    @BeforeEach
    void setUp() {

        TipoPago tipoPagoEjemplo = new TipoPago();
        tipoPagoEjemplo.setId(1);
        tipoPagoEjemplo.setNombre("Débito");

        pagoEjemplo = new Pago();
        pagoEjemplo.setId(1);
        pagoEjemplo.setReservaId(1);
        pagoEjemplo.setMonto(30000.0);
        pagoEjemplo.setFechaPago(new Date());
        pagoEjemplo.setEstado("COMPLETADO");
        pagoEjemplo.setTipoPago(tipoPagoEjemplo);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaConPagos() {

        // ARRANGE
        List<Pago> listaEjemplo = List.of(pagoEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Pago>> respuesta = pagoController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listar_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Pago>> respuesta = pagoController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ BUSCAR ============

    @Test
    void buscar_encontrado() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(pagoEjemplo);

        // ACT
        ResponseEntity<Pago> respuesta = pagoController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("COMPLETADO", respuesta.getBody().getEstado());
    }

    @Test
    void buscar_noEncontrado() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Pago no encontrado"));

        // ACT
        ResponseEntity<Pago> respuesta = pagoController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ LISTAR POR RESERVA ============

    @Test
    void listarPorReserva_devuelveListaConPagos() {

        // ARRANGE
        List<Pago> listaEjemplo = List.of(pagoEjemplo);
        when(service.listarPorReserva(1)).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Pago>> respuesta = pagoController.listarPorReserva(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarPorReserva_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarPorReserva(99)).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Pago>> respuesta = pagoController.listarPorReserva(99);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ LISTAR POR ESTADO ============

    @Test
    void listarPorEstado_devuelveListaConPagos() {

        // ARRANGE
        List<Pago> listaEjemplo = List.of(pagoEjemplo);
        when(service.listarPorEstado("COMPLETADO")).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Pago>> respuesta = pagoController.listarPorEstado("COMPLETADO");

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarPorEstado_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarPorEstado("RECHAZADO")).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Pago>> respuesta = pagoController.listarPorEstado("RECHAZADO");

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ PROCESAR ============

    @Test
    void procesar_procesaCorrectamente() {

        // ARRANGE
        when(service.procesar(pagoEjemplo)).thenReturn(pagoEjemplo);

        // ACT
        ResponseEntity<Pago> respuesta = pagoController.procesar(pagoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("COMPLETADO", respuesta.getBody().getEstado());
    }

    @Test
    void procesar_errorAlProcesar() {

        // ARRANGE: el catch de este método responde badRequest, no notFound
        when(service.procesar(pagoEjemplo)).thenThrow(new RuntimeException("La reserva no está en estado PENDIENTE"));

        // ACT
        ResponseEntity<Pago> respuesta = pagoController.procesar(pagoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = pagoController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        doThrow(new RuntimeException("Pago no encontrado")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = pagoController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}