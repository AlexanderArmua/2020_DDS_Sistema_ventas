package Domain.controllers;

import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Rol;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.daos.Entities.UsuarioRepository;
import Util.Configuration.Configuration;
import com.google.gson.Gson;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.JsonResponse;

import java.util.HashMap;
import java.util.Map;

public class LoginController {
    private final UsuarioRepository repoUsuario;

    private static final String queryParamUsuario = "username";
    private static final String queryParamContrasenna = "password";

    private static final String keyUsuarioActual = "usuario_actual";

    public LoginController() {
        this.repoUsuario = new UsuarioRepository();
    }

    public ModelAndView mostrar(Request request, Response response) {
        if (estaGuardadoElUsuarioEnSesion(request)) {
            response.redirect(ServerRoutes.HOME);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("query_param_usuario", queryParamUsuario);
        parametros.put("query_param_contrasenna", queryParamContrasenna);

        parametros.put("login_seguro", Configuration.LOGIN_SEGURO);

        return new ModelAndView(parametros,"login.hbs");
    }

    public String iniciarSesion(Request request, Response response) {
        LoginUsuario user = new Gson().fromJson(request.body(), LoginUsuario.class);
        response.type("application/json");

        String nombreUsuario = user.getUsuario();
        String contrasenna = user.getContrasenna();

        if (!Configuration.LOGIN_SEGURO) {
            request.session().attribute(keyUsuarioActual, new Usuario());

            return JsonResponse.buildJson(true, ServerRoutes.HOME, false, "");
        }

        Usuario usuario = this.repoUsuario.obtenerUsuario(nombreUsuario, contrasenna);

        if (usuario != null) {
            request.session().attribute(keyUsuarioActual, usuario);

            return JsonResponse.buildJson(true, ServerRoutes.HOME, false, "");
        } else {
            return JsonResponse.buildJson(false, "", true, "Ha ocurrido un error con el usuario o la contraseña");
        }
    }

    public Response desconectarSesion(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        request.session().removeAttribute(keyUsuarioActual);

        response.redirect(ServerRoutes.LOGIN);

        return response;
    };

    public static void estaLogueado(Request request, Response response) {
        if (!estaGuardadoElUsuarioEnSesion(request)) {
            response.redirect(ServerRoutes.LOGIN);
        }
    };

    public static Usuario obtenerUsuarioLogueado(Request request) {
        if (estaGuardadoElUsuarioEnSesion(request)) {
            return request.session().attribute(keyUsuarioActual);
        }

        return null;
    }

    private static boolean estaGuardadoElUsuarioEnSesion(Request request) {
        return request.session().attribute(keyUsuarioActual) != null;
    }

    private static boolean esSuperUsuario(Usuario usuario) {
        Rol rol = new Rol();
        rol.setId(1); // TODO: RRE HARDCODEADO ESTO MEJORARLO
        return usuario.tieneRol(rol);
    }

}