package Domain.controllers;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Entities.Usuarios.Usuario;

public class MensajeLeer {

    private Mensaje mensaje;

    private boolean noLeido;

    public MensajeLeer(Mensaje mensaje, Boolean noLeido) {
        this.mensaje = mensaje;
        this.noLeido = noLeido;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isNoLeido() {
        return noLeido;
    }

    public void setNoLeido(boolean noLeido) {
        this.noLeido = noLeido;
    }


    /*public Boolean fueLeido(Usuario usuario){
        this.setNoLeido( mensaje.fueLeidoPorUsuario(usuario) );
        return noLeido;
    }*/


}
