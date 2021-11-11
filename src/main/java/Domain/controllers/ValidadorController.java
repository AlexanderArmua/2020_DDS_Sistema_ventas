package Domain.controllers;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Entidades.EntidadJuridica;
import Domain.Entities.Operaciones.Operacion.Operacion;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.Validaciones.ValidacionCantPresupuestos;
import Domain.Entities.Validaciones.ValidacionCriterioSeleccion;
import Domain.Entities.Validaciones.ValidacionUsoPresupuesto;
import Domain.Entities.Validaciones.Validador;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.EntidadRepository;
import Util.Configuration.Configuration;
import Util.Tasks.ValidadorOperacionEgresoTask;
import server.ServerRoutes;
import spark.Request;
import spark.Response;
import static Util.Configuration.Configuration.validadorTask;


public class ValidadorController {

    private final Repositorio<EntidadJuridica> entidadRepository;

    public ValidadorController() {
        this.entidadRepository = new Repositorio<>(new DAOHibernate<>(EntidadJuridica.class));
    }


    public Response ejecutarValidador(Request request, Response response){
        LoginController.estaLogueado(request, response);

        Usuario usuario = LoginController.obtenerUsuarioLogueado(request);

        // TODO: Puede fallar si el usuario esta asociado a una entidad base
        EntidadJuridica entidadUsuario = this.entidadRepository.buscar(usuario.getEntidad().getId());

        Configuration.validadorTask.ejecutarParaEntidad(entidadUsuario);

        response.redirect(ServerRoutes.MENSAJES);

        return response;
    }
}
