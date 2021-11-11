package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import java.util.ArrayList;
import java.util.List;

public class OperacionIngresoRepository extends Repositorio<OperacionIngreso> {

    public OperacionIngresoRepository() {
        super(new DAOHibernate<>(OperacionIngreso.class));
    }

    public List<OperacionIngreso> buscarTodosSinVincular(int idEntidad) {
        String query = "select oi from OperacionIngreso oi where oi.entidad.id = :idEntidad and oi.fueVinculada = false";
        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad",idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

    public List<OperacionIngreso> buscarTodosParaElUsuario(Usuario user) {
        int idEntidad = user.getEntidad().getId();
        String query = "select oi from OperacionIngreso oi " +
                " where oi.entidad.id = :idEntidad";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad", idEntidad).getResultList();
        } catch(Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
