package Operaciones;

import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.CriterioMenorValor;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.ICriterioDeSeleccion;
import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Presupuestos.Presupuesto;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertTrue;

public class OperacionEgresoTest {

    @Test
    public void selecciono_el_presupuesto_mas_barato_para_operacion() {
        DOOperacionEgreso doOperacionEgreso = new DOOperacionEgreso(null, null, 0.0f, null, null, null,false);
        OperacionEgreso operacionEgreso = new OperacionEgreso(doOperacionEgreso);

        ICriterioDeSeleccion criterioDeSeleccion = new CriterioMenorValor();

        operacionEgreso.setCriterioSeleccionPresupuesto(criterioDeSeleccion);

        LocalDateTime dateTime1=LocalDateTime.of(2018, 10, 10, 11, 25);
        LocalDateTime dateTime2=LocalDateTime.of(2018, 11, 10, 11, 25);
        LocalDateTime dateTime3=LocalDateTime.of(2018, 10, 16, 11, 25);

        Presupuesto presupuesto1 = new Presupuesto(null, null, 500, operacionEgreso, null,  dateTime1,null);
        Presupuesto presupuesto2 = new Presupuesto(null, null, 1000, operacionEgreso, null, dateTime2,null);
        Presupuesto presupuesto3 = new Presupuesto(null, null, 2500, operacionEgreso, null, dateTime3,null);

        operacionEgreso.cargarPresupuesto(presupuesto1);
        operacionEgreso.cargarPresupuesto(presupuesto2);
        operacionEgreso.cargarPresupuesto(presupuesto3);

        operacionEgreso.setPresupuestoElegido(presupuesto1);

        assertTrue(operacionEgreso.cumpleCriterioSeleccion());
    }
}
