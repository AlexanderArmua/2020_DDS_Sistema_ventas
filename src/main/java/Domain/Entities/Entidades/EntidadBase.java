package Domain.Entities.Entidades;

import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.Usuario;

import javax.persistence.*;

@Entity
public class EntidadBase extends Entidad{

    @Column
    private String descripcion;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_entidadJuridica", referencedColumnName = "id")
    private EntidadJuridica entidadJuridica;

    public EntidadBase() {

    }

    public EntidadBase(Usuario usuarioCreador, String nombre, String descripcion) throws NoTienePermisoException {
        super(usuarioCreador, nombre);
        this.setDescripcion(descripcion);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EntidadJuridica getEntidadJuridica() {
        return entidadJuridica;
    }

    public void setEntidadJuridica(EntidadJuridica entidadJuridica) {
        this.entidadJuridica = entidadJuridica;
    }
}
