package Domain.Entities.Entidades.DataObject;

import Domain.Entities.Entidades.EntidadBase;
import Domain.Entities.Entidades.TipoEntidad.TipoEntidadJuridica;
import Domain.Entities.Ubicacion.Direccion;
import Domain.Entities.Usuarios.Usuario;

import java.util.LinkedList;
import java.util.List;

public class DOEntidadJuridica {

    private String nombre;

    private Usuario usuario;

    private String razonSocial;

    private String cuit;

    private Direccion direccionPostal;

    private List<EntidadBase> entidadesBase;

    private TipoEntidadJuridica tipoEntidadJuridica;

    private String codigoIGJ;

    public DOEntidadJuridica(Usuario usuarioCreador,
                             String nombre, String razonSocial,
                             String cuit, Direccion direccion, String codigoIGJ,
                             TipoEntidadJuridica tipoEntidadJuridica){

        this.setUsuario(usuarioCreador);
        this.setNombre(nombre);
        this.entidadesBase = new LinkedList<>();
        this.setRazonSocial(razonSocial);
        this.setCuit(cuit);
        this.setDireccionPostal(direccion);
        this.setCodigoIGJ(codigoIGJ);
        this.setTipoEntidadJuridica(tipoEntidadJuridica);

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

    public Direccion getDireccionPostal() {
        return direccionPostal;
    }

    public void setDireccionPostal(Direccion direccionPostal) {
        this.direccionPostal = direccionPostal;
    }

    public List<EntidadBase> getEntidadesBase() {
        return entidadesBase;
    }

    public void setEntidadesBase(List<EntidadBase> entidadesBase) {
        this.entidadesBase = entidadesBase;
    }

    public TipoEntidadJuridica getTipoEntidadJuridica() {
        return tipoEntidadJuridica;
    }

    public void setTipoEntidadJuridica(TipoEntidadJuridica tipoEntidadJuridica) {
        this.tipoEntidadJuridica = tipoEntidadJuridica;
    }

    public String getCodigoIGJ() {
        return codigoIGJ;
    }

    public void setCodigoIGJ(String codigoIGJ) {
        this.codigoIGJ = codigoIGJ;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
