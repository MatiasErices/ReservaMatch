package cl.duoc.sedeMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.duoc.sedeMS.model.Sede;
import cl.duoc.sedeMS.repository.SedeRepository;

@ExtendWith(MockitoExtension.class)
public class SedeServiceTest {

    @Mock
    private SedeRepository service; //repository simulado para pruebas unitarias

    @InjectMocks
    private SedeService sedeService; //sede service real, pero con el Mock del repo inyectado para pruebas unitarias

    private Sede sedeEjemplo;


    @BeforeEach
    void setUp(){

        sedeEjemplo = new Sede();
        sedeEjemplo.setId(1);
        sedeEjemplo.setNombre("Sede Central");
        sedeEjemplo.setDireccion("Av. Principal 123");
        sedeEjemplo.setComuna("Santiago");
        sedeEjemplo.setTelefono("123456789");

    }

    @Test
    void buscarPorId_encontrado(){

        //ARRANGE: preparamos la prueba
        Optional<Sede> sedeOptional = Optional.of(sedeEjemplo);
        when(service.findById(1)).thenReturn(sedeOptional);



        //ACT: llamamos al método real del service
        Sede resultado = sedeService.buscarPorId(1);

        //ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Sede Central", resultado.getNombre());


    }

    @Test
    void buscarPorId_noEncontrado(){

        //ARRANGE:
        Optional<Sede> optionalVacio = Optional.empty();
        when(service.findById(99)).thenReturn(optionalVacio);

        //ACT: ejecutamos el metodo buscarPorId(99) deberia devolver un error,
        // asi que capturamos el error para analizarlo
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
        sedeService.buscarPorId(99);
    });


    //ASSERT:
    assertEquals("Sede no encontrada", error.getMessage());

    }


    @Test
    void eliminarSede(){

        //ARRANGE: el repo devolvera la sedeEjemplo cuando se busque por id 1
        Optional<Sede> sedeOptional = Optional.of(sedeEjemplo);
        when(service.findById(1)).thenReturn(sedeOptional);

        //ACT: ejecutamos el metodo eliminar(1) del service, que internamente llamara al repo.deleteById(1)
        sedeService.eliminar(1);

        //ASSERT: verificamos que el metodo deleteById(1) del repo haya sido llamado exactamente una vez
        verify(service, times(1)).deleteById(1);

    }

    @Test
    void eliminarSede_noEncontrada(){

        //ARRANGE: el repo devolvera un Optional vacio al buscar por id 99
        Optional<Sede> optionalVacio = Optional.empty();
        when(service.findById(99)).thenReturn(optionalVacio);

        //ACT: ejecutamos el metodo eliminar(99) del service, que deberia lanzar un error porque la sede no existe
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            sedeService.eliminar(99);
        });

        //ASSERT: verificamos que el mensaje del error sea "Sede no encontrada"
        assertEquals("Sede no encontrada", error.getMessage());

    }

    @Test
    void listarSede(){

        //ARRANGE: preparamos una lista de sedes simulada
        List<Sede> sedesSimuladas = List.of(sedeEjemplo);
        when(service.findAll()).thenReturn(sedesSimuladas);

        //ACT: llamamos al método listar() del service, que internamente llamará al repo.findAll()
        List<Sede> resultado = sedeService.listar();

        //ASSERT: verificamos que el resultado sea igual a la lista simulada
        assertEquals(sedesSimuladas, resultado);

    }

    @Test
    void guardarSede(){

        Optional<Sede> sedeOptional = Optional.empty();
        assertFalse(sedeOptional.isPresent());
        when(service.save(sedeEjemplo)).thenReturn(sedeEjemplo);

        Sede resultado = sedeService.guardar(sedeEjemplo);

        assertEquals(sedeEjemplo, resultado);
    }

    @Test
    void actualizarSede() {
    // ARRANGE
    Sede sedeOriginal = new Sede(1, "Sede Original", "Dirección 123", "Providencia", "123456789");
    Sede sedeActualizada = new Sede(1, "Sede Actualizada", "Nueva Dirección 456", "Providencia", "987654321");

    when(service.findById(1)).thenReturn(Optional.of(sedeOriginal));
    when(service.save(any(Sede.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // ACT
    Sede resultado = sedeService.actualizar(1, sedeActualizada);

    // ASSERT
    assertEquals("Sede Actualizada", resultado.getNombre());
    assertEquals("Nueva Dirección 456", resultado.getDireccion());
    assertEquals("Providencia", resultado.getComuna());
    assertEquals("987654321", resultado.getTelefono());
}

    @Test
    void actualizarSede_noEncontrada() {
        // ARRANGE
        when(service.findById(99)).thenReturn(Optional.empty());

        // ACT & ASSERT
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
            sedeService.actualizar(99, new Sede());
        });

        assertEquals("Sede no encontrada", error.getMessage());
    }

    @Test
    void listarPorComuna(){

        List<Sede> sedesEnSantiago = List.of(sedeEjemplo);
        when(service.findByComuna("Santiago")).thenReturn(sedesEnSantiago);

        List<Sede> resultado = sedeService.listarPorComuna("Santiago");

        assertEquals(sedesEnSantiago, resultado);

    }


}
