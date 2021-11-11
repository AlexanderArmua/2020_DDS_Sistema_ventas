package Entidades;
import Domain.Entities.Entidades.EntidadBase;
import Domain.Entities.Entidades.EntidadJuridica;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOMemoria;
import Domain.Entities.Entidades.DataObject.DOEntidadJuridica;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.Permiso;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.Usuarios.Rol;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class EntidadJuridicaTest {

    private Repositorio<Usuario> repoUsuarios;

    @Before
    public void before() {
        repoUsuarios = new Repositorio<>(new DAOMemoria<>());

        Usuario usuario = new Usuario( "Nombre de usuario");
        usuario.setId(1);
        Rol rolUsuario1 = new Rol();
        rolUsuario1.setId(1);
        rolUsuario1.addPermisos(
                new Permiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA.getIdPermiso()),
                new Permiso(PermisoUsuario.ABM_ENTIDAD_BASE.getIdPermiso())
        );
        usuario.addRoles(rolUsuario1);

        repoUsuarios.agregar(usuario);

        Usuario usuario2 = new Usuario( "Alexander");
        usuario2.setId(2);
        Rol rolUsuario2 = new Rol();
        rolUsuario2.setId(2);
        rolUsuario2.addPermisos(new Permiso(PermisoUsuario.ABM_ENTIDAD_BASE.getIdPermiso()));
        usuario2.addRoles(rolUsuario2);

        repoUsuarios.agregar(usuario2);
    }

    @Test
    public void agregar_entidad_base_a_entidad_juridica() throws NoTienePermisoException {

        DOEntidadJuridica doEntidadJuridica = new DOEntidadJuridica(repoUsuarios.buscar(1),"EntidadJuridicaPrueba", "razon social prueba", "12314245", null, "", null);

        EntidadJuridica entidadJuridica = new EntidadJuridica(doEntidadJuridica);
        EntidadBase entidadBase = new EntidadBase(repoUsuarios.buscar(1), "Entidad Base Prueba", "descripcion");

        entidadJuridica.addEntidadBase(entidadBase);
        boolean contieneEntBase = entidadJuridica.getEntidadesBase().contains(entidadBase);
        
        assertTrue(contieneEntBase);
    }

    @Test
    public void no_puede_crear_entidades_juridicas_usuario_base() {
        Exception exception = new Exception();
        try {

            DOEntidadJuridica doEntidadJuridica = new DOEntidadJuridica(repoUsuarios.buscar(2),"EntidadJuridicaPrueba", "razon social prueba", "12314245", null, "", null);
            new EntidadJuridica(doEntidadJuridica);

        } catch (NoTienePermisoException e) {
            exception = e;
        }

        String expectedMessage = "El usuario no tiene permiso para crear una entidades jurídicas";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
