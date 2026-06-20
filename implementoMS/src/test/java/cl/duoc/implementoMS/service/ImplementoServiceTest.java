package cl.duoc.implementoMS.service;

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


import cl.duoc.implementoMS.model.Implemento;
import cl.duoc.implementoMS.repository.ImplementoRepository;

@ExtendWith(MockitoExtension.class)
public class ImplementoServiceTest {

    @Mock
    private ImplementoRepository implementoRepository; // repository simulado para pruebas unitarias

    @InjectMocks
    private ImplementoService implementoService; // service real, pero con el Mock del repo inyectado

    private Implemento implementoEjemplo;

    @BeforeEach
    void setUp() {

        implementoEjemplo = new Implemento();
        implementoEjemplo.setId(1);
        implementoEjemplo.setNombre("Pelota de Fútbol");
        implementoEjemplo.setDescripcion("Pelota oficial de cuero tamaño 5");
        implementoEjemplo.setStock(10);
        implementoEjemplo.setPrecioArriendo(2000.0);
    }

    // ============ LISTAR ============

    @Test
    void listar_devuelveListaDeImplementos() {

        // ARRANGE
        List<Implemento> listaEjemplo = List.of(implementoEjemplo);
        when(implementoRepository.findAll()).thenReturn(listaEjemplo);

        // ACT
        List<Implemento> resultado = implementoService.listar();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals("Pelota de Fútbol", resultado.get(0).getNombre());
    }

    // ============ LISTAR DISPONIBLES ============

    @Test
    void listarDisponibles_devuelveSoloConStock() {

        // ARRANGE
        List<Implemento> listaEjemplo = List.of(implementoEjemplo);
        when(implementoRepository.findByStockGreaterThan(0)).thenReturn(listaEjemplo);

        // ACT
        List<Implemento> resultado = implementoService.listarDisponibles();

        // ASSERT
        assertEquals(1, resultado.size());
        assertEquals(10, resultado.get(0).getStock());
    }

    // ============ BUSCAR POR ID ============

    @Test
    void buscarPorId_encontrado() {

        // ARRANGE
        Optional<Implemento> implementoOptional = Optional.of(implementoEjemplo);
        when(implementoRepository.findById(1)).thenReturn(implementoOptional);

        // ACT
        Implemento resultado = implementoService.buscarPorId(1);

        // ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Pelota de Fútbol", resultado.getNombre());
    }

    @Test
    void buscarPorId_noEncontrado() {

        // ARRANGE
        Optional<Implemento> optionalVacio = Optional.empty();
        when(implementoRepository.findById(99)).thenReturn(optionalVacio);

        // ACT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            implementoService.buscarPorId(99);
        });

        // ASSERT
        assertEquals("Implemento no encontrado", error.getMessage());
    }

    // ============ GUARDAR ============

    @Test
    void guardar_retornaImplementoGuardado() {

        // ARRANGE
        when(implementoRepository.save(implementoEjemplo)).thenReturn(implementoEjemplo);

        // ACT
        Implemento resultado = implementoService.guardar(implementoEjemplo);

        // ASSERT
        assertEquals("Pelota de Fútbol", resultado.getNombre());
        verify(implementoRepository, times(1)).save(implementoEjemplo);
    }

    // ============ ACTUALIZAR ============

    @Test
    void actualizar_actualizaCorrectamente() {

        // ARRANGE
        Implemento implementoNuevo = new Implemento();
        implementoNuevo.setNombre("Pelota Nueva");
        implementoNuevo.setDescripcion("Pelota tamaño 4");
        implementoNuevo.setStock(15);
        implementoNuevo.setPrecioArriendo(2500.0);

        when(implementoRepository.findById(1)).thenReturn(Optional.of(implementoEjemplo));
        when(implementoRepository.save(any(Implemento.class))).thenReturn(implementoEjemplo);

        // ACT
        Implemento resultado = implementoService.actualizar(1, implementoNuevo);

        // ASSERT
        assertEquals("Pelota Nueva", resultado.getNombre());
        assertEquals(15, resultado.getStock());
        verify(implementoRepository, times(1)).save(implementoEjemplo);
    }

    // ============ ELIMINAR ============

    @Test
    void eliminar_eliminaCorrectamente() {

        // ARRANGE
        when(implementoRepository.findById(1)).thenReturn(Optional.of(implementoEjemplo));

        // ACT
        implementoService.eliminar(1);

        // ASSERT
        verify(implementoRepository, times(1)).deleteById(1);
    }

    // ============ DESCONTAR STOCK ============

    @Test
    void descontarStock_descuentaCorrectamente() {

        // ARRANGE
        when(implementoRepository.findById(1)).thenReturn(Optional.of(implementoEjemplo));
        when(implementoRepository.save(any(Implemento.class))).thenReturn(implementoEjemplo);

        // ACT
        Implemento resultado = implementoService.descontarStock(1, 3);

        // ASSERT
        assertEquals(7, resultado.getStock());
        verify(implementoRepository, times(1)).save(implementoEjemplo);
    }

    @Test
    void descontarStock_stockInsuficiente() {

        // ARRANGE
        when(implementoRepository.findById(1)).thenReturn(Optional.of(implementoEjemplo));

        // ACT: el implemento tiene stock 10, pedimos descontar 20
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            implementoService.descontarStock(1, 20);
        });

        // ASSERT
        assertEquals("Stock insuficiente", error.getMessage());
        verify(implementoRepository, never()).save(any());
    }


    // ============ DEVOLVER STOCK ============

    @Test
    void devolverStock_devuelveCorrectamente() {

        // ARRANGE
        when(implementoRepository.findById(1)).thenReturn(Optional.of(implementoEjemplo));
        when(implementoRepository.save(any(Implemento.class))).thenReturn(implementoEjemplo);

        // ACT
        Implemento resultado = implementoService.devolverStock(1, 5);

        // ASSERT
        assertEquals(15, resultado.getStock());
        verify(implementoRepository, times(1)).save(implementoEjemplo);
    }

}