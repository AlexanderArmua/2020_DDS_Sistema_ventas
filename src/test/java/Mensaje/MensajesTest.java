package Mensaje;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOMemoria;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.daos.Entities.MensajeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class MensajesTest {

    Usuario usuario ;
    MensajeRepository repoMensajes = null;
    Repositorio<Usuario> repoUsuarios = null;

    @Before
    public void before() {
        inicializarRepoMensajes(LocalDateTime.now());

        usuario = new Usuario("Jose");
        usuario.setMensajes(repoMensajes.buscarTodos());

        repoUsuarios = new Repositorio<>(new DAOMemoria<>(new LinkedList<>()));

        repoUsuarios.agregar(usuario);
    }

    @Test
    public void usuario_filtro_por_fecha_devuelve_mensajes() {
        LocalDateTime ahora = LocalDateTime.now();

        LocalDateTime ahoraDespues = ahora.minusMinutes(1);
        List<Mensaje> mensajesFiltrados = usuario.filtrarPorFecha(ahoraDespues);

        Assert.assertEquals(mensajesFiltrados.size(), repoMensajes.buscarTodos().size());
    }

    @Test
    public void usuario_filtro_por_fecha_distinta_no_devuelve_mensajes() {
        LocalDateTime ahora = LocalDateTime.now();

        LocalDateTime ahoraDespues = ahora.minusDays(10);
        List<Mensaje> mensajesFiltrados = usuario.filtrarPorFecha(ahoraDespues);

        Assert.assertEquals(mensajesFiltrados.size(), 0);
    }

    @Test
    public void usuario_filtro_por_leido_devuelve_mensajes() throws Exception {
        Usuario usuario = this.repoUsuarios.buscar(0);
        repoMensajes.buscarTodos().get(0).leerMensaje(usuario);

        List<Mensaje> mensajesLeidos = usuario.filtrarPorLeidos(usuario);

        Assert.assertEquals(mensajesLeidos.size(), 1);
    }


    private void inicializarRepoMensajes(LocalDateTime tiempo) {
        this.repoMensajes = new MensajeRepository(new DAOMemoria<>());

        repoMensajes.agregar(new Mensaje("Devuelve Leer Mensaje", tiempo));
        repoMensajes.agregar(new Mensaje("Devuelve Leer Mensaje 2", tiempo));
    }
}
