package Domain.Entities.Validaciones;

// TODO: Ver como se pasan los mensajes, requiere rearmarlo o que esten siempre definidos adentro
public class ValidacionFactory {
    public static Validacion obtenerValidacion(String selectorValidacion) {
        Validacion validacion;
        switch (selectorValidacion) {
            case "cantidadPresupuestos":
                validacion = new ValidacionCantPresupuestos("Mensaje OK", "Mensaje Falló", "Validacion Cantidad de Presupuestos");
                break;
            case "usoPresupuesto":
                validacion = new ValidacionUsoPresupuesto("Mensaje OK", "Mensaje Falló", " Validacion Uso de Presupuesto");
                break;
            case "criterioSeleccion":
            default:
                validacion = new ValidacionCriterioSeleccion("Mensaje OK", "Mensaje Falló", " Validacion Criterio de seleccion");
                break;
        }

        return validacion;
    }
}
