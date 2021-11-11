package Domain.Entities.Operaciones.Operacion;

import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionIngreso;
import db.converters.LocalDateAttributeConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class OperacionIngreso extends Operacion {

    public OperacionIngreso() {
        super();
    }

    @Column(name = "fechaHasta")
    @Convert(converter = LocalDateAttributeConverter.class)
    private LocalDate fechaHasta;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "operaciones_vinculadas",
            joinColumns = @JoinColumn(name = "operacionIngresoId"),
            inverseJoinColumns = @JoinColumn(name = "operacionEgresoId"))
    private List<OperacionEgreso> operacionesEgresoAsociadas;

    public OperacionIngreso(DOOperacionIngreso doOperacionIngreso) {
        super(doOperacionIngreso);
        this.operacionesEgresoAsociadas = new ArrayList<>();
    }

    public LocalDate getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(LocalDate fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    @Override
    public boolean isNecesitaValidacion() {
        return false;
    }

    public List<OperacionEgreso> getOperacionesEgresoAsociadas() {
        return operacionesEgresoAsociadas;
    }

    public void setOperacionesEgresoAsociadas(List<OperacionEgreso> operacionesEgresoAsociadas) {
        this.operacionesEgresoAsociadas = operacionesEgresoAsociadas;
    }

    public void addOperacionEgresoAsociada(OperacionEgreso op){
        this.getOperacionesEgresoAsociadas().add(op);
    }
}
