package Domain.Repositories.daos.Entities;

import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Direccion;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import db.EntityManagerHelper;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DireccionRepository extends Repositorio<Direccion> {
    private final Repositorio<Pais> paisRepositorio;
    private final Repositorio<Provincia> provinciaRepositorio;
    private final Repositorio<Ciudad> ciudadRepositorio;

    public DireccionRepository() {
        super(new DAOHibernate<>(Direccion.class));
        this.paisRepositorio = new Repositorio<>(new DAOHibernate<>(Pais.class));
        this.provinciaRepositorio = new Repositorio<>(new DAOHibernate<>(Provincia.class));
        this.ciudadRepositorio = new Repositorio<>(new DAOHibernate<>(Ciudad.class));
    }


    @Transactional
    public List<Pais> buscarTodosPaises() {
        return paisRepositorio.buscarTodos();
    }

    @Transactional
    public List<Provincia> buscarProvincias(Integer idPais) {
        String jql = "from Provincia where id_pais = :idPais";
        TypedQuery<Provincia> q = EntityManagerHelper.getEntityManager().createQuery(jql, Provincia.class)
                .setParameter("idPais", idPais);

        List<Provincia> provincias;
        try {
            provincias = q.getResultList();
        } catch (NoResultException e) {
            provincias = new ArrayList<>();
        }

        provincias.forEach(provincia -> provincia.setPais(null));

        return provincias;
    }

    @Transactional
    public List<Ciudad> buscarCiudades(Integer idProvincia) {
        String jql = "from Ciudad where id_provincia = :idProvincia";
        TypedQuery<Ciudad> q = EntityManagerHelper.getEntityManager().createQuery(jql, Ciudad.class)
                .setParameter("idProvincia", idProvincia);

        List<Ciudad> ciudades;
        try {
            ciudades = q.getResultList();
        } catch (NoResultException e) {
            ciudades = new ArrayList<>();
        }

        ciudades.forEach(ciudad -> ciudad.setProvincia(null));

        return ciudades;
    }

    @Transactional
    public Pais buscarPais(Integer idPais) {
        return this.paisRepositorio.buscar(idPais);
    }

    @Transactional
    public Provincia buscarProvincia(Integer idProvincia) {
        return this.provinciaRepositorio.buscar(idProvincia);
    }

    @Transactional
    public Ciudad buscarCiudad(Integer idCiudad) {
        return this.ciudadRepositorio.buscar(idCiudad);
    }
}
