package Domain.Repositories.daos.Entities;

import Domain.Entities.Usuarios.Rol;

import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import javax.persistence.TypedQuery;
import java.util.List;

public class RolRepository extends Repositorio<Rol> {

    public RolRepository() {
        super(new DAOHibernate<>(Rol.class));
    }

    // TODO: DELETE, queda como ejemplo de implementación
    public List<Rol> findAllConIdPar() {
        String jql = "from Rol where id % 2 = 0";
        TypedQuery<Rol> q = EntityManagerHelper.getEntityManager().createQuery(jql, Rol.class);

        return q.getResultList();
    }
}
