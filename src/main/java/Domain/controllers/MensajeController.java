package Domain.controllers;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.daos.Entities.MensajeRepository;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MensajeController {

    private final MensajeRepository mensajeRepository;

    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamFecha = "fecha";
    private static final String queryParamIdMensaje = "idMensaje";
    private static final String queryParamTitulo = "titulo";



    public MensajeController() {
        this.mensajeRepository = new MensajeRepository();
    }

    public ModelAndView mostrarTodos(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        Usuario usuario = LoginController.obtenerUsuarioLogueado(request);

        List<Mensaje> mensajesRepo = mensajeRepository.obtenerMensajes(usuario);

        List<MensajeLeer> mensajes = this.armarListaParaMensajeLeer(mensajesRepo,usuario);

        Map<String, Object> parametros = this.obtenerParametrosDefault();

        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.EJECUTAR_VALIDADOR_TRANSPARENCIA)) {
            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
            parametros.put("mensajes", mensajes);
            parametros.put("operacion_view_egreso", ServerRoutes.OPERACIONES_EGRESOS);
        }
        else
        {
            response.redirect(ServerRoutes.HOME);
        }
        return new ModelAndView(parametros, "mensajes.hbs");
    }



    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_id_mensaje",queryParamIdMensaje);

        parametros.put("home_route", ServerRoutes.HOME);
        parametros.put("mensaje_route", ServerRoutes.MENSAJES);
        parametros.put("mensaje_route_crear", ServerRoutes.MENSAJE_CREAR);
        parametros.put("leer", ServerRoutes.LEER);

        return parametros;
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

    public Response leerMensaje(Request request, Response response){
        Usuario usuario = LoginController.obtenerUsuarioLogueado(request);
        int idMensaje = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));
        Mensaje mensaje= mensajeRepository.buscar(idMensaje);
        mensaje.leerMensaje(usuario);
        mensajeRepository.modificar(mensaje);
        return response;
    }

    private List<MensajeLeer> armarListaParaMensajeLeer(List<Mensaje> mensajes, Usuario usuario){
        List<MensajeLeer> mensajesLeer = mensajes.stream().map(mensaje -> new MensajeLeer(mensaje, !mensaje.fueLeidoPorUsuario(usuario))).collect(Collectors.toList());
        return mensajesLeer;
    }

}
