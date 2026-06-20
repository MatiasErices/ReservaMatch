package cl.duoc.usuarioMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.usuarioMS.model.Rol;
import cl.duoc.usuarioMS.model.Usuario;
import cl.duoc.usuarioMS.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository; // repository simulado para pruebas unitarias

    @InjectMocks
    private UsuarioService usuarioService; // usuario service real, pero con el Mock del repo inyectado

    private Usuario usuarioEjemplo;
    private Rol rolEjemplo;

    @BeforeEach
    void setUp() {

        rolEjemplo = new Rol();
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
    void listar_devuelveListaDeUsuarios() {

        // ARRANGE: preparamos la prueba
        List<Usuario> listaEjemplo = List.of(usuarioEjemplo);
        when(repository.findAll()).thenReturn(listaEjemplo);

        // ACT: llamamos al método real del service
        List<Usuario> resultado = usuarioService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_retornaUsuarioGuardado() {

        // ARRANGE
        when(repository.save(usuarioEjemplo)).thenReturn(usuarioEjemplo);

        // ACT
        Usuario resultado = usuarioService.guardar(usuarioEjemplo);

        // ASSERT
        assertEquals("Juan Pérez", resultado.getNombre());
        verify(repository, times(1)).save(usuarioEjemplo);
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrado() {

        // ARRANGE: el repo devolverá un Optional con el usuario
        Optional<Usuario> usuarioOptional = Optional.of(usuarioEjemplo);
        when(repository.findById(1)).thenReturn(usuarioOptional);

        // ACT
        Usuario resultado = usuarioService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {

        // ARRANGE: el repo devolverá un Optional vacío
        Optional<Usuario> optionalVacio = Optional.empty();
        when(repository.findById(99)).thenReturn(optionalVacio);

        // ACT: ejecutamos el método buscarPorId(99) debería devolver un error,
        // así que capturamos el error para analizarlo
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            usuarioService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Usuario no encontrado", error.getMessage());
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        Usuario usuarioNuevo = new Usuario();
        usuarioNuevo.setNombre("Juan Actualizado");
        usuarioNuevo.setEmail("juanact@mail.com");
        usuarioNuevo.setPassword("nuevaClave");
        usuarioNuevo.setRut("12345678-9");
        usuarioNuevo.setTelefono("987654321");
        usuarioNuevo.setRol(rolEjemplo);

        when(repository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));
        when(repository.save(any(Usuario.class))).thenReturn(usuarioEjemplo);

        // ACT
        Usuario resultado = usuarioService.actualizar(1, usuarioNuevo);

        // ASSERT
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("juanact@mail.com", resultado.getEmail());
        verify(repository, times(1)).save(usuarioEjemplo);
    }


    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(usuarioEjemplo));

        // ACT
        usuarioService.eliminar(1);

        // ASSERT
        verify(repository, times(1)).deleteById(1);
    }


}