package Domain.Entities.Entidades.Criterio;

import db.EntidadPersistente;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;

import javax.persistence.*;

@Entity
@Table( name = "categoria")
public class Categoria extends EntidadPersistente {

    public Categoria() {}

    @Column
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_criterio", referencedColumnName = "id")
    private Criterio criterio;

    public Categoria(Usuario usurioCreador, String descripcion, Criterio criterio) throws NoTienePermisoException {
        if (usurioCreador.tienePermiso(PermisoUsuario.ABM_CATYCRI)) {
            throw new NoTienePermisoException("El usuario no tiene permiso para crear una categoria");
        }

        this.descripcion = descripcion;
        this.criterio = criterio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Criterio getCriterio() {
        return criterio;
    }

    public void setCriterio(Criterio criterio) {
        this.criterio = criterio;
    }
}
