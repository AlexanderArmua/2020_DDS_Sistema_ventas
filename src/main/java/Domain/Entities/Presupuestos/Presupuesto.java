package Domain.Entities.Presupuestos;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Proveedor.Proveedor;
import db.EntidadPersistente;
import db.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table( name = "presupuesto")
public class Presupuesto extends EntidadPersistente {

    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemPresupuesto> items;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_documentoComercialPresupuesto", referencedColumnName = "id")
    private DocumentoComercial documentoComercial;

    @Column
    private float valorTotal;

    @Column
    private String descripcion;

    @Column(name = "fecha")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime fecha;

    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn( name = "id_operacionEgreso", referencedColumnName = "id")
    private OperacionEgreso operacionEgreso;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_proveedor", referencedColumnName = "id")
    private Proveedor proveedor;

    public Presupuesto() {
        this.items = new ArrayList<>();
        this.valorTotal=0;
    }

    public Presupuesto(List<ItemPresupuesto> items, DocumentoComercial documentoComercial, float valorTotal, OperacionEgreso operacionEgreso, Proveedor proveedor, LocalDateTime fecha,String descripcion) {
        this.items = items;
        this.documentoComercial = documentoComercial;
        this.valorTotal = valorTotal;
        this.operacionEgreso = operacionEgreso;
        this.proveedor = proveedor;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public void addItem(ItemPresupuesto item) {
        this.items.add(item);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public List<ItemPresupuesto> getItems() {
        return items;
    }

    public void setItems(List<ItemPresupuesto> items) {
        this.items = items;
    }

    public DocumentoComercial getDocumentoComercial() {
        return documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public OperacionEgreso getOperacionEgreso() {
        return operacionEgreso;
    }

    public void setOperacionEgreso(OperacionEgreso operacionEgreso) {
        this.operacionEgreso = operacionEgreso;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
