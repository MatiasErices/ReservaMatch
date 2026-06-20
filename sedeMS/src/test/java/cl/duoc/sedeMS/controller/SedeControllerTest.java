package cl.duoc.sedeMS.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import cl.duoc.sedeMS.model.Sede;
import cl.duoc.sedeMS.service.SedeService;

@ExtendWith(MockitoExtension.class)
public class SedeControllerTest {

    @Mock
    private SedeService service; // dependencia simulada

    @InjectMocks
    private SedeController controller; // controller real, con el service mock inyectado

    private Sede sedeEjemplo;

    @BeforeEach
    void setUp() {
        sedeEjemplo = new Sede();
        sedeEjemplo.setId(1);
        sedeEjemplo.setNombre("Sede Central");
        sedeEjemplo.setDireccion("Av. Principal 123");
        sedeEjemplo.setComuna("Santiago");
        sedeEjemplo.setTelefono("123456789");
    }

    @Test
    void listar_devuelveListaConSedes() {
        // ARRANGE
        when(service.listar()).thenReturn(List.of(sedeEjemplo));

        // ACT
        ResponseEntity<List<Sede>> respuesta = controller.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(List.of(sedeEjemplo), respuesta.getBody());
    }

    @Test
    void listar_devuelveNoContentCuandoListaVacia() {
        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Sede>> respuesta = controller.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertEquals(null, respuesta.getBody());
    }

    @Test
    void buscarPorId_devuelveSedeExistente() {
        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(sedeEjemplo);

        // ACT
        ResponseEntity<Sede> respuesta = controller.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(sedeEjemplo, respuesta.getBody());
    }

    @Test
    void buscarPorId_devuelveNotFoundCuandoNoExiste() {
        // ARRANGE
        when(service.buscarPorId(999)).thenThrow(new RuntimeException("Sede no encontrada"));

        // ACT
        ResponseEntity<Sede> respuesta = controller.buscar(999);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void eliminar_devuelveNoContent() {

        // ACT
        ResponseEntity<Void> respuesta = controller.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertEquals(null, respuesta.getBody());
    }

    @Test
    void eliminar_devuelveNotFoundCuandoSedeNoExiste() {
        // ARRANGE
        doThrow(new RuntimeException("Sede no encontrada")).when(service).eliminar(999);

        // ACT
        ResponseEntity<Void> respuesta = controller.eliminar(999);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    @Test
    void listarPorComuna_devuelveListaDeSedes() {
        // ARRANGE
        List<Sede> listaEjemplo = List.of(sedeEjemplo);
        when(service.listarPorComuna("Santiago")).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Sede>> respuesta = controller.listarPorComuna("Santiago");

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(listaEjemplo, respuesta.getBody());
    }

    @Test
    void listarPorComuna_devuelveNoContentCuandoNoHaySedes() {
        // ARRANGE
        when(service.listarPorComuna("Valparaiso")).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Sede>> respuesta = controller.listarPorComuna("Valparaiso");

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        assertEquals(null, respuesta.getBody());
    }

    @Test
    void guardar_devuelveSedeGuardada() {
        // ARRANGE
        when(service.guardar(sedeEjemplo)).thenReturn(sedeEjemplo);

        // ACT
        ResponseEntity<Sede> respuesta = controller.guardar(sedeEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(sedeEjemplo, respuesta.getBody());
    }

    @Test
    void actualizar_devuelveSedeActualizada() {
        // ARRANGE
        Sede sedeActualizada = new Sede();
        sedeActualizada.setId(1);
        sedeActualizada.setNombre("Sede Central Actualizada");
        sedeActualizada.setDireccion("Av. Principal 123");
        sedeActualizada.setComuna("Santiago");
        sedeActualizada.setTelefono("987654321");

        when(service.actualizar(1, sedeActualizada)).thenReturn(sedeActualizada);

        // ACT
        ResponseEntity<Sede> respuesta = controller.actualizar(1, sedeActualizada);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(sedeActualizada, respuesta.getBody());
    }

    @Test
    void actualizar_devuelveNotFoundCuandoSedeNoExiste() {
        // ARRANGE
        when(service.actualizar(999, sedeEjemplo)).thenThrow(new RuntimeException("Sede no encontrada"));

        // ACT
        ResponseEntity<Sede> respuesta = controller.actualizar(999, sedeEjemplo);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}