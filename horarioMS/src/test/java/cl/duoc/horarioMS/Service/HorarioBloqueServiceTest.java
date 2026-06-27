package cl.duoc.horarioMS.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.horarioMS.model.HorarioBloque;
import cl.duoc.horarioMS.repository.HorarioBloqueRepository;
import cl.duoc.horarioMS.service.HorarioBloqueService;

@ExtendWith(MockitoExtension.class)
public class HorarioBloqueServiceTest {

    @Mock
    private HorarioBloqueRepository horarioBloqueRepository; // repository simulado para pruebas unitarias

    @InjectMocks
    private HorarioBloqueService horarioBloqueService; // service real, pero con el Mock del repo inyectado

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
    void listar_devuelveListaDeBloques() {

        // ARRANGE
        List<HorarioBloque> listaEjemplo = List.of(bloqueEjemplo);
        when(horarioBloqueRepository.findAll()).thenReturn(listaEjemplo);

        // ACT
        List<HorarioBloque> resultado = horarioBloqueService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("08:00", resultado.get(0).getHoraInicio());
    }

    // ============ LISTAR DISPONIBLES ============

    @Test
    void listarDisponibles_devuelveSoloLosDisponibles() {

        // ARRANGE
        List<HorarioBloque> listaEjemplo = List.of(bloqueEjemplo);
        when(horarioBloqueRepository.findByDisponibleTrue()).thenReturn(listaEjemplo);

        // ACT
        List<HorarioBloque> resultado = horarioBloqueService.listarDisponibles();

        // ASSERT
        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getDisponible());
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrado() {

        // ARRANGE
        when(horarioBloqueRepository.findById(1)).thenReturn(Optional.of(bloqueEjemplo));

        // ACT
        HorarioBloque resultado = horarioBloqueService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("08:00", resultado.getHoraInicio());
    }

    @Test
    void buscarPorId_noEncontrado() {

        // ARRANGE
        when(horarioBloqueRepository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            horarioBloqueService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Bloque horario no encontrado", error.getMessage());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_retornaBloqueGuardado() {

        // ARRANGE
        when(horarioBloqueRepository.save(bloqueEjemplo)).thenReturn(bloqueEjemplo);

        // ACT
        HorarioBloque resultado = horarioBloqueService.guardar(bloqueEjemplo);

        // ASSERT
        assertEquals("08:00", resultado.getHoraInicio());
        verify(horarioBloqueRepository, times(1)).save(bloqueEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        Date fechaNueva = new Date(); // un día después de fechaEjemplo

        HorarioBloque bloqueNuevo = new HorarioBloque();
        bloqueNuevo.setFecha(fechaNueva);
        bloqueNuevo.setHoraInicio("10:00");
        bloqueNuevo.setHoraFin("11:00");
        bloqueNuevo.setDisponible(false);

        when(horarioBloqueRepository.findById(1)).thenReturn(Optional.of(bloqueEjemplo));
        when(horarioBloqueRepository.save(any(HorarioBloque.class))).thenReturn(bloqueEjemplo);

        // ACT
        HorarioBloque resultado = horarioBloqueService.actualizar(1, bloqueNuevo);

        // ASSERT
        assertEquals(fechaNueva, resultado.getFecha());
        assertEquals("10:00", resultado.getHoraInicio());
        assertEquals("11:00", resultado.getHoraFin());
        assertFalse(resultado.getDisponible());
        verify(horarioBloqueRepository, times(1)).save(bloqueEjemplo);
    }

    @Test
    void actualizar_noEncontrado() {

        // ARRANGE
        HorarioBloque bloqueNuevo = new HorarioBloque();
        bloqueNuevo.setHoraInicio("10:00");

        when(horarioBloqueRepository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            horarioBloqueService.actualizar(99, bloqueNuevo);
        });

        // ASSERT
        assertEquals("Bloque horario no encontrado", error.getMessage());
        verify(horarioBloqueRepository, never()).save(any());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        when(horarioBloqueRepository.findById(1)).thenReturn(Optional.of(bloqueEjemplo));

        // ACT
        horarioBloqueService.eliminar(1);

        // ASSERT
        verify(horarioBloqueRepository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        when(horarioBloqueRepository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            horarioBloqueService.eliminar(99);
        });

        // ASSERT
        assertEquals("Bloque horario no encontrado", error.getMessage());
        verify(horarioBloqueRepository, never()).deleteById(any());
    }


    // ============ MARCAR NO DISPONIBLE ============

    @Test
    void marcarNoDisponible_marcaCorrectamente() {

        // ARRANGE
        when(horarioBloqueRepository.findById(1)).thenReturn(Optional.of(bloqueEjemplo));
        when(horarioBloqueRepository.save(any(HorarioBloque.class))).thenReturn(bloqueEjemplo);

        // ACT
        HorarioBloque resultado = horarioBloqueService.marcarNoDisponible(1);

        // ASSERT
        assertFalse(resultado.getDisponible());
        verify(horarioBloqueRepository, times(1)).save(bloqueEjemplo);
    }

    @Test
    void marcarNoDisponible_noEncontrado() {

        // ARRANGE
        when(horarioBloqueRepository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            horarioBloqueService.marcarNoDisponible(99);
        });

        // ASSERT
        assertEquals("Bloque horario no encontrado", error.getMessage());
        verify(horarioBloqueRepository, never()).save(any());
    }
}