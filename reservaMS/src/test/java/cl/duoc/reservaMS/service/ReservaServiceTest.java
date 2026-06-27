package cl.duoc.reservaMS.service;

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

import cl.duoc.reservaMS.client.CanchaClient;
import cl.duoc.reservaMS.client.HorarioClient;
import cl.duoc.reservaMS.client.UsuarioClient;
import cl.duoc.reservaMS.dto.CanchaDTO;
import cl.duoc.reservaMS.dto.HorarioBloqueDTO;
import cl.duoc.reservaMS.dto.SedeDTO;
import cl.duoc.reservaMS.dto.UsuarioDTO;
import cl.duoc.reservaMS.model.Reserva;
import cl.duoc.reservaMS.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

    @Mock
    private ReservaRepository repository; // repository simulado para pruebas unitarias

    @Mock
    private UsuarioClient usuarioClient; // client simulado, no llama realmente a UsuarioMS

    @Mock
    private CanchaClient canchaClient; // client simulado, no llama realmente a CanchaMS

    @Mock
    private HorarioClient horarioClient; // client simulado, no llama realmente a HorarioMS

    @InjectMocks
    private ReservaService reservaService; // service real, con los Mocks inyectados

    private Reserva reservaEjemplo;
    private UsuarioDTO usuarioEjemplo;
    private CanchaDTO canchaEjemplo;
    private HorarioBloqueDTO horarioEjemplo;

    @BeforeEach
    void setUp() {

        reservaEjemplo = new Reserva();
        reservaEjemplo.setId(1);
        reservaEjemplo.setUsuarioId(1);
        reservaEjemplo.setCanchaId(1);
        reservaEjemplo.setSedeId(1);
        reservaEjemplo.setHoraBloqueId(1);
        reservaEjemplo.setFechaReserva(new Date());
        reservaEjemplo.setEstado("PENDIENTE");
        reservaEjemplo.setMontoTotal(30000.0);

        usuarioEjemplo = new UsuarioDTO();
        usuarioEjemplo.setId(1);
        usuarioEjemplo.setNombre("Juan Pérez");

        SedeDTO sedeEjemplo = new SedeDTO();
        sedeEjemplo.setId(1);
        sedeEjemplo.setNombre("Sede Santiago Centro");

        canchaEjemplo = new CanchaDTO();
        canchaEjemplo.setId(1);
        canchaEjemplo.setNombre("Cancha 1");
        canchaEjemplo.setDisponible(true);
        canchaEjemplo.setPrecioPorHora(30000.0);
        canchaEjemplo.setSede(sedeEjemplo);

        horarioEjemplo = new HorarioBloqueDTO();
        horarioEjemplo.setId(1);
        horarioEjemplo.setDisponible(true);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaDeReservas() {

        // ARRANGE
        List<Reserva> listaEjemplo = List.of(reservaEjemplo);
        when(repository.findAll()).thenReturn(listaEjemplo);

        // ACT
        List<Reserva> resultado = reservaService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrado() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(reservaEjemplo));

        // ACT
        Reserva resultado = reservaService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals(1, resultado.getSedeId());
        assertEquals(1, resultado.getUsuarioId());
    }

    @Test
    void buscarPorId_noEncontrado() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Reserva no encontrada", error.getMessage());
    }

    // ============ LISTAR POR USUARIO ============

    @Test
    void listarPorUsuario_devuelveReservasDelUsuario() {

        // ARRANGE
        List<Reserva> listaEjemplo = List.of(reservaEjemplo);
        when(repository.findByUsuarioId(1)).thenReturn(listaEjemplo);

        // ACT
        List<Reserva> resultado = reservaService.listarPorUsuario(1);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getUsuarioId());
    }

    // ============ LISTAR POR ESTADO ============

    @Test
    void listarPorEstado_devuelveReservasConEseEstado() {

        // ARRANGE
        List<Reserva> listaEjemplo = List.of(reservaEjemplo);
        when(repository.findByEstado("PENDIENTE")).thenReturn(listaEjemplo);

        // ACT
        List<Reserva> resultado = reservaService.listarPorEstado("PENDIENTE");

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("PENDIENTE", resultado.get(0).getEstado());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(reservaEjemplo));

        // ACT
        reservaService.eliminar(1);

        // ASSERT
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_reservaNoExiste() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.eliminar(99);
        });

        // ASSERT
        assertEquals("Reserva no encontrada", error.getMessage());
        verify(repository, never()).deleteById(any());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_creaReservaCorrectamente() {

        // ARRANGE
        Reserva reservaNueva = new Reserva();
        reservaNueva.setUsuarioId(1);
        reservaNueva.setCanchaId(1);
        reservaNueva.setHoraBloqueId(1);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioEjemplo);
        when(canchaClient.obtenerCancha(1)).thenReturn(canchaEjemplo);
        when(horarioClient.obtenerHorario(1)).thenReturn(horarioEjemplo);
        when(repository.save(any(Reserva.class))).thenReturn(reservaEjemplo);

        // ACT
        Reserva resultado = reservaService.guardar(reservaNueva);

        // ASSERT
        assertEquals("PENDIENTE", resultado.getEstado());
        assertEquals(1, reservaNueva.getSedeId()); // se asignó desde la cancha
        assertEquals(30000.0, reservaNueva.getMontoTotal());
        verify(repository, times(1)).save(any(Reserva.class));
    }

    
    @Test
    void guardar_usuarioNoEncontrado() {

        // ARRANGE
        Reserva reservaNueva = new Reserva();
        reservaNueva.setUsuarioId(99);

        when(usuarioClient.obtenerUsuario(99)).thenReturn(null);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaNueva);
        });

        // ASSERT
        assertEquals("Usuario no encontrado", error.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void guardar_canchaNoDisponible() {

        // ARRANGE
        Reserva reservaNueva = new Reserva();
        reservaNueva.setUsuarioId(1);
        reservaNueva.setCanchaId(1);

        CanchaDTO canchaNoDisponible = new CanchaDTO();
        canchaNoDisponible.setDisponible(false);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioEjemplo);
        when(canchaClient.obtenerCancha(1)).thenReturn(canchaNoDisponible);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaNueva);
        });

        // ASSERT
        assertEquals("Cancha no disponible", error.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void guardar_horarioNoDisponible() {

        // ARRANGE
        Reserva reservaNueva = new Reserva();
        reservaNueva.setUsuarioId(1);
        reservaNueva.setCanchaId(1);
        reservaNueva.setHoraBloqueId(1);

        HorarioBloqueDTO horarioNoDisponible = new HorarioBloqueDTO();
        horarioNoDisponible.setDisponible(false);

        when(usuarioClient.obtenerUsuario(1)).thenReturn(usuarioEjemplo);
        when(canchaClient.obtenerCancha(1)).thenReturn(canchaEjemplo);
        when(horarioClient.obtenerHorario(1)).thenReturn(horarioNoDisponible);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.guardar(reservaNueva);
        });

        // ASSERT
        assertEquals("Horario no disponible", error.getMessage());
        verify(repository, never()).save(any());
    }

    // ============ CONFIRMAR ============

    @Test
    void confirmar_confirmaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(reservaEjemplo));
        when(repository.save(any(Reserva.class))).thenReturn(reservaEjemplo);

        // ACT
        Reserva resultado = reservaService.confirmar(1);

        // ASSERT
        assertEquals("CONFIRMADA", resultado.getEstado());
        verify(canchaClient, times(1)).marcarNoDisponible(1);
        verify(horarioClient, times(1)).marcarNoDisponible(1);
    }

    @Test
    void confirmar_reservaNoExiste() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.confirmar(99);
        });

        // ASSERT
        assertEquals("Reserva no encontrada", error.getMessage());
    }

    // ============ CANCELAR ============

    @Test
    void cancelar_cancelaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(reservaEjemplo));
        when(repository.save(any(Reserva.class))).thenReturn(reservaEjemplo);

        // ACT
        Reserva resultado = reservaService.cancelar(1);

        // ASSERT
        assertEquals("CANCELADA", resultado.getEstado());
    }

    @Test
    void cancelar_reservaNoExiste() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            reservaService.cancelar(99);
        });

        // ASSERT
        assertEquals("Reserva no encontrada", error.getMessage());
    }


}