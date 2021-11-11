package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class OperacionEgresoRepository extends Repositorio<OperacionEgreso> {

    public OperacionEgresoRepository() {
        super(new DAOHibernate<>(OperacionEgreso.class));
    }

    public List<OperacionEgreso> listarSegunCriterioYCategoria(int criterioId, int categoriaId,Usuario usuario) {
        int idEntidad = usuario.getEntidad().getId();
        String query = "select oe from OperacionEgreso oe ";
        if(criterioId != 0) {
            query += " join oe.categorias c where oe.entidad.id = :idEntidad and c.criterio.id = :criterioId ";
            if(categoriaId != 0) {
                query += " and c.id = :categoriaId ";
            }
        } else {
            if(categoriaId != 0) {
                query += " where oe.entidad.id = :idEntidad and :categoriaId in (oe.categorias.id) ";
            }
            else{
                query += " where oe.entidad.id = :idEntidad";
            }
        }

        try {
            Query listado = EntityManagerHelper.getEntityManager().createQuery(query);
            listado.setParameter("idEntidad",idEntidad);
            if (criterioId != 0) {
                listado.setParameter("criterioId", criterioId);
            }
            if (categoriaId != 0) {
                listado.setParameter("categoriaId", categoriaId);
            }

            return listado.getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

    public List<OperacionEgreso> buscarTodosSinVincular(int idEntidad) {
        String query = "select oe from OperacionEgreso oe where oe.entidad.id = :idEntidad and oe.fueVinculada = false";
        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad",idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

    public List<OperacionEgreso> buscarTodosParaElUsuario(Usuario user) {
        int idEntidad = user.getEntidad().getId();
        String query = "select oe from OperacionEgreso oe " +
                " where oe.entidad.id = :idEntidad";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad", idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
