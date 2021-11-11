package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import java.util.ArrayList;
import java.util.List;


public class ProveedorRepository extends Repositorio<Proveedor> {

    public ProveedorRepository() {
        super(new DAOHibernate<>(Proveedor.class));
    }


    public List<Proveedor> buscarTodosParaElUsuario(Usuario user) {
        int idEntidad = user.getEntidad().getId();
        String query = "select p from Proveedor p " +
                " where p.entidad.id = :idEntidad ";

        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad",idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }
}
