package Domain.Entities.Entidades.Criterio;

import db.EntidadPersistente;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;

import javax.persistence.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table( name = "criterio")
public class Criterio extends EntidadPersistente {

    public Criterio() {}

    @Column
    private String descripcion;

    @OneToMany(mappedBy = "criterio",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Categoria> categorias = new LinkedList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "id_criterioPadre", referencedColumnName = "id")
    private Criterio padre = null;

    public Criterio(Usuario usuarioCreador, String descripcion) throws NoTienePermisoException {
        if (usuarioCreador.tienePermiso(PermisoUsuario.ABM_CATYCRI)) {
            throw new NoTienePermisoException("El usuario no tiene permiso para crear un criterio");
        }

        this.descripcion = descripcion;
    }

    public void addCategoria(Categoria ... categorias) {
        Collections.addAll(this.categorias, categorias);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Criterio getPadre() {
        return padre;
    }

    public void setPadre(Criterio padre) {
        this.padre = padre;
    }
}
