package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.Operacion.Operacion;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class OperacionRepository  extends Repositorio<Operacion> {

    public OperacionRepository() {
        super(new DAOHibernate<>(Operacion.class));
    }


}
