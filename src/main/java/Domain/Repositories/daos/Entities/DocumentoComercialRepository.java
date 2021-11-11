package Domain.Repositories.daos.Entities;

import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

public class DocumentoComercialRepository extends Repositorio<DocumentoComercial> {

    public DocumentoComercialRepository() {
        super(new DAOHibernate<>(DocumentoComercial.class));
    }

}
