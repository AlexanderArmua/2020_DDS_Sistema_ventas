package Domain.controllers;

import Domain.Entities.Entidades.Criterio.Categoria;

public class CategoriaEditar {
    private Categoria categoria;
    private Boolean seleccionada;

    public CategoriaEditar(Categoria categoria, Boolean seleccionada) {
        this.categoria = categoria;
        this.seleccionada = seleccionada;
    }

    public Categoria getCategoria() { return categoria; }

    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public Boolean getSeleccionada() { return seleccionada; }

    public void setSeleccionada(Boolean seleccionada) { this.seleccionada = seleccionada; }
}
