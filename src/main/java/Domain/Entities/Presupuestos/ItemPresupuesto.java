package Domain.Entities.Presupuestos;

import Domain.Entities.Operaciones.Item.Item;
import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class ItemPresupuesto extends EntidadPersistente {

    @Column
    private float precio;

    @Column
    private Integer cantidad;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "id_presupuesto", referencedColumnName = "id")
    private Presupuesto presupuesto;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_item", referencedColumnName = "id")
    private Item item;

    public ItemPresupuesto() {}

    public ItemPresupuesto(float precio, Presupuesto presupuesto, Item item, Integer cantidad) {
        this.precio = precio;
        this.presupuesto = presupuesto;
        this.item = item;
        this.cantidad = cantidad;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Presupuesto getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(Presupuesto presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
