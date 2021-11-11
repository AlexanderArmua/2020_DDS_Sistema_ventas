package Domain.Entities.Presupuestos;

public class ItemsPresupuestoDO {
    public ItemsPresupuestoDO(int id, float precio, Integer cantidad) {
        this.setId(id);
        this.setPrecio(precio);
        this.setCantidad(cantidad);
    }

    private int id;
    private float precio;
    private Integer cantidad;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
