package Domain.Repositories.daos.Entities;

import Domain.Entities.Ubicacion.Ciudad;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class CiudadRepository extends Repositorio<Ciudad>{

    public CiudadRepository() {
        super(new DAOHibernate<>(Ciudad.class));
    }

}