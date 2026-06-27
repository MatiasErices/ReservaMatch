package cl.duoc.canchaMS.Service;

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

import cl.duoc.canchaMS.model.Cancha;
import cl.duoc.canchaMS.model.TipoCancha;
import cl.duoc.canchaMS.repository.CanchaRepository;
import cl.duoc.canchaMS.service.CanchaService;

@ExtendWith(MockitoExtension.class)
public class CanchaServiceTest {

    @Mock
    private CanchaRepository repository; // repository simulado para pruebas unitarias

    @InjectMocks
    private CanchaService canchaService; // service real, con los Mocks inyectados

    private Cancha canchaEjemplo;
    private TipoCancha tipoCanchaEjemplo;

    @BeforeEach
    void setUp() {

        tipoCanchaEjemplo = new TipoCancha();
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

    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaDeCanchas() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(repository.findAll()).thenReturn(listaEjemplo);

        // ACT
        List<Cancha> resultado = canchaService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Cancha Central", resultado.get(0).getNombre());
    }

    // ============ LISTAR DISPONIBLES ============

    @Test
    void listarDisponibles_devuelveSoloLasDisponibles() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(repository.findByDisponibleTrue()).thenReturn(listaEjemplo);

        // ACT
        List<Cancha> resultado = canchaService.listarDisponibles();

        // ASSERT
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getDisponible());
    }

    // ============ LISTAR POR SEDE ============

    @Test
    void listarPorSede_devuelveCanchasDeLaSede() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(repository.findBySedeId(1)).thenReturn(listaEjemplo);

        // ACT
        List<Cancha> resultado = canchaService.listarPorSede(1);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getSedeId());
    }

    @Test
    void listarPorSedeDisponibles_devuelveCanchasDisponiblesDeLaSede() {

        // ARRANGE
        List<Cancha> listaEjemplo = List.of(canchaEjemplo);
        when(repository.findBySedeIdAndDisponibleTrue(1)).thenReturn(listaEjemplo);

        // ACT
        List<Cancha> resultado = canchaService.listarPorSedeDisponibles(1);

        // ASSERT
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getDisponible());
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrada() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(canchaEjemplo));

        // ACT
        Cancha resultado = canchaService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Cancha Central", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrada() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            canchaService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Cancha no encontrada", error.getMessage());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_retornaCanchaGuardada() {

        // ARRANGE
        when(repository.save(canchaEjemplo)).thenReturn(canchaEjemplo);

        // ACT
        Cancha resultado = canchaService.guardar(canchaEjemplo);

        // ASSERT
        assertEquals("Cancha Central", resultado.getNombre());
        verify(repository, times(1)).save(canchaEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        TipoCancha tipoNuevo = new TipoCancha();
        tipoNuevo.setId(2);
        tipoNuevo.setNombre("Pádel");
        tipoNuevo.setDescripcion("Cancha de pádel");

        Cancha canchaNueva = new Cancha();
        canchaNueva.setNombre("Cancha Renovada");
        canchaNueva.setCapacidad(8);
        canchaNueva.setPrecioPorHora(20000.0);
        canchaNueva.setTipoCancha(tipoNuevo);
        canchaNueva.setSedeId(2);
        canchaNueva.setDisponible(false);

        when(repository.findById(1)).thenReturn(Optional.of(canchaEjemplo));
        when(repository.save(any(Cancha.class))).thenReturn(canchaEjemplo);

        // ACT
        Cancha resultado = canchaService.actualizar(1, canchaNueva);

        // ASSERT
        assertEquals("Cancha Renovada", resultado.getNombre());
        assertEquals(8, resultado.getCapacidad());
        assertEquals(20000.0, resultado.getPrecioPorHora());
        assertEquals(tipoNuevo, resultado.getTipoCancha());
        assertEquals(2, resultado.getSedeId());
        assertFalse(resultado.getDisponible());
        verify(repository, times(1)).save(canchaEjemplo);
    }

    @Test
    void actualizar_noEncontrada() {

        // ARRANGE
        Cancha canchaNueva = new Cancha();
        canchaNueva.setNombre("Cancha Renovada");

        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            canchaService.actualizar(99, canchaNueva);
        });

        // ASSERT
        assertEquals("Cancha no encontrada", error.getMessage());
        verify(repository, never()).save(any());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(canchaEjemplo));

        // ACT
        canchaService.eliminar(1);

        // ASSERT
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noEncontrada() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            canchaService.eliminar(99);
        });

        // ASSERT
        assertEquals("Cancha no encontrada", error.getMessage());
        verify(repository, never()).deleteById(any());
    }

    // ============ MARCAR NO DISPONIBLE ============

    @Test
    void marcarNoDisponible_marcaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(canchaEjemplo));
        when(repository.save(any(Cancha.class))).thenReturn(canchaEjemplo);

        // ACT
        Cancha resultado = canchaService.marcarNoDisponible(1);

        // ASSERT
        assertFalse(resultado.getDisponible());
        verify(repository, times(1)).save(canchaEjemplo);
    }

    @Test
    void marcarNoDisponible_noEncontrada() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            canchaService.marcarNoDisponible(99);
        });

        // ASSERT
        assertEquals("Cancha no encontrada", error.getMessage());
        verify(repository, never()).save(any());
    }

}
