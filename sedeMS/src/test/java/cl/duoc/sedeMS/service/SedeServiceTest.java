package cl.duoc.sedeMS.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private SedeRepository repository; //repository simulado para pruebas unitarias

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
        when(repository.findById(1)).thenReturn(sedeOptional);



        //ACT: llamamos al método real del service
        Sede resultado = sedeService.buscarPorId(1);

        //ASSERT
        assertEquals(1, resultado.getId());
        assertEquals("Sede Central", resultado.getNombre());


    }

    @Test
    void buscarPorId_noEncontrado(){

        //ARRANGE: el repo devolvera un Optional vacio
        Optional<Sede> optionalVacio = Optional.empty();
        when(repository.findById(99)).thenReturn(optionalVacio);

        //ACT: ejecutamos el metodo buscarPorId(99) deberia devolver un error, 
        // asi que capturamos el error para analizarlo
        RuntimeException error = assertThrows(RuntimeException.class, () -> {
        sedeService.buscarPorId(99);
    });


    //ASSERT:
    assertEquals("Sede no encontrada", error.getMessage());

    }



}
