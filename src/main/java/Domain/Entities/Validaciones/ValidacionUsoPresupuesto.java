package Domain.Entities.Validaciones;

import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Presupuestos.ItemPresupuesto;
import Domain.Entities.Presupuestos.Presupuesto;

import java.util.ArrayList;
import java.util.List;

public class ValidacionUsoPresupuesto extends Validacion {

    public ValidacionUsoPresupuesto(String mensajeOK, String mensajeFAIL, String titulo) {
        super(mensajeOK, mensajeFAIL, titulo);
    }

    @Override
    public Boolean esValido(ResultadoValidacion resultadoValidacion) {
        OperacionEgreso operacionEgreso = resultadoValidacion.getOperacionEgreso();

        List<Item> itemsOperacion = operacionEgreso.getItems();
        Presupuesto presupuestoElegido = operacionEgreso.getPresupuestoElegido()!= null ? operacionEgreso.getPresupuestoElegido(): new Presupuesto();

        List<ItemPresupuesto> itemsPresupuesto = presupuestoElegido.getItems();

        Boolean coincidenLosValoresTotales = operacionEgreso.getValorTotal() == presupuestoElegido.getValorTotal();

        // TODOS LOS ITEMS dentro de itemsOperacion deben cumplir de estar dentro de itemPresupuesto
        Boolean coincidenTodosLosItems = itemsOperacion.stream()
                .allMatch(itemOperacion -> itemsPresupuesto.stream()
                        .anyMatch(itemPresupuesto -> itemPresupuesto.getItem().equals(itemOperacion)));


        return coincidenTodosLosItems && coincidenLosValoresTotales;
    }
}

/**
 *  [Item1, Item2, Item3]
 *  [ItemPresupuesto1, ItemPresupuesto2, ItemPresupuesto3]
 *
 *  Item1 y [ItemPresupuesto1, ItemPresupuesto2, ItemPresupuesto3]
 *  Item1 === ItemPresupuesto1.getItem() => Si
 *
 *  Item2 y [ItemPresupuesto1, ItemPresupuesto2, ItemPresupuesto3]
 *  Item2 === ItemPresupuesto1.getItem() => No
 *  Item2 === ItemPresupuesto2.getItem() => No
 */