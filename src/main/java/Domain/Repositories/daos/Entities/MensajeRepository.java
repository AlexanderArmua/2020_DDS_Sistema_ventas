package Domain.Repositories.daos.Entities;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.DAOMemoria;
import db.EntityManagerHelper;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;


public class MensajeRepository extends Repositorio<Mensaje> {
    private final Repositorio<Usuario> repositorioUsuario;

    public MensajeRepository() {
        super(new DAOHibernate<>(Mensaje.class));
        this.repositorioUsuario = new Repositorio<>(new DAOHibernate<>(Usuario.class));
    }

    public MensajeRepository(DAOMemoria<Mensaje> daoMemoria) {
        super(daoMemoria);
        this.repositorioUsuario = new Repositorio<>(new DAOMemoria<>());
    }

    @Transactional
    public List<Mensaje> obtenerMensajes(Usuario user) {
        return this.repositorioUsuario.buscar(user.getId()).getMensajes();
    }

}
