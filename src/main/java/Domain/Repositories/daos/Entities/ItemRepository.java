package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.Item.Item;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemRepository extends Repositorio<Item> {

    public ItemRepository() {
        super(new DAOHibernate<>(Item.class));
    }

    public List<Item> buscarItemsDelEgreso(int idEgreso) {
        String query = "select i from OperacionEgreso oe " +
                "join oe.items i " +
                "where oe.id = :idEgreso";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEgreso", idEgreso).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

}