package Domain.Entities.Usuarios;

import db.EntidadPersistente;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "rol")
public class Rol extends EntidadPersistente {

    @Column
    private String nombre;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_permiso", referencedColumnName = "id")
    private List<Permiso> permisos = new ArrayList<>();

    public Rol(String nombre) {
        this.nombre = nombre;

    }

    public Rol() {

    }

    public void addPermisos(Permiso... permisoUsuarios) {
        Collections.addAll(this.permisos, permisoUsuarios);
    }

    public boolean tienePermiso(PermisoUsuario permisoUsuario) {
        return permisos.stream().anyMatch(permiso -> permiso.getId() == permisoUsuario.getIdPermiso());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void vaciarPermisos() {
        this.permisos = null;
    }

    @Override
    public String toString() {
        return "Rol{" +
                "nombre='" + nombre + '\'' +
                ", permisos=" + permisos +
                '}';
    }
}
