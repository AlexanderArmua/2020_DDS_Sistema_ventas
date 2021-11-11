package Domain.Entities.Operaciones.Proveedor;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Ubicacion.Direccion;
import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class Proveedor extends EntidadPersistente {

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String razonSocial;

    @Column
    private String cuit;

    @Column
    private String dni;

    @ManyToOne
    @JoinColumn( name = "id_entidad", referencedColumnName = "id")
    private Entidad entidad;

    @OneToOne(cascade = CascadeType.ALL)
    //@JoinColumn( name = "id_direccion", referencedColumnName = "id")
    private Direccion direccionPostal;

    public Proveedor() {
    }

    public Proveedor(DOProveedor doProveedor) {
        this.setNombre(doProveedor.getNombre());
        this.setApellido(doProveedor.getApellido());
        this.setRazonSocial(doProveedor.getRazonSocial());
        this.setCuit(doProveedor.getCuit());
        this.setDni(doProveedor.getDni());
        this.setDireccionPostal(doProveedor.getDireccionPostal());
        this.setEntidad(doProveedor.getEntidad());
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Direccion getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(Direccion direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }
}
