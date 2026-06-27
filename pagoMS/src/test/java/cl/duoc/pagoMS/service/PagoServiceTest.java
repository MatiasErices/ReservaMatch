package cl.duoc.pagoMS.service;

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

import cl.duoc.pagoMS.client.ReservaClient;
import cl.duoc.pagoMS.dto.ReservaDTO;
import cl.duoc.pagoMS.model.Pago;
import cl.duoc.pagoMS.model.TipoPago;
import cl.duoc.pagoMS.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @Mock
    private PagoRepository repository; // repository simulado para pruebas unitarias

    @Mock
    private ReservaClient reservaClient; // client simulado, no llama realmente a ReservaMS

    @InjectMocks
    private PagoService pagoService; // service real, con los Mocks inyectados

    private Pago pagoEjemplo;
    private TipoPago tipoPagoEjemplo;
    private ReservaDTO reservaPendienteEjemplo;

    @BeforeEach
    void setUp() {

        tipoPagoEjemplo = new TipoPago();
        tipoPagoEjemplo.setId(1);
        tipoPagoEjemplo.setNombre("Débito");

        pagoEjemplo = new Pago();
        pagoEjemplo.setId(1);
        pagoEjemplo.setReservaId(1);
        pagoEjemplo.setMonto(30000.0);
        pagoEjemplo.setFechaPago(new Date());
        pagoEjemplo.setEstado("COMPLETADO");
        pagoEjemplo.setTipoPago(tipoPagoEjemplo);

        reservaPendienteEjemplo = new ReservaDTO();
        reservaPendienteEjemplo.setId(1);
        reservaPendienteEjemplo.setEstado("PENDIENTE");
        reservaPendienteEjemplo.setMontoTotal(30000.0);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaDePagos() {

        // ARRANGE
        List<Pago> listaEjemplo = List.of(pagoEjemplo);
        when(repository.findAll()).thenReturn(listaEjemplo);

        // ACT
        List<Pago> resultado = pagoService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(30000.0, resultado.get(0).getMonto());
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrado() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(pagoEjemplo));

        // ACT
        Pago resultado = pagoService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("COMPLETADO", resultado.getEstado());
    }

    @Test
    void buscarPorId_noEncontrado() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pagoService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Pago no encontrado", error.getMessage());
    }

    // ============ LISTAR POR RESERVA ============

    @Test
    void listarPorReserva_devuelvePagosDeLaReserva() {

        // ARRANGE
        List<Pago> listaEjemplo = List.of(pagoEjemplo);
        when(repository.findByReservaId(1)).thenReturn(listaEjemplo);

        // ACT
        List<Pago> resultado = pagoService.listarPorReserva(1);

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(1, resultado.get(0).getReservaId());
    }

    // ============ LISTAR POR ESTADO ============

    @Test
    void listarPorEstado_devuelvePagosConEseEstado() {

        // ARRANGE
        List<Pago> listaEjemplo = List.of(pagoEjemplo);
        when(repository.findByEstado("COMPLETADO")).thenReturn(listaEjemplo);

        // ACT
        List<Pago> resultado = pagoService.listarPorEstado("COMPLETADO");

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("COMPLETADO", resultado.get(0).getEstado());
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        when(repository.findById(1)).thenReturn(Optional.of(pagoEjemplo));

        // ACT
        pagoService.eliminar(1);

        // ASSERT
        verify(repository, times(1)).deleteById(1);
    }

    @Test
    void eliminar_pagoNoExiste() {

        // ARRANGE
        when(repository.findById(99)).thenReturn(Optional.empty());

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pagoService.eliminar(99);
        });

        // ASSERT
        assertEquals("Pago no encontrado", error.getMessage());
        verify(repository, never()).deleteById(any());
    }

    // ============ PROCESAR ============

    @Test
    void procesar_procesaCorrectamente() {

        // ARRANGE
        Pago pagoNuevo = new Pago();
        pagoNuevo.setReservaId(1);
        pagoNuevo.setTipoPago(tipoPagoEjemplo);

        when(reservaClient.obtenerReserva(1)).thenReturn(reservaPendienteEjemplo);
        when(repository.save(any(Pago.class))).thenReturn(pagoEjemplo);

        // ACT
        Pago resultado = pagoService.procesar(pagoNuevo);

        // ASSERT
        assertEquals("COMPLETADO", resultado.getEstado());
        verify(reservaClient, times(1)).confirmarReserva(1);
        verify(repository, times(1)).save(any(Pago.class));
    }

    @Test
    void procesar_reservaNoEncontrada() {

        // ARRANGE
        Pago pagoNuevo = new Pago();
        pagoNuevo.setReservaId(99);

        when(reservaClient.obtenerReserva(99)).thenReturn(null);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pagoService.procesar(pagoNuevo);
        });

        // ASSERT
        assertEquals("Reserva no encontrada", error.getMessage());
        verify(repository, never()).save(any());
    }

    @Test
    void procesar_reservaNoPendiente() {

        // ARRANGE
        Pago pagoNuevo = new Pago();
        pagoNuevo.setReservaId(1);

        ReservaDTO reservaConfirmada = new ReservaDTO();
        reservaConfirmada.setId(1);
        reservaConfirmada.setEstado("CONFIRMADA");

        when(reservaClient.obtenerReserva(1)).thenReturn(reservaConfirmada);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            pagoService.procesar(pagoNuevo);
        });

        // ASSERT
        assertEquals("La reserva no está en estado PENDIENTE", error.getMessage());
        verify(repository, never()).save(any());
    }


}