package cl.duoc.canchaMS.Controller;

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

import cl.duoc.canchaMS.controller.CanchaController;
import cl.duoc.canchaMS.dto.CanchaDTO;
import cl.duoc.canchaMS.dto.SedeDTO;
import cl.duoc.canchaMS.model.Cancha;
import cl.duoc.canchaMS.model.TipoCancha;
import cl.duoc.canchaMS.service.CanchaService;

@ExtendWith(MockitoExtension.class)
public class CanchaControllerTest {

    @Mock
    private CanchaService service; // service simulado para pruebas unitarias

    @InjectMocks
    private CanchaController canchaController; // controller real, con el Mock del service inyectado

    private Cancha canchaEjemplo;
    private CanchaDTO dtoEjemplo;

    @BeforeEach
    void setUp() {

        TipoCancha tipoCanchaEjemplo = new TipoCancha();
        tipoCanchaEjemplo.setId(1);
        tipoCanchaEjemplo.setNombre("Fútbol");
        tipoCanchaEjemplo.setDescripcion("Cancha de fútbol techada");

        canchaEjemplo = new Cancha();
        canchaEjemplo.setId(1);
        canchaEjemplo.setNombre("Cancha Central");
        canchaEjemplo.setCapacidad(10);
        canchaEjemplo.setPrecioPorHora(15000.0);
        canchaEjemplo.setTipoCancha(tipoCanchaEjemplo);
        canchaEjemplo.setSedeId(1);
        canchaEjemplo.setDisponible(true);

        SedeDTO sedeDTOEjemplo = new SedeDTO(1, "Sede Providencia", "Av. Siempre Viva 123", "Providencia", "+56912345678");
        dtoEjemplo = new CanchaDTO(1, "Cancha Central", 10, 15000.0, true, "Fútbol", sedeDTOEjemplo);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaConCanchas() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listar_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ LISTAR DISPONIBLES ============

    @Test
    void listarDisponibles_devuelveListaConCanchas() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(service.listarDisponibles()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listarDisponibles();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarDisponibles_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarDisponibles()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listarDisponibles();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ LISTAR POR SEDE ============

    @Test
    void listarPorSede_devuelveListaConCanchas() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(service.listarPorSede(1)).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listarPorSede(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarPorSede_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarPorSede(1)).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listarPorSede(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    @Test
    void listarPorSedeDisponibles_devuelveListaConCanchas() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(service.listarPorSedeDisponibles(1)).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listarPorSedeDisponibles(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
    }

    @Test
    void listarPorSedeDisponibles_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listarPorSedeDisponibles(1)).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Cancha>> respuesta = canchaController.listarPorSedeDisponibles(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ BUSCAR ============

    @Test
    void buscar_encontrada() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(canchaEjemplo);

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Cancha Central", respuesta.getBody().getNombre());
    }

    @Test
    void buscar_noEncontrada() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Cancha no encontrada"));

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ OBTENER DTO ============

    @Test
    void obtenerDTO_encontrada() {

        // ARRANGE
        when(service.obtenerDTO(1)).thenReturn(dtoEjemplo);

        // ACT
        ResponseEntity<CanchaDTO> respuesta = canchaController.obtenerDTO(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Cancha Central", respuesta.getBody().getNombre());
        assertEquals("Fútbol", respuesta.getBody().getTipoCancha());
    }

    @Test
    void obtenerDTO_noEncontrada() {

        // ARRANGE
        when(service.obtenerDTO(99)).thenThrow(new RuntimeException("Cancha no encontrada"));

        // ACT
        ResponseEntity<CanchaDTO> respuesta = canchaController.obtenerDTO(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_creaCanchaCorrectamente() {

        // ARRANGE
        when(service.guardar(canchaEjemplo)).thenReturn(canchaEjemplo);

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.guardar(canchaEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Cancha Central", respuesta.getBody().getNombre());
        verify(service, times(1)).guardar(canchaEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        when(service.actualizar(1, canchaEjemplo)).thenReturn(canchaEjemplo);

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.actualizar(1, canchaEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Cancha Central", respuesta.getBody().getNombre());
    }

    @Test
    void actualizar_noEncontrada() {

        // ARRANGE
        when(service.actualizar(99, canchaEjemplo)).thenThrow(new RuntimeException("Cancha no encontrada"));

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.actualizar(99, canchaEjemplo);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ MARCAR NO DISPONIBLE ============

    @Test
    void marcarNoDisponible_marcaCorrectamente() {

        // ARRANGE
        when(service.marcarNoDisponible(1)).thenReturn(canchaEjemplo);

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.marcarNoDisponible(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
    }

    @Test
    void marcarNoDisponible_noEncontrada() {

        // ARRANGE
        when(service.marcarNoDisponible(99)).thenThrow(new RuntimeException("Cancha no encontrada"));

        // ACT
        ResponseEntity<Cancha> respuesta = canchaController.marcarNoDisponible(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = canchaController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_noEncontrada() {

        // ARRANGE
        doThrow(new RuntimeException("Cancha no encontrada")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = canchaController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
