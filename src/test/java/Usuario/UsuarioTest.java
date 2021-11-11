package Usuario;

import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOMemoria;
import Domain.Entities.Entidades.DataObject.DOEntidadJuridica;
import Domain.Entities.Entidades.EntidadJuridica;
import Security.PasswordValidator.PasswordInvalidaException;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.Permiso;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.Usuarios.Rol;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class UsuarioTest {

    private Repositorio<Usuario> repoUsuarios;

    @Before
    public void before()  {
        this.repoUsuarios = new Repositorio<>(new DAOMemoria<>());

        Usuario usuario1 = new Usuario("Usuario 1");
        Usuario usuario2 = new Usuario("Usuario 2");
        usuario1.setId(1);
        usuario2.setId(2);

        Rol rolUsuario = new Rol();
        rolUsuario.setId(1);
        rolUsuario.addPermisos(
                new Permiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA.getIdPermiso()),
                new Permiso(PermisoUsuario.ABM_ENTIDAD_BASE.getIdPermiso()),
                new Permiso(PermisoUsuario.ABM_CATYCRI.getIdPermiso()));

        usuario1.addRoles(rolUsuario);

        this.repoUsuarios.agregar(usuario1);
        this.repoUsuarios.agregar(usuario2);
    }

    @Test
    public void crear_usuario_admin_se_agrega_en_entidad() throws NoTienePermisoException {
        Usuario usuario = this.repoUsuarios.buscar(1);

        DOEntidadJuridica doEntidadJuridica = new DOEntidadJuridica(usuario, "Empresa turbia", "Razones turbias", "Cuit falso", null, "", null );

        EntidadJuridica entidadJuridica = new EntidadJuridica(doEntidadJuridica);

        entidadJuridica.addUsuario(usuario);

        List<Usuario> usuariosEntidad = entidadJuridica.getUsuarios();

        assertTrue(usuariosEntidad.contains(usuario));
    }

    @Test
    public void crear_usuario_normal_no_tiene_permisos_admin() {
        Usuario usuario = this.repoUsuarios.buscar(2);

        assertFalse(usuario.tienePermiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA));
    }

}
