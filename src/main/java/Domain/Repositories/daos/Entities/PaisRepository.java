package Domain.Repositories.daos.Entities;

import Domain.Entities.Ubicacion.Pais;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class PaisRepository extends Repositorio<Pais>{

    public PaisRepository() {
        super(new DAOHibernate<>(Pais.class));
    }

}