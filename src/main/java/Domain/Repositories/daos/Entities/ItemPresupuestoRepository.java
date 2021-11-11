package Domain.Repositories.daos.Entities;

import Domain.Entities.Presupuestos.ItemPresupuesto;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

public class ItemPresupuestoRepository extends Repositorio<ItemPresupuesto> {

    public ItemPresupuestoRepository() {
        super(new DAOHibernate<>(ItemPresupuesto.class));
    }

    @Transactional
    public void deleteItemsPresupuesto(int idPresupuesto) {
        EntityManagerHelper.beginTransaction();

        String query = "delete from ItemPresupuesto ip where ip.presupuesto.id = :idPresupuesto";
        try {
            EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idPresupuesto", idPresupuesto).executeUpdate();
            EntityManagerHelper.commit();
        } catch(Exception e) {
            throw e;
        }
    }

    public List<ItemPresupuesto> buscarItemsDelPresupuesto(int idPresupuesto) {
        String query = "select ip from ItemPresupuesto ip where ip.presupuesto.id = :idPresupuesto";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idPresupuesto", idPresupuesto).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

}
