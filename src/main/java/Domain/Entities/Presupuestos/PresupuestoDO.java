package Domain.Entities.Presupuestos;

import java.util.ArrayList;
import java.util.List;

public class PresupuestoDO {

    public PresupuestoDO(int id , List<ItemPresupuesto> items) {
        this.setId(id);
        this.setItems(items);
    }

    private int id;
    private List<ItemsPresupuestoDO> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ItemsPresupuestoDO> getItems() {
        return items;
    }

    public void setItems(List<ItemPresupuesto> items) {
        List<ItemsPresupuestoDO> itemsPresupuestoDO = new ArrayList<>();
        items.stream().forEach(i -> {
            ItemsPresupuestoDO item = new ItemsPresupuestoDO(i.getId(), i.getPrecio(), i.getCantidad());
            itemsPresupuestoDO.add(item);
        });
        this.items = itemsPresupuestoDO;
    }
}
