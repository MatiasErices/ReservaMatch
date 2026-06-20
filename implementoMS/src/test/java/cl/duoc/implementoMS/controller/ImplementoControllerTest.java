package cl.duoc.implementoMS.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.duoc.implementoMS.model.Implemento;
import cl.duoc.implementoMS.service.ImplementoService;

@ExtendWith(MockitoExtension.class)
public class ImplementoControllerTest {

    @Mock
    private ImplementoService service; // service simulado para pruebas unitarias

    @InjectMocks
    private ImplementoController implementoController; // controller real, con el Mock del service inyectado

    private Implemento implementoEjemplo;

    @BeforeEach
    void setUp() {

        implementoEjemplo = new Implemento();
        implementoEjemplo.setId(1);
        implementoEjemplo.setNombre("Pelota de Fútbol");
        implementoEjemplo.setDescripcion("Pelota oficial de cuero tamaño 5");
        implementoEjemplo.setStock(10);
        implementoEjemplo.setPrecioArriendo(2000.0);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaConImplementos() {

        // ARRANGE
        List<Implemento> listaEjemplo = List.of(implementoEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Implemento>> respuesta = implementoController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listar_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Implemento>> respuesta = implementoController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ LISTAR DISPONIBLES ============

    @Test
    void listarDisponibles_devuelveListaConImplementos() {

        // ARRANGE
        List<Implemento> listaEjemplo = List.of(implementoEjemplo);
        when(service.listarDisponibles()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Implemento>> respuesta = implementoController.listarDisponibles();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarDisponibles_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarDisponibles()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Implemento>> respuesta = implementoController.listarDisponibles();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ BUSCAR ============

    @Test
    void buscar_encontrado() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(implementoEjemplo);

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Pelota de Fútbol", respuesta.getBody().getNombre());
    }

    @Test
    void buscar_noEncontrado() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Implemento no encontrado"));

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_creaImplementoCorrectamente() {

        // ARRANGE
        when(service.guardar(implementoEjemplo)).thenReturn(implementoEjemplo);

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.guardar(implementoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Pelota de Fútbol", respuesta.getBody().getNombre());
        verify(service, times(1)).guardar(implementoEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        when(service.actualizar(1, implementoEjemplo)).thenReturn(implementoEjemplo);

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.actualizar(1, implementoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Pelota de Fútbol", respuesta.getBody().getNombre());
    }

    @Test
    void actualizar_noEncontrado() {

        // ARRANGE
        when(service.actualizar(99, implementoEjemplo)).thenThrow(new RuntimeException("Implemento no encontrado"));

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.actualizar(99, implementoEjemplo);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ DESCONTAR STOCK ============

    @Test
    void descontarStock_descuentaCorrectamente() {

        // ARRANGE
        when(service.descontarStock(1, 3)).thenReturn(implementoEjemplo);

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.descontarStock(1, 3);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void descontarStock_stockInsuficiente() {

        // ARRANGE: el catch de este método responde badRequest, no notFound
        when(service.descontarStock(1, 50)).thenThrow(new RuntimeException("Stock insuficiente"));

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.descontarStock(1, 50);

        // ASSERT
        assertEquals(HttpStatus.BAD_REQUEST, respuesta.getStatusCode());
    }

    // ============ DEVOLVER STOCK ============

    @Test
    void devolverStock_devuelveCorrectamente() {

        // ARRANGE
        when(service.devolverStock(1, 5)).thenReturn(implementoEjemplo);

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.devolverStock(1, 5);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void devolverStock_noEncontrado() {

        // ARRANGE
        when(service.devolverStock(99, 5)).thenThrow(new RuntimeException("Implemento no encontrado"));

        // ACT
        ResponseEntity<Implemento> respuesta = implementoController.devolverStock(99, 5);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = implementoController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        doThrow(new RuntimeException("Implemento no encontrado")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = implementoController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}