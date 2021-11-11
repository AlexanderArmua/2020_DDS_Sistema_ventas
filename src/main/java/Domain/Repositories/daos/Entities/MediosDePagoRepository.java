package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.MediosDePago.MedioDePago;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class MediosDePagoRepository extends Repositorio<MedioDePago> {

    public MediosDePagoRepository() {
        super(new DAOHibernate<>(MedioDePago.class));
    }

}
