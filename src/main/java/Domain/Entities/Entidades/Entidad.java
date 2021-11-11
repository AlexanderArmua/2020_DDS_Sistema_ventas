package Domain.Entities.Entidades;

import Domain.Entities.Entidades.Criterio.Criterio;
import Domain.Entities.Operaciones.Operacion.Operacion;
import Domain.Entities.Validaciones.ValidacionCantPresupuestos;
import Domain.Entities.Validaciones.ValidacionCriterioSeleccion;
import Domain.Entities.Validaciones.ValidacionUsoPresupuesto;
import Domain.Entities.Operaciones.Proveedor.Proveedor;
import db.EntidadPersistente;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.Validaciones.Validador;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Inheritance( strategy = InheritanceType.JOINED)
public abstract class Entidad extends EntidadPersistente {

    @Column( name = "nombre_entidad")
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Usuario> usuarios;

    @OneToMany( cascade = CascadeType.ALL, mappedBy = "entidad", fetch = FetchType.LAZY)
    private List<Operacion> operaciones;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Criterio> criterios;

    @OneToMany( cascade = CascadeType.ALL)
    private List<Proveedor> proveedores;

    @Transient
    private Validador validador;

    public Entidad() {
        this.validador = new Validador();

        this.validador.addValidacionesARealizar( new ValidacionCantPresupuestos("Paso la validacion","Fallo la validacion", "Validacion Cantidad de Presupuestos"));
        this.validador.addValidacionesARealizar( new ValidacionUsoPresupuesto("Paso la validacion","Fallo la validacion", "Validacion Uso de Presupuestos"));
        this.validador.addValidacionesARealizar( new ValidacionCriterioSeleccion("Paso la validacion","Fallo la validacion", "Validacion Criterio de Seleccion"));

    }

    public Entidad(Usuario usuarioCreador, String nombre) throws NoTienePermisoException {
        if (!usuarioCreador.tienePermiso(PermisoUsuario.ABM_ENTIDAD_BASE)) {
            throw new NoTienePermisoException("El usuario no tiene permiso para crear una entidades base");
        }

        this.setNombre(nombre);
        this.operaciones = new ArrayList<>();
        this.usuarios = new ArrayList<>();
        this.criterios = new ArrayList<>();
        this.proveedores = new ArrayList<>();
        this.validador = new Validador(); // Todo: Podria haber distintos validadores inicializados por entidad
    }

    public void addUsuario(Usuario usuario) {
        this.usuarios.add(usuario);
    }

    public void addOperacion(Operacion ... operacion) {
        Collections.addAll(this.operaciones, operacion);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuario) {
        this.usuarios = usuario;
    }

    public List<Operacion> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<Operacion> operaciones) {
        this.operaciones = operaciones;
    }

    public List<Criterio> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<Criterio> criteriosValidacion) {
        this.criterios = criteriosValidacion;
    }

    public Validador getValidador() {
        return validador;
    }

    public void setValidador(Validador validador) {
        this.validador = validador;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }
}
