package Domain.Repositories.daos.Entities;

import Domain.Entities.Ubicacion.Provincia;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class ProvinciaRepository extends Repositorio<Provincia>{

    public ProvinciaRepository() {
        super(new DAOHibernate<>(Provincia.class));
    }

}