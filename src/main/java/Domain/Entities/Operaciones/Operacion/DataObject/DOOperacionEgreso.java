package Domain.Entities.Operaciones.Operacion.DataObject;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.MediosDePago.TipoMedioPago;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.ICriterioDeSeleccion;
import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Usuarios.Usuario;

import java.util.ArrayList;
import java.util.List;


public class DOOperacionEgreso extends DOOperacion {

    private Proveedor proveedor;

    private boolean necesitaPresupuesto;

    private int cantidadPresupuestoRequeridos;

    private List<Presupuesto> presupuestos;

    private ICriterioDeSeleccion criterioSeleccionPresupuesto;

    private Presupuesto presupuestoElegido;

    private List<Usuario> revisores;

    private boolean fueValidada;

    private List<Categoria> categorias;

    private OperacionIngreso operacionIngreso;

    public DOOperacionEgreso(TipoMedioPago tipoMedioPago, DocumentoComercial documentoComercial,
                             float valorTotal,String descripcion, List<Item> items, Proveedor proveedor,
                             boolean necesitaPresupuesto){

        super(tipoMedioPago, documentoComercial, valorTotal,descripcion,items);

        this.setProveedor(proveedor);
        this.setNecesitaPresupuesto(necesitaPresupuesto);
        this.presupuestos = new ArrayList<>();
        this.revisores = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }

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
        return presupuestos;
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

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public OperacionIngreso getOperacionIngreso() {
        return operacionIngreso;
    }

    public void setOperacionIngreso(OperacionIngreso operacionIngreso) {
        this.operacionIngreso = operacionIngreso;
    }
}
