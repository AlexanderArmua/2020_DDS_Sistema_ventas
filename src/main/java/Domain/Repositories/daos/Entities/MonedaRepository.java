package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Presupuestos.Moneda;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class MonedaRepository extends Repositorio<Moneda> {
    public MonedaRepository() {
        super(new DAOHibernate<>(Moneda.class));
    }
}
