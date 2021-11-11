package Domain.Entities.Usuarios;

import db.EntidadPersistente;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "permiso")
public class Permiso extends EntidadPersistente {

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    public Permiso() {}

    public Permiso(int idPermiso) {
        this.setId(idPermiso);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
