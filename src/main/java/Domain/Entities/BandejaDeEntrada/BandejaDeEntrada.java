package Domain.Entities.BandejaDeEntrada;

import Domain.Entities.Usuarios.Usuario;
import db.EntidadPersistente;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;



//@Entity
//@Table( name = "bandejaDeEntrada" )
@Embeddable
public class BandejaDeEntrada {

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mensaje", referencedColumnName = "id")
    private List<Mensaje> mensajes;


    public BandejaDeEntrada() {
        this.mensajes = new ArrayList<>();
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
        this.mensajes.addAll(mensajes);
    }

    //------------------------ GETTER & SETTERS -------------------------------------------------

    public List<Mensaje> getMensajes() {
        return this.mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    /*public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }*/
}
