package Util.Tasks.Vinculador;

import java.util.ArrayList;
import java.util.List;

public class OperacionesDTO {

    public OperacionesDTO() {
        egresos = new ArrayList<>();
        ingresos = new ArrayList<>();
        criterios = new ArrayList<>();
    }

    private List<OperacionVinc> egresos;

    private List<OperacionVinc> ingresos;

    private List<String> criterios;

    public List<OperacionVinc> getOperacionesEgresos() {
        return egresos;
    }

    public void setOperacionesEgresos(List<OperacionVinc> operacionesEgresos) {
        this.egresos = operacionesEgresos;
    }

    public List<OperacionVinc> getOperacionIngresos() {
        return ingresos;
    }

    public void setOperacionIngresos(List<OperacionVinc> operacionIngresos) {
        this.ingresos = operacionIngresos;
    }

    public List<String> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<String> criterios) {
        this.criterios = criterios;
    }
}
