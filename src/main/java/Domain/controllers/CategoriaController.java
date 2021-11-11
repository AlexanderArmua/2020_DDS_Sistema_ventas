package Domain.controllers;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Entidades.Criterio.Criterio;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Repositories.daos.Entities.CategoriaRepository;
import Domain.Repositories.daos.Entities.CriterioRepository;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final CriterioRepository criterioRepository;

    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamCriterio = "criterio";

    private static final String queryParamPermisoABM = "permisoABM";

    public CategoriaController() {
        this.categoriaRepository = new CategoriaRepository();
        this.criterioRepository = new CriterioRepository();
    }

    public ModelAndView mostrarTodos(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        List<Categoria> categorias = this.categoriaRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_CATYCRI)) {
            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_CATYCRI)) {
                parametros.put("permisoABM", queryParamPermisoABM);
            }
            parametros.put("categorias", categorias);
            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        }
        else
        {
            response.redirect(ServerRoutes.HOME);
        }
        return new ModelAndView(parametros, "categorias.hbs");
    }

    public ModelAndView pantallaCrearCategoria(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();
        List<Criterio> criterios= this.criterioRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_criterio", queryParamCriterio);
        parametros.put("criterios", criterios);
        parametros.put("criterioSeleccionado", new Criterio());

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        return new ModelAndView(parametros, "categoria.hbs");
    }

    public ModelAndView pantallaEditarCategoria(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();
        int idCategoria = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));
        Categoria categoria = this.categoriaRepository.buscar(idCategoria);
        List<Criterio> criterios = this.criterioRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_criterio", queryParamCriterio);

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        parametros.put("categoria", categoria);
        parametros.put("criterios", criterios);
        parametros.put("criterioSeleccionado", categoria != null ? categoria.getCriterio() != null ? categoria.getCriterio() : new Criterio() : new Criterio());

        return new ModelAndView(parametros, "categoria.hbs");
    }

    public Response crearYEditarCategoria(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idCategoria = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        Categoria categoria = idCategoria == 0 ? new Categoria() : this.categoriaRepository.buscar(idCategoria);

        categoria.setDescripcion(paramToStringInt(request.queryParams(queryParamDescripcion)));

        Criterio criterio = this.criterioRepository.buscar(Integer.valueOf(paramToStringInt(request.queryParams(queryParamCriterio))));
        categoria.setCriterio(criterio);

        if(idCategoria == 0) {
            this.categoriaRepository.agregar(categoria);
        } else {
            this.categoriaRepository.modificar(categoria);
        }

        response.redirect(ServerRoutes.CATEGORIAS);

        return response;
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("categorias_route", ServerRoutes.CATEGORIAS);

        parametros.put("categorias_route_crear", ServerRoutes.CATEGORIA_CREAR);

        return parametros;
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

}
