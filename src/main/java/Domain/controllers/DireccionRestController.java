package Domain.controllers;

import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;
import Domain.Repositories.daos.Entities.DireccionRepository;
import com.google.gson.Gson;
import server.ServerRoutes;
import spark.Request;
import spark.Response;

import java.util.List;

public class DireccionRestController {
    private final DireccionRepository direccionRepository;

    public DireccionRestController() {
        this.direccionRepository = new DireccionRepository();
    }

    public String listadoPaises(Request _, Response response) {
        List<Pais> paises = this.direccionRepository.buscarTodosPaises();

        Gson gson = new Gson();

        response.type("application/json");

        return gson.toJson(paises);
    }

    public String listadoProvincias(Request request, Response response) {
        Integer idPais = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        List<Provincia> provincias = this.direccionRepository.buscarProvincias(idPais);

        Gson gson = new Gson();

        response.type("application/json");

        return gson.toJson(provincias);
    }

    public String listadoCiudades(Request request, Response response) {
        Integer idCiudad = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        List<Ciudad> ciudades = this.direccionRepository.buscarCiudades(idCiudad);

        Gson gson = new Gson();

        response.type("application/json");

        return gson.toJson(ciudades);
    }

}
