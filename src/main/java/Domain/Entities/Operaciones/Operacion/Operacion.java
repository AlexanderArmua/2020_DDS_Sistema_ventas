package Domain.Entities.Operaciones.Operacion;

import java.time.LocalDateTime;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacion;
import Domain.Entities.Presupuestos.Moneda;
import Domain.Entities.Presupuestos.Presupuesto;
import db.EntidadPersistente;
import db.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;

@Entity
@Table
@Inheritance( strategy = InheritanceType.JOINED)
public abstract class Operacion extends EntidadPersistente {

    public Operacion() {

    }


    @ManyToOne( cascade = CascadeType.DETACH)
    @JoinColumn( name = "id_entidad", referencedColumnName = "id")
    private Entidad entidad;

    @Column
    private float valorTotal;

    @Column
    private String descripcion;

    @Column(name = "fecha")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime fecha;

    @Column
    private boolean fueVinculada;

    public Operacion(DOOperacion doOperacion){
        this.setDescripcion(doOperacion.getDescripcion());
        this.setValorTotal(doOperacion.getValorTotal());
        this.setFecha(doOperacion.getFecha());
        this.setEntidad(doOperacion.getEntidad());
    }

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "id_moneda", referencedColumnName = "id")
    private Moneda moneda;

    //------------------------GETTER & SETTERS -------------------------------------------------

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public abstract boolean isNecesitaValidacion();

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
  
    public boolean isFueVinculada() {
        return fueVinculada;
    }

    public void setFueVinculada(boolean fueVinculada) {
        this.fueVinculada = fueVinculada;
    }

    public Moneda getMoneda() {
        return moneda;
    }

    public void setMoneda(Moneda moneda) {
        this.moneda = moneda;
    }
}
