package Domain.Entities.BandejaDeEntrada;

import Domain.Entities.Validaciones.ResultadoValidacion;
import db.EntidadPersistente;
import Domain.Entities.Usuarios.Usuario;
import db.converters.LocalDateTimeAttributeConverter;

import javax.persistence.*;
import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table( name = "mensaje" )
public class Mensaje extends EntidadPersistente {

    @Column(name = "fecha")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime fechaMensaje;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private Set<Usuario> fueLeidoPor = new HashSet<>();

    @Column( name = "descripcion_mensaje" )
    private String descripcion;

    @Column( name = "titulo_mensaje")
    private String titulo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_resultadovalidacion",referencedColumnName = "id")
    private ResultadoValidacion resultadoValidacion;

    public Mensaje(String descripcion) {
        this.fechaMensaje = LocalDateTime.now();
        this.descripcion = descripcion;
    }

    public Mensaje( LocalDateTime fechaMensaje ) {
        this.fechaMensaje = fechaMensaje;
    }

    public Mensaje(String descripcion, LocalDateTime fechaMensaje) {
        this.fechaMensaje = fechaMensaje;
        this.descripcion = descripcion;
    }

    public Mensaje( ) {
        this.setFueLeido(new HashSet<>());
    }

    public void leerMensaje(Usuario usuario) {
        this.fueLeidoPor.add(usuario);
    }

    public void setFechaMensaje(LocalDateTime fechaValidacion) {
        this.fechaMensaje = fechaValidacion;
    }

    public LocalDateTime getFechaMensaje() {
        return this.fechaMensaje;
    }

    public Boolean fueLeidoPorUsuario(Usuario usuario) {
        return this.fueLeidoPor.stream().anyMatch(usuarioLector -> usuarioLector.equals(usuario));
    }

    public Set<Usuario> isFueLeido() {
        return fueLeidoPor;
    }

    public void setFueLeido(Set<Usuario> fueLeidoPor) {
        this.fueLeidoPor = fueLeidoPor;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ResultadoValidacion getResultadoValidacion() {
        return resultadoValidacion;
    }

    public void setResultadoValidacion(ResultadoValidacion resultadoValidacion) {
        this.resultadoValidacion = resultadoValidacion;
    }
}
