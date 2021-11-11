package Domain.Entities.Validaciones;

import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.CriterioMenorValor;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.TipoCriterioSeleccion;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;

public class ValidacionCriterioSeleccion extends Validacion {

    public ValidacionCriterioSeleccion(String mensajeOK, String mensajeFAIL, String titulo) {
        super(mensajeOK, mensajeFAIL, titulo);
    }

    @Override
    public Boolean esValido(ResultadoValidacion resultadoValidacion) {
        OperacionEgreso operacionEgreso = resultadoValidacion.getOperacionEgreso();
        if(operacionEgreso.getTipoCriterioSeleccion() != null) {
            switch(operacionEgreso.getTipoCriterioSeleccion().toString()) {
                case "CRITERIO_MENOR_VALOR": operacionEgreso.setCriterioSeleccionPresupuesto(new CriterioMenorValor()); break;
                default: break;
            }
            return operacionEgreso.cumpleCriterioSeleccion();
        }
        return null;
    }

}
