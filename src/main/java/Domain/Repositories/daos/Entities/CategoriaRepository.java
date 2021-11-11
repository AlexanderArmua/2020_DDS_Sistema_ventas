package Domain.Repositories.daos.Entities;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class CategoriaRepository extends Repositorio<Categoria> {

    public CategoriaRepository() {
        super(new DAOHibernate<>(Categoria.class));
    }

    public List<Categoria> listarSegunCriterio(int criterioId) {
        String query = "select c from Categoria c where c.criterio.id = :criterioId";
        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("criterioId", criterioId).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

    public List<Categoria> buscarTodosParaElUsuario(Usuario usuario){
        int idEntidad = usuario.getEntidad().getId();
        String query = "select c from Entidad e " +
                "join e.criterios cri " +
                "join cri.categorias c " +
                "where e.id = :idEntidad";
        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad", idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

}
