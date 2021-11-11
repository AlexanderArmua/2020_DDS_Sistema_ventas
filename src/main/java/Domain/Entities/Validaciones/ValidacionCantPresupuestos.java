package Domain.Entities.Validaciones;


import Domain.Entities.Operaciones.Operacion.OperacionEgreso;


public class ValidacionCantPresupuestos extends Validacion {

    public ValidacionCantPresupuestos(String mensajeOK, String mensajeFAIL, String titulo) {
        super(mensajeOK, mensajeFAIL, titulo);
    }

    @Override
    public Boolean esValido(ResultadoValidacion resultadoValidacion) {
        OperacionEgreso operacionEgreso = resultadoValidacion.getOperacionEgreso();

        return operacionEgreso.getCantidadPresupuestoRequeridos() == operacionEgreso.getPresupuestos().size();
    }

}
