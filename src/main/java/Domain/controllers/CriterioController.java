package Domain.controllers;

import Domain.Entities.Entidades.Criterio.Criterio;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Repositories.daos.Entities.CriterioRepository;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CriterioController {

    public final CriterioRepository criterioRepository;

    public static final String queryParamDescripcion = "descripcion";
    public static final String queryParamCriterioPadre = "criterioPadre";
    private static final String queryParamPermisoABM = "permisoABM";

    public CriterioController() {
        this.criterioRepository = new CriterioRepository();
    }

    public ModelAndView mostrarTodos(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        List<Criterio> criterios = this.criterioRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_CATYCRI)) {

            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_CATYCRI)) {
                parametros.put("permisoABM", queryParamPermisoABM);
            }
            parametros.put("criterios", criterios);
            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        }
        else{
            response.redirect(ServerRoutes.HOME);
        }
        return new ModelAndView(parametros, "criterios.hbs");
    }

    public ModelAndView pantallaCrearCriterio(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();
        List<Criterio> criterios = this.criterioRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("query_param_criterio_padre", queryParamCriterioPadre);
        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("criterios", criterios);
        parametros.put("criterioSeleccionado", new Criterio());

        return new ModelAndView(parametros, "criterio.hbs");
    }

    public ModelAndView pantallaEditarCriterio(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();
        int idCriterio = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));
        Criterio criterio = this.criterioRepository.buscar(idCriterio);
        List<Criterio> criterios = this.criterioRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_criterio_padre", queryParamCriterioPadre);

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        parametros.put("criterio", criterio);
        parametros.put("criterios", criterios);
        parametros.put("criterioSeleccionado", criterio.getPadre() != null ? criterio.getPadre() : new Criterio());

        return new ModelAndView(parametros, "criterio.hbs");
    }

    public Response crearYEditarCriterio(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idCriterio = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        Criterio criterio = idCriterio == 0 ? new Criterio() : this.criterioRepository.buscar(idCriterio);

        criterio.setDescripcion(paramToStringInt(request.queryParams(queryParamDescripcion)));

        Criterio criterioPadre = this.criterioRepository.buscar(Integer.valueOf(paramToStringInt(request.queryParams(queryParamCriterioPadre))));
        criterio.setPadre(criterioPadre);

        if(idCriterio == 0) {
            this.criterioRepository.agregar(criterio);
        } else {
            this.criterioRepository.modificar(criterio);
        }

        response.redirect(ServerRoutes.CRITERIOS);

        return response;
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("criterios_route", ServerRoutes.CRITERIOS);

        parametros.put("criterios_route_crear", ServerRoutes.CRITERIO_CREAR);

        return parametros;
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

}
