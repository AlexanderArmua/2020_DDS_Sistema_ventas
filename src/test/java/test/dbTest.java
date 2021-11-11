package test;

import Domain.Entities.Usuarios.Permiso;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Rol;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.daos.Entities.RolRepository;
import Domain.Repositories.daos.Entities.UsuarioRepository;
import org.junit.Assert;
import org.junit.Before;
import java.util.List;


public class dbTest {

    UsuarioRepository repositorioUsuarios = new UsuarioRepository();
    RolRepository repositorioRoles = new RolRepository();
    List<Rol> todosLosRoles;

    @Before
    public void before() {
        Usuario usuario1 = new Usuario("Macarena");
        Usuario usuario2 = new Usuario("Alexander");

        Rol rolAdministrador = new Rol();
        rolAdministrador.setNombre("admin");
        Rol rolNormal = new Rol();
        rolNormal.setNombre("normal");

        rolAdministrador.addPermisos(
                new Permiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA.getIdPermiso()),
                new Permiso(PermisoUsuario.ABM_ENTIDAD_BASE.getIdPermiso()),
                new Permiso(PermisoUsuario.ABM_CATYCRI.getIdPermiso())
        );

        rolNormal.addPermisos(
                new Permiso(PermisoUsuario.ABM_CATYCRI.getIdPermiso())
        );

        repositorioRoles.agregar(rolAdministrador);

        todosLosRoles = repositorioRoles.buscarTodos();
        usuario1.setRoles(todosLosRoles);

        repositorioRoles.agregar(rolNormal);

        todosLosRoles = repositorioRoles.buscarTodos();
        usuario2.setRoles(todosLosRoles);

        //repositorioUsuarios.crearUsuario(usuario1, "44MMnnpp??,-.1");
        //repositorioUsuarios.crearUsuario(usuario2, "44MMnp??,-.1");

    }
/*
    @Test
    public void recuperarUsuario() {
        Usuario usuario0 = (Usuario) repositorioUsuarios.buscar(1);
        Assert.assertEquals("Macarena", usuario0.getUsuario());
    }*/


//    @Test

    public void agregarUsuario() {
        Usuario usuarioAlan = new Usuario("Alan");
        usuarioAlan.setId(2);

        repositorioUsuarios.agregar(usuarioAlan);

        Usuario usuario0 = (Usuario) repositorioUsuarios.buscar(2);
        Assert.assertEquals("Alan", usuario0.getUsuario());

    }

}
