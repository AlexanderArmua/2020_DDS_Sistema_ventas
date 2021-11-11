package Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion;

import Domain.Entities.Operaciones.Operacion.OperacionEgreso;

public interface ICriterioDeSeleccion {

    Boolean cumpleCriterioSeleccion(OperacionEgreso operacionEgreso);
}
