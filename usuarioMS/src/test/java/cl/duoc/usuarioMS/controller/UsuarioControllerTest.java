package cl.duoc.usuarioMS.controller;

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

import cl.duoc.usuarioMS.model.Rol;
import cl.duoc.usuarioMS.model.Usuario;
import cl.duoc.usuarioMS.service.UsuarioService;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    @Mock
    private UsuarioService service; // service simulado para pruebas unitarias

    @InjectMocks
    private UsuarioController usuarioController; // controller real, pero con el Mock del service inyectado

    private Usuario usuarioEjemplo;

    @BeforeEach
    void setUp() {

        Rol rolEjemplo = new Rol();
        rolEjemplo.setId(1);
        rolEjemplo.setNombre("CLIENTE");

        usuarioEjemplo = new Usuario();
        usuarioEjemplo.setId(1);
        usuarioEjemplo.setNombre("Juan Pérez");
        usuarioEjemplo.setEmail("juan@mail.com");
        usuarioEjemplo.setPassword("1234");
        usuarioEjemplo.setRut("12345678-9");
        usuarioEjemplo.setTelefono("912345678");
        usuarioEjemplo.setRol(rolEjemplo);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaConUsuarios() {

        // ARRANGE
        List<Usuario> listaEjemplo = List.of(usuarioEjemplo);
        when(service.listar()).thenReturn(listaEjemplo);

        // ACT
        ResponseEntity<List<Usuario>> respuesta = usuarioController.listar();

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals(1, respuesta.getBody().size());
        assertEquals("Juan Pérez", respuesta.getBody().get(0).getNombre());
    }

    @Test
    void listar_devuelveNoContentSiListaVacia() {

        // ARRANGE
        when(service.listar()).thenReturn(List.of());

        // ACT
        ResponseEntity<List<Usuario>> respuesta = usuarioController.listar();

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
    }

    // ============ BUSCAR ============

    @Test
    void buscar_encontrado() {

        // ARRANGE
        when(service.buscarPorId(1)).thenReturn(usuarioEjemplo);

        // ACT
        ResponseEntity<Usuario> respuesta = usuarioController.buscar(1);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Juan Pérez", respuesta.getBody().getNombre());
    }

    @Test
    void buscar_noEncontrado() {

        // ARRANGE
        when(service.buscarPorId(99)).thenThrow(new RuntimeException("Usuario no encontrado"));

        // ACT
        ResponseEntity<Usuario> respuesta = usuarioController.buscar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_creaUsuarioCorrectamente() {

        // ARRANGE
        when(service.guardar(usuarioEjemplo)).thenReturn(usuarioEjemplo);

        // ACT
        ResponseEntity<Usuario> respuesta = usuarioController.guardar(usuarioEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Juan Pérez", respuesta.getBody().getNombre());
        verify(service, times(1)).guardar(usuarioEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        when(service.actualizar(1, usuarioEjemplo)).thenReturn(usuarioEjemplo);

        // ACT
        ResponseEntity<Usuario> respuesta = usuarioController.actualizar(1, usuarioEjemplo);

        // ASSERT
        assertEquals(HttpStatus.OK, respuesta.getStatusCode());
        assertEquals("Juan Pérez", respuesta.getBody().getNombre());
    }

    @Test
    void actualizar_noEncontrado() {

        // ARRANGE
        when(service.actualizar(99, usuarioEjemplo)).thenThrow(new RuntimeException("Usuario no encontrado"));

        // ACT
        ResponseEntity<Usuario> respuesta = usuarioController.actualizar(99, usuarioEjemplo);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        doNothing().when(service).eliminar(1);

        // ACT
        ResponseEntity<Void> respuesta = usuarioController.eliminar(1);

        // ASSERT
        assertEquals(HttpStatus.NO_CONTENT, respuesta.getStatusCode());
        verify(service, times(1)).eliminar(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        doThrow(new RuntimeException("Usuario no encontrado")).when(service).eliminar(99);

        // ACT
        ResponseEntity<Void> respuesta = usuarioController.eliminar(99);

        // ASSERT
        assertEquals(HttpStatus.NOT_FOUND, respuesta.getStatusCode());
    }
}
