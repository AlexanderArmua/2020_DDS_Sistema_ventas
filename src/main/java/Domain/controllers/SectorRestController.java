package Domain.controllers;

import Domain.Entities.Entidades.Actividad;
import Domain.Entities.Entidades.Sector;
import Domain.Entities.Ubicacion.Pais;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import com.google.gson.Gson;
import server.ServerRoutes;
import spark.Request;
import spark.Response;

import java.util.List;

public class SectorRestController {
    private final Repositorio<Sector> sectorRepositorio;

    public SectorRestController() {
        this.sectorRepositorio = new Repositorio<>(new DAOHibernate<>(Sector.class));
    }

    public String listadoSectores(Request _, Response response) {
        List<Sector> sectores = this.sectorRepositorio.buscarTodos();
        sectores.forEach(sector -> {
            sector.setActividades(null);
            sector.setTipoSectores(null);
        });

        Gson gson = new Gson();

        response.type("application/json");

        return gson.toJson(sectores);
    }

    public String listadoActividades(Request request, Response response) {
        int idSector = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Sector sector = this.sectorRepositorio.buscarTodos().stream().filter(sector1 -> sector1.getId() == idSector).findAny().orElseGet(null);

        if (sector == null) {
            return "[]";
        }

        List<Actividad> actividades = sector.getActividades();
        actividades.forEach(actividad -> actividad.setSector(null));

        Gson gson = new Gson();

        response.type("application/json");

        return gson.toJson(actividades);
    }
}
