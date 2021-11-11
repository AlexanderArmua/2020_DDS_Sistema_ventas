package Domain.Entities.Operaciones.Operacion;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.MediosDePago.MedioDePago;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.ICriterioDeSeleccion;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.TipoCriterioSeleccion;
import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionEgreso;
import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Usuarios.Usuario;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class OperacionEgreso extends Operacion {

    public OperacionEgreso() {
        super();
        this.items = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.presupuestos = new ArrayList<>();
        this.revisores = new ArrayList<>();
    }

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_proveedor", referencedColumnName = "id")
    private Proveedor proveedor;

    @Column
    private boolean necesitaPresupuesto;

    @Column
    private int cantidadPresupuestoRequeridos;

    @OneToMany(mappedBy="operacionEgreso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Presupuesto> presupuestos;

    @Transient
    private ICriterioDeSeleccion criterioSeleccionPresupuesto;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn( name = "id_presupuesto", referencedColumnName = "id")
    private Presupuesto presupuestoElegido;

    @ManyToMany( fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Usuario> revisores;

    @Column
    private boolean fueValidada;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_medioDePago", referencedColumnName = "id")
    private MedioDePago medioDePago;

    @OneToOne(cascade = CascadeType.ALL)
    private DocumentoComercial documentoComercial;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Item> items;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Categoria> categorias;

   /*@ManyToOne
    @JoinColumn( name = "id_operacionIngreso", referencedColumnName = "id")
    private OperacionIngreso operacionIngreso;*/

    @ManyToMany(mappedBy = "operacionesEgresoAsociadas")
    private List<OperacionIngreso> operacionesIngresoAsociadas;

    @Enumerated(value = EnumType.STRING)
    private TipoCriterioSeleccion tipoCriterioSeleccion;

    public boolean debeTenerDocumento() {
        return true;
    }

    public void addCategoroa(Categoria ...categorias) {
        Collections.addAll(this.categorias, categorias);
    }
    public void addItem(Item ...items) {
        Collections.addAll(this.items, items);
    }

    public OperacionEgreso(DOOperacionEgreso doOperacionEgreso) {
        super(doOperacionEgreso);
        this.setMedioDePago(doOperacionEgreso.getMedioDePago());
        this.setDocumentoComercial(doOperacionEgreso.getDocumentoComercial());
        this.setItems(doOperacionEgreso.getItems());
        this.setProveedor(doOperacionEgreso.getProveedor());
        this.setNecesitaPresupuesto(doOperacionEgreso.isNecesitaPresupuesto());
        this.presupuestos = doOperacionEgreso.getPresupuestos();
        this.revisores = doOperacionEgreso.getRevisores();
        this.operacionesIngresoAsociadas = new ArrayList<>();
    }

    public void cargarPresupuesto(Presupuesto presupuesto) {
        this.presupuestos.add(presupuesto);
    }

    public Boolean cumpleCriterioSeleccion() {
        return this.criterioSeleccionPresupuesto.cumpleCriterioSeleccion(this);
    }

    public void finalizarValidacion() {
        this.fueValidada = true;
    }

    public Presupuesto presupuestoMenorValor(){
        if (this.presupuestos.size() == 0 ) {
            return null;
        }

        Presupuesto presupuestoMenorValor = this.presupuestos.get(0);

        for (Presupuesto presupuesto : this.presupuestos) {
            if (presupuesto.getValorTotal() < presupuestoMenorValor.getValorTotal()) {
                presupuestoMenorValor = presupuesto;
            }
        }

        return presupuestoMenorValor;
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public boolean isNecesitaPresupuesto() {
        return necesitaPresupuesto;
    }

    public void setNecesitaPresupuesto(boolean necesitaPresupuesto) {
        this.necesitaPresupuesto = necesitaPresupuesto;
    }

    public int getCantidadPresupuestoRequeridos() {
        return cantidadPresupuestoRequeridos;
    }

    public void setCantidadPresupuestoRequeridos(int cantidadPresupuestoRequeridos) {
        this.cantidadPresupuestoRequeridos = cantidadPresupuestoRequeridos;
    }

    public List<Presupuesto> getPresupuestos() {
        return this.presupuestos;
    }

    public void setPresupuestos(List<Presupuesto> presupuestos) {
        this.presupuestos = presupuestos;
    }

    public ICriterioDeSeleccion getCriterioSeleccionPresupuesto() {
        return criterioSeleccionPresupuesto;
    }

    public void setCriterioSeleccionPresupuesto(ICriterioDeSeleccion criterioSeleccionPresupuesto) {
        this.criterioSeleccionPresupuesto = criterioSeleccionPresupuesto;
    }

    public Presupuesto getPresupuestoElegido() {
        return presupuestoElegido;
    }

    public void setPresupuestoElegido(Presupuesto presupuestoElegido) {
        this.presupuestoElegido = presupuestoElegido;
    }

    public List<Usuario> getRevisores() {
        return revisores;
    }

    public void setRevisores(List<Usuario> revisores) {
        this.revisores = revisores;
    }

    public boolean isFueValidada() {
        return fueValidada;
    }

    public void setFueValidada(boolean fueValidada) {
        this.fueValidada = fueValidada;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    /*public OperacionIngreso getOperacionIngreso() {
        return operacionIngreso;
    }

    public void setOperacionIngreso(OperacionIngreso operacionIngreso) {
        this.operacionIngreso = operacionIngreso;
    }
*/
    @Override
    public boolean isNecesitaValidacion() {
        return !this.isFueValidada();
    }

    public List<OperacionIngreso> getOperacionesIngresoAsociadas() {
        return operacionesIngresoAsociadas;
    }

    public void setOperacionesIngresoAsociadas(List<OperacionIngreso> operacionesIngresoAsociadas) {
        this.operacionesIngresoAsociadas = operacionesIngresoAsociadas;
    }

    public TipoCriterioSeleccion getTipoCriterioSeleccion() {
        return tipoCriterioSeleccion;
    }

    public void setTipoCriterioSeleccion(TipoCriterioSeleccion tipoCriterioSeleccion) {
        this.tipoCriterioSeleccion = tipoCriterioSeleccion;
    }

    public void addOperacionIngresoAsociada(OperacionIngreso op) {
        this.getOperacionesIngresoAsociadas().add(op);
    }
}
