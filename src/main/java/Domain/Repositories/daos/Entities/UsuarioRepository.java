package Domain.Repositories.daos.Entities;

import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Security.PasswordValidator.PasswordInvalidaException;
import Security.PasswordValidator.PasswordResult;
import Security.PasswordValidator.PasswordValidator;
import db.EntityManagerHelper;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


public class UsuarioRepository extends Repositorio<Usuario> {
    private PasswordValidator passwordValidator;

    public UsuarioRepository() {
        super(new DAOHibernate<>(Usuario.class));
        this.passwordValidator = new PasswordValidator();
    }

    @Transactional
    public Usuario obtenerUsuario(String username, String contrasenia) {
        String shaContrasenna256hex = Usuario.getPasswordHashed(contrasenia);

        String jql = "from Usuario where usuario = :username and contrasenia = :contrasenia";
        TypedQuery<Usuario> q = EntityManagerHelper.getEntityManager().createQuery(jql, Usuario.class)
                .setParameter("username", username)
                .setParameter("contrasenia", shaContrasenna256hex);

        Usuario usuario;
        try {
            usuario = q.getSingleResult();
        } catch (NoResultException e) {
            usuario = null;
        }

        return usuario;
    }

    @Transactional
    public void crearUsuario(Usuario usuario, String contraseniaOriginal) throws Exception {
        PasswordResult passwordResult = this.passwordValidator.getSecurityPercentage(contraseniaOriginal);
        if (!passwordResult.isValid()) {
            throw new PasswordInvalidaException(passwordResult.getSecurityFails());
        }

        String shaContrasenna256hex = Usuario.getPasswordHashed(contraseniaOriginal);

        EntityManagerHelper.getEntityManager().getTransaction().begin();

        //TODO: FALTA AGREGAR LA QUERY PARA QUE SUME LOS OTROS CAMPOS DEL USUARIO
        EntityManagerHelper.getEntityManager()
                .createNativeQuery("INSERT INTO usuario (contrasenia, usuario) VALUES (?,?)")
                .setParameter(1, shaContrasenna256hex)
                .setParameter(2, usuario.getUsuario())
                .executeUpdate();

        // TODO: INSERTAR LOS ROLES

        EntityManagerHelper.getEntityManager().getTransaction().commit();

        this.obtenerUsuario(usuario.getUsuario(), contraseniaOriginal);
    }

    public List<Usuario> buscarUsuariosDeEntidad(int idEntidad){
        String query = "select u from Usuario u where u.entidad.id = :idEntidad";
        try {
            return EntityManagerHelper.getEntityManager().createQuery(query).setParameter("idEntidad", idEntidad).getResultList();
        } catch(Exception e) {
            return new ArrayList<>();
        }
    }

}
