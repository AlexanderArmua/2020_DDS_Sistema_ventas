package Entidades.TipoEntidad;

import Domain.Entities.Entidades.TipoEntidad.Empresa;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EmpresaTest {

    @Test
    public void crear_empresa_promedio_invalido_falla_minimo() {
        Exception exception = new Exception();

        try {
            new Empresa(0, null, -1L);
        } catch (Exception ex) {
            exception = ex;
        }

        String expectedMessage = "El monto ingresado es inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void crear_empresa_promedio_invalido_falla_maximo() {
        Exception exception = new Exception();

        try {
            new Empresa(0, null, 755740001L);
        } catch (Exception ex) {
            exception = ex;
        }

        String expectedMessage = "El monto ingresado es inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
