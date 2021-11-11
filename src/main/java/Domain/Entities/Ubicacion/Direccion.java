package Domain.Entities.Ubicacion;

import db.EntidadPersistente;
import Domain.Entities.Ubicacion.DataObject.DODireccion;

import javax.persistence.*;

@Entity
@Table( name = "direccion")
public class Direccion extends EntidadPersistente {

    @Column
    private String calle;

    @Column( name = "nroAltura")
    private String altura;

    @Column( name = "nroPiso")
    private String piso;

    @Column
    private String departamento;

    @Column
    private String codigoPostal;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn( name = "id_ciudad", referencedColumnName = "id")
    private Ciudad ciudad;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn( name = "id_provincia", referencedColumnName = "id")
    private Provincia provincia;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn( name = "id_pais", referencedColumnName = "id")
    private Pais pais;

    public Direccion(){

    }

    public Direccion(DODireccion doDireccion) {
        this.setCalle(doDireccion.getCalle());
        this.setAltura(doDireccion.getAltura());
        this.setPiso(doDireccion.getPiso());
        this.setDepartamento(doDireccion.getDepartamento());
        this.setCiudad(doDireccion.getCiudad());
        this.setProvincia(doDireccion.getProvincia());
        this.setPais(doDireccion.getPais());
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
