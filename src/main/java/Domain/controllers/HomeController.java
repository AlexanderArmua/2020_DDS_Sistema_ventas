package Domain.controllers;

import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class HomeController {
    public Response mostrar(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        response.redirect(ServerRoutes.HOME);

        return response;
    }

    public ModelAndView mostrarPantallaBienvenida(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        return new ModelAndView(parametros,"home.hbs");
    }
}
