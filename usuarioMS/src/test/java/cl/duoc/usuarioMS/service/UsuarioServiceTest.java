package cl.duoc.usuarioMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.usuarioMS.dto.UsuarioDTO;
import cl.duoc.usuarioMS.model.Rol;
import cl.duoc.usuarioMS.model.Usuario;
import cl.duoc.usuarioMS.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class) // Habilita el uso de Mockito en esta clase
public class UsuarioServiceTest {

    @Mock // Creamos un "doble de riesgo" (simulacro) del repositorio
    private UsuarioRepository repository;

    @InjectMocks // Inyectamos el repositorio simulado dentro del servicio real
    private UsuarioService service;

    private Usuario usuarioPrueba;
    private Rol rolPrueba;

    // @BeforeEach hace que este método se ejecute ANTES de cada prueba
    // Lo usamos para tener datos frescos y limpios cada vez.
    @BeforeEach
    void setUp() {
        rolPrueba = new Rol(1, "CLIENTE");
        usuarioPrueba = new Usuario(1, "Juan", "juan@mail.com", "1234", "11111111-1", "987654321", rolPrueba);
    }

    @Test
    void testListar() {
        // 1. Preparación (Given): Le decimos al simulacro qué hacer
        when(repository.findAll()).thenReturn(Arrays.asList(usuarioPrueba));

        // 2. Ejecución (When): Llamamos a nuestro servicio real
        List<Usuario> resultado = service.listar();

        // 3. Verificación (Then): Afirmamos que el resultado es el esperado
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
    }

    @Test
    void testBuscarPorId_Exito() {
        // Le decimos al mock: "Cuando te busquen por el ID 1, devuelve el usuarioPrueba"
        when(repository.findById(1)).thenReturn(Optional.of(usuarioPrueba));

        Usuario resultado = service.buscarPorId(1);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
    }

    @Test
    void testBuscarPorId_NoEncontrado() {
        // Le decimos al mock: "Cuando te busquen por el ID 99, devuelve vacío"
        when(repository.findById(99)).thenReturn(Optional.empty());

        // Verificamos que al buscar el ID 99, el servicio lance una RuntimeException
        RuntimeException excepcion = assertThrows(RuntimeException.class, () -> {
            service.buscarPorId(99);
        });

        assertEquals("Usuario no encontrado", excepcion.getMessage());
    }

    @Test
    void testGuardar() {
        when(repository.save(any(Usuario.class))).thenReturn(usuarioPrueba);

        Usuario resultado = service.guardar(usuarioPrueba);

        assertNotNull(resultado);
        assertEquals("juan@mail.com", resultado.getEmail());
    }

    @Test
    void testActualizar() {
        // Para actualizar, primero el servicio busca por ID, así que mockeamos eso:
        when(repository.findById(1)).thenReturn(Optional.of(usuarioPrueba));
        // Luego mockeamos el guardado
        when(repository.save(any(Usuario.class))).thenReturn(usuarioPrueba);

        Usuario datosNuevos = new Usuario(null, "Juan Actualizado", "juan.nuevo@mail.com", "4321", "11111111-1", "123123123", rolPrueba);
        
        Usuario resultado = service.actualizar(1, datosNuevos);

        assertNotNull(resultado);
        // Verificamos que el repositorio intentó guardar una vez
        verify(repository, times(1)).save(any(Usuario.class));
    }

    @Test
    void testEliminar() {
        // Para eliminar, primero busca por ID
        when(repository.findById(1)).thenReturn(Optional.of(usuarioPrueba));
        // doNothing() se usa para métodos que devuelven 'void'
        doNothing().when(repository).deleteById(1);

        service.eliminar(1);

        // Verificamos que el método deleteById se haya llamado exactamente 1 vez
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void testObtenerDTO() {
        when(repository.findById(1)).thenReturn(Optional.of(usuarioPrueba));

        UsuarioDTO dto = service.obtenerDTO(1);

        assertNotNull(dto);
        assertEquals(1, dto.getId());
        assertEquals("Juan", dto.getNombre());
        assertEquals("CLIENTE", dto.getRolNombre()); // Validamos que aplane el Rol correctamente
    }
}