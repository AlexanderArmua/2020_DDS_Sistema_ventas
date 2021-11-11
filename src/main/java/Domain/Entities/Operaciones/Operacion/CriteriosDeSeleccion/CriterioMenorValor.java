package Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion;

import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Presupuestos.Presupuesto;

public class CriterioMenorValor implements ICriterioDeSeleccion {

    @Override
    public Boolean cumpleCriterioSeleccion(OperacionEgreso operacionEgreso) {
        Presupuesto presupuestoElegido = operacionEgreso.getPresupuestoElegido();

        return (presupuestoElegido == null || operacionEgreso.presupuestoMenorValor() == null) ? false : operacionEgreso.presupuestoMenorValor() == presupuestoElegido;
    }
}
