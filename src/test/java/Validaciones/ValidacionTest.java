package Validaciones;

import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.Item.TipoItem;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.CriterioMenorValor;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.ICriterioDeSeleccion;
import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Presupuestos.ItemPresupuesto;
import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Validaciones.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class ValidacionTest {

    private ResultadoValidacion resultadoValidacion;

    @Before
    public void before() {
        resultadoValidacion = new ResultadoValidacion(this.getOperacionEgresoTest());
    }

    @Test
    public void es_correcta_la_cantidad_de_presupuestos_en_operacion_egreso() {
        ValidacionCantPresupuestos validacionCantPresupuestos = new ValidacionCantPresupuestos("Mensaje OK", "Mensaje Error", "Validacion Cantidad de Presupuestos");

        resultadoValidacion.getOperacionEgreso().setCantidadPresupuestoRequeridos(3);

        validacionCantPresupuestos.validarcriterios(resultadoValidacion);

        assertTrue(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void es_incorrecta_la_cantidad_de_presupuestos_en_operacion_egreso() {
        ValidacionCantPresupuestos validacionCantPresupuestos = new ValidacionCantPresupuestos("Mensaje OK", "Mensaje Error", "Validacion Cantidad de Presupuestos");

        resultadoValidacion.getOperacionEgreso().setCantidadPresupuestoRequeridos(2);

        validacionCantPresupuestos.validarcriterios(resultadoValidacion);

        assertFalse(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void es_correcto_el_criterio_de_seleccion_del_operacion_egreso() {
        ValidacionCriterioSeleccion validacionCriterioSeleccion = new ValidacionCriterioSeleccion("Mensaje OK", "Mensaje Error", " Validacion Criterio de seleccion");

        resultadoValidacion.getOperacionEgreso().setPresupuestoElegido(
                resultadoValidacion.getOperacionEgreso().presupuestoMenorValor()
        );

        validacionCriterioSeleccion.validarcriterios(resultadoValidacion);

        assertTrue(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void es_incorrecto_el_criterio_de_seleccion_del_operacion_egreso() {
        ValidacionCriterioSeleccion validacionCriterioSeleccion = new ValidacionCriterioSeleccion("Mensaje OK", "Mensaje Error", " Validacion Criterio de seleccion");

        resultadoValidacion.getOperacionEgreso().setPresupuestoElegido(
                resultadoValidacion.getOperacionEgreso().getPresupuestos().get(2)
        );

        validacionCriterioSeleccion.validarcriterios(resultadoValidacion);

        assertFalse(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void es_correcta_la_seleccion_items_del_presupuesto_coinciden_totales_y_items() {
        ValidacionUsoPresupuesto validacionUsoPresupuesto = new ValidacionUsoPresupuesto("Mensaje OK", "Mensaje Error", " Validacion Uso de Presupuesto");

        OperacionEgreso operacionEgreso = resultadoValidacion.getOperacionEgreso();

        Presupuesto presupuestoElegido = operacionEgreso.presupuestoMenorValor();
        operacionEgreso.setPresupuestoElegido(presupuestoElegido);
        presupuestoElegido.setValorTotal(500);
        operacionEgreso.setValorTotal(500);

        operacionEgreso.setItems(this.getItemsTest());
        presupuestoElegido.setItems(this.getItemsPresupuestoTest(operacionEgreso.getItems(), presupuestoElegido));

        validacionUsoPresupuesto.validarcriterios(resultadoValidacion);

        assertTrue(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void es_incorrecta_la_seleccion_items_del_presupuesto_no_coincide_total() {
        ValidacionUsoPresupuesto validacionUsoPresupuesto = new ValidacionUsoPresupuesto("Mensaje OK", "Mensaje Error", " Validacion Uso de Presupuesto");

        OperacionEgreso operacionEgreso = resultadoValidacion.getOperacionEgreso();

        Presupuesto presupuestoElegido = operacionEgreso.presupuestoMenorValor();
        operacionEgreso.setPresupuestoElegido(presupuestoElegido);
        presupuestoElegido.setValorTotal(500);
        operacionEgreso.setValorTotal(250);

        operacionEgreso.setItems(this.getItemsTest());
        presupuestoElegido.setItems(this.getItemsPresupuestoTest(operacionEgreso.getItems(), presupuestoElegido));

        validacionUsoPresupuesto.validarcriterios(resultadoValidacion);

        assertFalse(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void es_incorrecta_la_seleccion_items_del_presupuesto_no_coinciden_items() {
        ValidacionUsoPresupuesto validacionUsoPresupuesto = new ValidacionUsoPresupuesto("Mensaje OK", "Mensaje Error", " Validacion Uso de Presupuesto");

        OperacionEgreso operacionEgreso = resultadoValidacion.getOperacionEgreso();

        Presupuesto presupuestoElegido = operacionEgreso.presupuestoMenorValor();
        operacionEgreso.setPresupuestoElegido(presupuestoElegido);
        presupuestoElegido.setValorTotal(500);
        operacionEgreso.setValorTotal(500);

        operacionEgreso.setItems(this.getItemsTest());
        presupuestoElegido.setItems(this.getItemsPresupuestoTest(new LinkedList<>(), presupuestoElegido));

        validacionUsoPresupuesto.validarcriterios(resultadoValidacion);

        assertFalse(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void validacion_inserta_mensajes_en_resultado_validacion() {
        Validacion validacion = new ValidacionCantPresupuestos("Mensaje OK creado para test", "Mensaje Fail", "Validacion Cantidad de Presupuestos");

        resultadoValidacion.getOperacionEgreso().setCantidadPresupuestoRequeridos(3);

        validacion.validarcriterios(resultadoValidacion);

        assertEquals(resultadoValidacion.getMensajes().get(0).getDescripcion(), "Mensaje OK creado para test");
        assertTrue(resultadoValidacion.isFueExitosa());
    }

    @Test
    public void validacion_inserta_mensajes_de_fallo_en_resultado_validacion() {
        Validacion validacion = new ValidacionCantPresupuestos("Mensaje OK creado para test", "Mensaje Fail", "Validacion Cantidad de Presupuestos");

        resultadoValidacion.getOperacionEgreso().setCantidadPresupuestoRequeridos(1);

        validacion.validarcriterios(resultadoValidacion);

        assertEquals(resultadoValidacion.getMensajes().get(0).getDescripcion(), "Mensaje Fail");
        assertFalse(resultadoValidacion.isFueExitosa());
    }


    private OperacionEgreso getOperacionEgresoTest() {
        DOOperacionEgreso doOperacionEgreso = new DOOperacionEgreso(null, null, 0.0f, null, null,null, false);

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

        operacionEgreso.setNecesitaPresupuesto(true);

        return operacionEgreso;
    }

    private List<Item> getItemsTest() {
        List<Item> items = new LinkedList<>();

        items.add(new Item("Detalle Item 1", "Descripcion Item 1", TipoItem.PRODUCTO, (float) 500.00, 2));
        items.add(new Item("Detalle Item 2", "Descripcion Item 2", TipoItem.PRODUCTO, (float) 600.00, 3));

        return items;
    }

    private List<ItemPresupuesto> getItemsPresupuestoTest(List<Item> items, Presupuesto presupuesto) {
        return items.stream().map(item -> new ItemPresupuesto(100, presupuesto, item,2)).collect(Collectors.toList());
    }
}
