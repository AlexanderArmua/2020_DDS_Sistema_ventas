package Util.Tasks.Vinculador;

import java.util.ArrayList;
import java.util.List;

public class OperacionResponse {

    public OperacionResponse() {
        this.responseOperacion = new ArrayList<>();
    }

    private Long id;

    private List<BasicOperation> responseOperacion;

    private boolean fueVinculada;

    private boolean isEgreso;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BasicOperation> getResponseOperacion() {
        return responseOperacion;
    }

    public void setResponseOperacion(List<BasicOperation> responseOperacion) {
        this.responseOperacion = responseOperacion;
    }

    public boolean isFueVinculada() {
        return fueVinculada;
    }

    public void setFueVinculada(boolean fueVinculada) {
        this.fueVinculada = fueVinculada;
    }

    public final class BasicOperation {
        private Long id;
        private boolean fueVinculada;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public boolean isFueVinculada() {
            return fueVinculada;
        }

        public void setFueVinculada(boolean fueVinculada) {
            this.fueVinculada = fueVinculada;
        }
    }

    public boolean isEgreso() {
        return isEgreso;
    }

    public void setEgreso(boolean egreso) {
        isEgreso = egreso;
    }
}
