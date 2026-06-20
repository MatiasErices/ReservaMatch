package cl.duoc.arriendoImplementoMS.Service;

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

import cl.duoc.arriendoImplementoMS.client.ImplementoClient;
import cl.duoc.arriendoImplementoMS.client.ReservaClient;
import cl.duoc.arriendoImplementoMS.dto.ArriendoImplementoDTO;
import cl.duoc.arriendoImplementoMS.dto.ImplementoDTO;
import cl.duoc.arriendoImplementoMS.dto.ReservaDTO;
import cl.duoc.arriendoImplementoMS.model.ArriendoImplemento;
import cl.duoc.arriendoImplementoMS.repository.ArriendoImplementoRepository;
import cl.duoc.arriendoImplementoMS.service.ArriendoImplementoService;

@ExtendWith(MockitoExtension.class)
public class ArriendoImplementoServiceTest {

    @Mock
    private ArriendoImplementoRepository repository; // repository simulado para pruebas unitarias

    @Mock
    private ReservaClient reservaClient; // cliente Feign simulado hacia reservaMS

    @Mock
    private ImplementoClient implementoClient; // cliente Feign simulado hacia implementoMS

    @InjectMocks
    private ArriendoImplementoService arriendoImplementoService; // service real, con los Mocks inyectados

    private ArriendoImplemento arriendoEjemplo;
    private ImplementoDTO implementoEjemplo;
    private ReservaDTO reservaConfirmadaEjemplo;

    @BeforeEach
    void setUp() {

        arriendoEjemplo = new ArriendoImplemento();
        arriendoEjemplo.setId(1);
        arriendoEjemplo.setReservaId(1);
        arriendoEjemplo.setImplementoId(1);
        arriendoEjemplo.setCantidad(3);
        arriendoEjemplo.setFechaArriendo(new Date(1_700_000_000_000L));
        arriendoEjemplo.setMontoTotal(6000.0);

        implementoEjemplo = new ImplementoDTO(1, "Pelota de Fútbol", "Pelota oficial talla 5", 10, 2000.0);

        reservaConfirmadaEjemplo = new ReservaDTO(1, new Date(1_699_000_000_000L), "CONFIRMADA", 5000.0, null, null, null);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaDeArriendos() {

        // ARRANGE
        List<ArriendoImplemento> listaEjemplo = List.of(arriendoEjemplo);
        when(repository.findAll()).thenReturn(listaEjemplo);

        // ACT
        List<ArriendoImplemento> resultado = arriendoImplementoService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(3, resultado.get(0).getCantidad());
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrado() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(arriendoEjemplo));

        // ACT
        ArriendoImplemento resultado = arriendoImplementoService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals(3, resultado.getCantidad());
    }

    @Test
    void buscarPorId_noEncontrado() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Arriendo no encontrado", error.getMessage());
    }

    // ============ LISTAR POR RESERVA / POR IMPLEMENTO ============

    @Test
    void listarPorReserva_devuelveArriendosDeLaReserva() {

        // ARRANGE
        List<ArriendoImplemento> listaEjemplo = List.of(arriendoEjemplo);
        when(repository.findByReservaId(1)).thenReturn(listaEjemplo);

        // ACT
        List<ArriendoImplemento> resultado = arriendoImplementoService.listarPorReserva(1);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getReservaId());
    }

    @Test
    void listarPorImplemento_devuelveArriendosDelImplemento() {

        // ARRANGE
        List<ArriendoImplemento> listaEjemplo = List.of(arriendoEjemplo);
        when(repository.findByImplementoId(1)).thenReturn(listaEjemplo);

        // ACT
        List<ArriendoImplemento> resultado = arriendoImplementoService.listarPorImplemento(1);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getImplementoId());
    }

    // ============ CREAR (reglas de negocio) ============

    @Test
    void crear_creaArriendoCorrectamente() {

        // ARRANGE
        ArriendoImplemento arriendoNuevo = new ArriendoImplemento();
        arriendoNuevo.setReservaId(1);
        arriendoNuevo.setImplementoId(1);
        arriendoNuevo.setCantidad(3);

        when(reservaClient.obtenerReserva(1)).thenReturn(reservaConfirmadaEjemplo);
        when(implementoClient.obtenerImplemento(1)).thenReturn(implementoEjemplo);
        when(repository.save(arriendoNuevo)).thenReturn(arriendoNuevo);

        // ACT
        ArriendoImplemento resultado = arriendoImplementoService.crear(arriendoNuevo);

        // ASSERT
        assertEquals(6000.0, resultado.getMontoTotal()); // precioArriendo (2000.0) x cantidad (3)
        assertNotNull(resultado.getFechaArriendo());
        verify(repository, times(1)).save(arriendoNuevo);
        verify(implementoClient, times(1)).descontarStock(1, 3);
    }

    @Test
    void crear_reservaNoEncontrada() {

        // ARRANGE
        ArriendoImplemento arriendoNuevo = new ArriendoImplemento();
        arriendoNuevo.setReservaId(99);
        arriendoNuevo.setImplementoId(1);
        arriendoNuevo.setCantidad(2);

        when(reservaClient.obtenerReserva(99)).thenReturn(null);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.crear(arriendoNuevo);
        });

        // ASSERT
        assertEquals("Reserva no encontrada", error.getMessage());
        verify(implementoClient, never()).obtenerImplemento(any());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_reservaNoConfirmada() {

        // ARRANGE
        ReservaDTO reservaPendiente = new ReservaDTO(1, new Date(1_699_000_000_000L), "PENDIENTE", 5000.0, null, null, null);

        ArriendoImplemento arriendoNuevo = new ArriendoImplemento();
        arriendoNuevo.setReservaId(1);
        arriendoNuevo.setImplementoId(1);
        arriendoNuevo.setCantidad(2);

        when(reservaClient.obtenerReserva(1)).thenReturn(reservaPendiente);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.crear(arriendoNuevo);
        });

        // ASSERT
        assertEquals("La reserva debe estar CONFIRMADA para arrendar implementos", error.getMessage());
        verify(implementoClient, never()).obtenerImplemento(any());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_implementoNoEncontrado() {

        // ARRANGE
        ArriendoImplemento arriendoNuevo = new ArriendoImplemento();
        arriendoNuevo.setReservaId(1);
        arriendoNuevo.setImplementoId(99);
        arriendoNuevo.setCantidad(2);

        when(reservaClient.obtenerReserva(1)).thenReturn(reservaConfirmadaEjemplo);
        when(implementoClient.obtenerImplemento(99)).thenReturn(null);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.crear(arriendoNuevo);
        });

        // ASSERT
        assertEquals("Implemento no encontrado", error.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void crear_stockInsuficiente() {

        // ARRANGE: el implemento tiene stock 10, pedimos arrendar 50
        ArriendoImplemento arriendoNuevo = new ArriendoImplemento();
        arriendoNuevo.setReservaId(1);
        arriendoNuevo.setImplementoId(1);
        arriendoNuevo.setCantidad(50);

        when(reservaClient.obtenerReserva(1)).thenReturn(reservaConfirmadaEjemplo);
        when(implementoClient.obtenerImplemento(1)).thenReturn(implementoEjemplo);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.crear(arriendoNuevo);
        });

        // ASSERT
        assertEquals("Stock insuficiente", error.getMessage());
        verify(repository, never()).save(any());
        verify(implementoClient, never()).descontarStock(any(), any());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamenteYDevuelveStock() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(arriendoEjemplo));

        // ACT
        arriendoImplementoService.eliminar(1);

        // ASSERT
        verify(implementoClient, times(1)).devolverStock(1, 3); // implementoId=1, cantidad=3
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_noEncontrado() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.eliminar(99);
        });

        // ASSERT
        assertEquals("Arriendo no encontrado", error.getMessage());
        verify(implementoClient, never()).devolverStock(any(), any());
        verify(repository, never()).deleteById(any());
    }

    // ============ OBTENER DTO ============

    @Test
    void obtenerDTO_devuelveDTOCorrecto() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(arriendoEjemplo));
        when(implementoClient.obtenerImplemento(1)).thenReturn(implementoEjemplo);
        when(reservaClient.obtenerReserva(1)).thenReturn(reservaConfirmadaEjemplo);

        // ACT
        ArriendoImplementoDTO resultado = arriendoImplementoService.obtenerDTO(1);

        // ASSERT
        assertEquals(arriendoEjemplo.getId(), resultado.getId());
        assertEquals(arriendoEjemplo.getCantidad(), resultado.getCantidad());
        assertEquals(implementoEjemplo, resultado.getImplemento());
        assertEquals(reservaConfirmadaEjemplo, resultado.getReserva());
    }

    @Test
    void obtenerDTO_noEncontrado() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            arriendoImplementoService.obtenerDTO(99);
        });

        // ASSERT
        assertEquals("Arriendo no encontrado", error.getMessage());
        verify(implementoClient, never()).obtenerImplemento(any());
        verify(reservaClient, never()).obtenerReserva(any());
    }
}
