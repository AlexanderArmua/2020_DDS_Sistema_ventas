package Domain.controllers;

import Domain.Entities.Usuarios.Rol;

public class RolEditarUsuario {
    private Rol rol;
    private Boolean seleccionado;

    public RolEditarUsuario(Rol rol, Boolean seleccionado) {
        this.rol = rol;
        this.seleccionado = seleccionado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
