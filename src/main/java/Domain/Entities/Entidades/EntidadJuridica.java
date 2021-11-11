package Domain.Entities.Entidades;

import Domain.Entities.Entidades.DataObject.DOEntidadJuridica;
import Domain.Entities.Entidades.TipoEntidad.TipoEntidadJuridica;
import Domain.Entities.Ubicacion.Direccion;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.PermisoUsuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class EntidadJuridica extends Entidad {

    @Column
    private String razonSocial;

    @Column
    private String cuit;

    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_direccionPostal", referencedColumnName = "id")
    private Direccion direccionPostal;

    @OneToMany(mappedBy = "entidadJuridica", cascade = CascadeType.ALL)
    private List<EntidadBase> entidadesBase;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "id_tipoEntidadJuridica", referencedColumnName = "id")
    private TipoEntidadJuridica tipoEntidadJuridica;

    @Column
    private String codigoIGJ;


    public EntidadJuridica() {
    }

    public EntidadJuridica(DOEntidadJuridica doEntidadJuridica) throws NoTienePermisoException {
        super(doEntidadJuridica.getUsuario(), doEntidadJuridica.getNombre());

        // TODO: MOVER ESTO DE LUGAR
        if (!doEntidadJuridica.getUsuario().tienePermiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA)) {
            throw new NoTienePermisoException("El usuario no tiene permiso para crear una entidades jurídicas");
        }

        this.entidadesBase = doEntidadJuridica.getEntidadesBase();
        this.setRazonSocial(doEntidadJuridica.getRazonSocial());
        this.setCuit(doEntidadJuridica.getCuit());
        this.setDireccionPostal(doEntidadJuridica.getDireccionPostal());
        this.setCodigoIGJ(doEntidadJuridica.getCodigoIGJ());
        this.setTipoEntidadJuridica(doEntidadJuridica.getTipoEntidadJuridica());
    }

    public void addEntidadBase(EntidadBase entidadBase) {
        this.entidadesBase.add(entidadBase);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

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

}
