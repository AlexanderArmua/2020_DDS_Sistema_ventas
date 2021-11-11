package Domain.controllers;
import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.Item.TipoItem;
import Domain.Repositories.daos.Entities.ItemRepository;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemController {

    private final ItemRepository itemRepository;

    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamDetalle = "detalle";
    private static final String queryParamTipo = "tipo";


    public ItemController() {
        this.itemRepository = new ItemRepository();
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        List<Item> items = this.itemRepository.buscarTodos();

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        parametros.put("items", items);

        return new ModelAndView(parametros, "items.hbs");
    }

    public ModelAndView pantallaCrearItem(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_detalle", queryParamDetalle);
        parametros.put("query_param_tipo", queryParamTipo);

        return new ModelAndView(parametros, "item.hbs");
    }

    public ModelAndView pantallaEditarItem(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        int idItem = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));
        Item item = this.itemRepository.buscar(idItem);

        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_detalle", queryParamDetalle);
        parametros.put("query_param_tipo", queryParamTipo);

        return new ModelAndView(parametros, "item.hbs");
    }

    public Response crearYEditarItem(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        int idItem = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        Item item = idItem == 0 ? new Item() : this.itemRepository.buscar(idItem);

        item.setDescripcion(paramToStringInt(request.queryParams(queryParamDescripcion)));
        item.setDetalle(paramToStringInt(request.queryParams(queryParamDetalle)));
        item.setTipoItem(parsearTipoItem(request.queryParams(queryParamTipo)));

        if(idItem == 0) {
            this.itemRepository.agregar(item);
        } else {
            this.itemRepository.modificar(item);
        }

        response.redirect(ServerRoutes.ITEMS);

        return response;
    }

    public TipoItem parsearTipoItem(String tipo){
        switch (tipo){
            case "servicio":
                return TipoItem.SERVICIO;
            case "producto":
                return TipoItem.PRODUCTO;
            default:
                return TipoItem.PRODUCTO;
        }
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("items_route", ServerRoutes.ITEMS);

        parametros.put("items_route_crear", ServerRoutes.ITEM_CREAR);

        return parametros;
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

}
