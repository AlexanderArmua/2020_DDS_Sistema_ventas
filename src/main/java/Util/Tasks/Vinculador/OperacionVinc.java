package Util.Tasks.Vinculador;

import java.util.Date;

public class OperacionVinc {

    public Float valorTotal;
    public String fecha;
    public String fechaHasta;
    public Integer id;
    public boolean isEgreso;

    public OperacionVinc() {
        this.isEgreso = false;
    }

    public String getFecha() {
        return fecha;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isEgreso() {
        return isEgreso;
    }

    public void setEgreso(boolean egreso) {
        isEgreso = egreso;
    }
}
