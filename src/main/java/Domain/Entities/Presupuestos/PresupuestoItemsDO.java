package Domain.Entities.Presupuestos;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Entidades.Criterio.CategoriaDO;
import Domain.Entities.Operaciones.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class PresupuestoItemsDO {

    public PresupuestoItemsDO(int id, List<Item> items, List<Categoria> categorias) {
        this.setId(id);
        this.setItems(items);
        this.setCategorias(categorias);
    }

    private int id;

    private List<Item> items;

    private List<CategoriaDO> categorias;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<CategoriaDO> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        List<CategoriaDO> lista = new ArrayList<>();
        categorias.stream().forEach(c -> {
            CategoriaDO cat = new CategoriaDO();
            cat.setId(c.getId());
            cat.setDescripcion(c.getDescripcion());
            lista.add(cat);
        });
        this.categorias = lista;
    }
}
