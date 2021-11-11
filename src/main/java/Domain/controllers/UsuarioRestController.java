package Domain.controllers;

import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.daos.Entities.UsuarioRepository;
import com.google.gson.Gson;
import server.ServerRoutes;
import spark.Request;
import spark.Response;

public class UsuarioRestController {
    private UsuarioRepository repoUsuario;

    public UsuarioRestController() {
        this.repoUsuario = new UsuarioRepository();
    }

    public String mostrar(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Usuario usuarioEliminar = this.repoUsuario.buscar(Integer.parseInt(request.params(ServerRoutes.PARAM_ID)));

        Gson gson = new Gson();
        String json = gson.toJson(usuarioEliminar);

        response.type("application/json");

        return json;
    }
}
