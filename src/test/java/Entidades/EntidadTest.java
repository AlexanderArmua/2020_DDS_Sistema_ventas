package Entidades;

import Domain.Entities.Entidades.EntidadJuridica;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOMemoria;
import Domain.Entities.Entidades.DataObject.DOEntidadJuridica;
import Domain.Entities.Usuarios.NoTienePermisoException;
import Domain.Entities.Usuarios.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.assertTrue;

public class EntidadTest {

    private Repositorio<Usuario> repoUsuarios = null;

    @Before
    public void before() {
        Usuario usuario = new Usuario( "Nombre de usuario");
        //usuario.addRoles(new RolCreadoEntidadesJuridicas());

        repoUsuarios = new Repositorio<>(new DAOMemoria<>(new LinkedList<>()));

        repoUsuarios.agregar(usuario);
    }

    @Test
    public void no_puede_crear_entidades_base_usuario_base() {
        Exception exception = new Exception();
        try {
            DOEntidadJuridica doEntidadJuridica = new DOEntidadJuridica(repoUsuarios.buscar(0),"EntidadJuridicaPrueba", "razon social prueba", "12314245", null, "", null);
            new EntidadJuridica(doEntidadJuridica);
        } catch (NoTienePermisoException e) {
            exception = e;
        }

        String expectedMessage = "El usuario no tiene permiso para crear una entidades base";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
