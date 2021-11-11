package Domain.Entities.Usuarios;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.hash.Hashing;
import db.EntidadPersistente;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuario")
public class Usuario extends EntidadPersistente {

    @Column
    private String usuario;

    private String contrasenia;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn( name = "id_entidad", referencedColumnName = "id")
    private Entidad entidad;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", referencedColumnName = "id")
    private List<Rol> roles = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mensaje", referencedColumnName = "id")
    private List<Mensaje> mensajes;


    public Usuario(final String nombre) {
        this.setUsuario(nombre);
    }

    public Usuario() {

    }

    public boolean tienePermiso(PermisoUsuario permisoUsuario) {
        return this.roles.stream().anyMatch(rol -> rol.tienePermiso(permisoUsuario));
    }

    public boolean tieneRol(Rol rol) {
        return this.roles.stream().anyMatch(r -> r.getId() == rol.getId());
    }

    // TODO: Esta bien esto aca?
    public void crearOperacionEgreso(OperacionEgreso operacionEgreso) {
        operacionEgreso.setEntidad(this.entidad);
        this.entidad.addOperacion(operacionEgreso);
    }

    public void addRoles(Rol ... roles) {
        Collections.addAll(this.roles, roles);
    }

    public static String getPasswordHashed(String contrasenia) {
        String shaContrasenna256hex = Hashing.sha512()
                .hashString(contrasenia, StandardCharsets.UTF_16BE)
                .toString();

        return shaContrasenna256hex;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuario='" + usuario + '\'' +
                ", entidad=" + entidad +
                ", roles=" + roles +
                '}';
    }


    public List<Mensaje> filtrarPorFecha(LocalDateTime localDateTime) {
        return this.mensajes.stream()
                .filter(mensaje -> mensaje.getFechaMensaje().toLocalDate().compareTo(localDateTime.toLocalDate()) == 0)
                .collect(Collectors.toList());
    }

    public List<Mensaje> filtrarPorLeidos(Usuario usuario) {
        return this.mensajes.stream()
                .filter(mensaje -> mensaje.fueLeidoPorUsuario(usuario))
                .collect(Collectors.toList());
    }

    public void addMensaje(Mensaje ... mensajes) {
        Collections.addAll(this.mensajes, mensajes);
    }

    public void addMensaje(List<Mensaje> mensajes) {
        if (this.mensajes == null) {
            this.mensajes = new LinkedList<>();
        }
        this.mensajes.addAll(mensajes);
    }


    //------------------------GETTER & SETTERS -------------------------------------------------

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Entidad getEntidad() {
        return entidad;
    }

    public void setEntidad(Entidad entidad) {
        this.entidad = entidad;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<Mensaje> getMensajes() {
        if (this.mensajes == null) {
            this.mensajes = new LinkedList<>();
        }
        return this.mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

}
