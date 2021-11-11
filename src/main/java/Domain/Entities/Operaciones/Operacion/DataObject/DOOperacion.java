package Domain.Entities.Operaciones.Operacion.DataObject;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.MediosDePago.MedioDePago;
import Domain.Entities.Operaciones.MediosDePago.TipoMedioPago;

import java.time.LocalDateTime;
import java.util.List;

public abstract class DOOperacion {
    private Long id;

    private MedioDePago medioDePago;

    private DocumentoComercial documentoComercial;

    private Entidad entidad;

    private float valorTotal;

    private String descripcion;

    private LocalDateTime fecha;

    private List<Item> items;


    public DOOperacion(TipoMedioPago tipoMedioPago, DocumentoComercial documentoComercial, float valorTotal,String descripcion, List<Item> items){
        this.setMedioDePago(new MedioDePago(tipoMedioPago));
        this.setDocumentoComercial(documentoComercial);
        this.setValorTotal(valorTotal);
        this.setItems(items);
        this.setDescripcion(descripcion);
    }

    public MedioDePago getMedioDePago() {
        return medioDePago;
    }

    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }

    public DocumentoComercial getDocumentoComercial() {
        return documentoComercial;
    }

    public void setDocumentoComercial(DocumentoComercial documentoComercial) {
        this.documentoComercial = documentoComercial;
    }

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
