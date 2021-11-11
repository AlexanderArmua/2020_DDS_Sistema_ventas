package Domain.Repositories.daos.Entities;

import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;
import java.util.List;
import java.util.ArrayList;

public class PresupuestoRepository  extends Repositorio<Presupuesto> {

    public PresupuestoRepository() {
        super(new DAOHibernate<>(Presupuesto.class));
    }

    public List<Presupuesto> buscarTodosParaElUsuario(Usuario user) {
        int idEntidad = user.getEntidad().getId();
        String query = "select p from Presupuesto p " +
                "join p.operacionEgreso oe " +
                " where oe.entidad.id = :idEntidad";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad",idEntidad).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public List<Presupuesto> buscarPorOperacion(int idOperacion) {
        String query = "select p from Presupuesto p where p.operacionEgreso.id = :idOperacion";
        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idOperacion", idOperacion).getResultList();
        } catch(Exception e) {
            return null;
        }
    }


}
