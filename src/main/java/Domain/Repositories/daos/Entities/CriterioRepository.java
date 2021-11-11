package Domain.Repositories.daos.Entities;

import Domain.Entities.Entidades.Criterio.Criterio;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import java.util.ArrayList;
import java.util.List;

public class CriterioRepository extends Repositorio<Criterio> {

    public CriterioRepository() {
        super(new DAOHibernate<>(Criterio.class));
    }

    public List<Criterio> buscarTodosParaElUsuario(Usuario user) {
        int idEntidad = user.getEntidad().getId();
        String query = "select c from Entidad e " +
                "join e.criterios c" +
                " where e.id= :idEntidad";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad", idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
