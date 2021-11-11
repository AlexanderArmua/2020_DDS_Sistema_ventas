package Domain.Entities.Operaciones.Proveedor;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Ubicacion.Direccion;

public class DOProveedor {

    private String nombre;

    private String apellido;

    private String razonSocial;

    private String cuit;

    private String dni;

    private Direccion direccionPostal;

    private Entidad entidad;

    public DOProveedor(String nombre, String apellido, String razonSocial, String cuit,
                       String dni, Direccion direccion,Entidad entidad){

        this.setNombre(nombre);
        this.setApellido(apellido);
        this.setRazonSocial(razonSocial);
        this.setCuit(cuit);
        this.setDni(dni);
        this.setDireccionPostal(direccion);
        this.setEntidad(entidad);
    }

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
