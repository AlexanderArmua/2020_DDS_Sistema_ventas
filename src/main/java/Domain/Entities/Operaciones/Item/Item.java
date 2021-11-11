package Domain.Entities.Operaciones.Item;

import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class Item extends EntidadPersistente {

    @Column
    private String detalle;

    @Column
    private Float precio;

    @Column
    private String descripcion;

    @Column
    private Integer cantidad;

    @Enumerated(value = EnumType.STRING)
    @Column( name = "tipoItem")
    private TipoItem tipoItem;

    public Item(String detalle, String descripcion, TipoItem tipoItem, Float precio, Integer cantidad) {
        this.setDescripcion(descripcion);
        this.setDetalle(detalle);
        this.setTipoItem(tipoItem);
        this.setPrecio(precio);
        this.setCantidad(cantidad);
    }

    public Item() {

    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoItem getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(TipoItem tipoItem) {
        this.tipoItem = tipoItem;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

}
