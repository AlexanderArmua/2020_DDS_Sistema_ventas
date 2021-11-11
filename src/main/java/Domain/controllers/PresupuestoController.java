package Domain.controllers;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Entities.Operaciones.DocumentosComerciales.TipoDocumentoComercial;
import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Presupuestos.ItemPresupuesto;
import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Presupuestos.PresupuestoDO;
import Domain.Entities.Presupuestos.PresupuestoItemsDO;

import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.*;
import Util.Archivador.Archivador;
import Util.Configuration.Configuration;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.JsonResponse;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class PresupuestoController {
    private final PresupuestoRepository repoPresupuesto;
    private final OperacionEgresoRepository operacionEgresoRepository;
    private final ProveedorRepository proveedorRepository;
    private final ItemPresupuestoRepository itemPresupuestoRepository;
    private final Archivador archivador;
    private final CategoriaRepository repoCategorias;

    private static final String queryValorTotal = "valorTotal";
    private static final String queryParamProveedor = "proveedor";
    private static final String queryParamOperacion = "operacionEgreso";
    private static final String queryParamItemPrecio = "itemPrecio";
    private static final String queryParamItemId = "itemId";
    private static final String queryParamDocumentoComercial = "documentoComercial";
    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamNumeroDocumentoComercial = "numeroDocumentoComercial";
    private static final String queryParamEnlaceDocumentoComercial = "enlaceDocumentoComercial";

    private static final String queryParamPermisoABM = "permisoABM";

    public PresupuestoController() {
        this.repoPresupuesto = new PresupuestoRepository();
        this.operacionEgresoRepository = new OperacionEgresoRepository();
        this.proveedorRepository = new ProveedorRepository();
        this.itemPresupuestoRepository = new ItemPresupuestoRepository();
        this.archivador = new Archivador();
        this.repoCategorias = new CategoriaRepository();
    }

    public ModelAndView mostrar(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = obtenerParametrosDefault();
        if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_PRESUPUESTO)) {

            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_PRESUPUESTO)) {
                parametros.put("permisoABM", queryParamPermisoABM);
            }
        }
        else {
            response.redirect(ServerRoutes.HOME);
        }
        List<Presupuesto> presupuestos = this.repoPresupuesto.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("presupuestos", presupuestos);

        return new ModelAndView(parametros, "presupuestos.hbs");
    }

    public ModelAndView crear(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        Map<String, Object> parametros = obtenerParametrosDefault();

        List<Proveedor> proveedores = this.proveedorRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));
        List<OperacionEgreso> egresos = this.operacionEgresoRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        parametros.put("presupuestoJSON", getJsonPresupuesto(new Presupuesto()));
        parametros.put("query_param_descPresupuesto", queryParamDescripcion);
        parametros.put("query_param_valor_total", queryValorTotal);
        parametros.put("egresos", egresos);
        parametros.put("proveedores", proveedores);
        parametros.put("proveedorSeleccionado", new Proveedor());

        parametros.put("tipoArchivo", "null");
        parametros.put("categoriaSeleccionada", this.armarListaCategoriasParaEditar(null, request));

        parametros.put("egresoSeleccionado", new OperacionEgreso());
        parametros.put("operaciones", getJsonListaItems(egresos));

        return new ModelAndView(parametros, "presupuesto.hbs");
    }

    public ModelAndView editar(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        int idPresupuesto = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Presupuesto presupuesto = this.repoPresupuesto.buscar(idPresupuesto);

        List<Proveedor> proveedores = this.proveedorRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        List<OperacionEgreso> egresos = this.operacionEgresoRepository.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        Map<String, Object> parametros = obtenerParametrosDefault();
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        parametros.put("presupuestoJSON", getJsonPresupuesto(presupuesto));
        parametros.put("query_param_descPresupuesto", queryParamDescripcion);
        parametros.put("presupuesto", presupuesto);
        parametros.put("query_param_valor_total", queryValorTotal);
        parametros.put("egresos", egresos);
        parametros.put("proveedores", proveedores);
        parametros.put("proveedorSeleccionado", presupuesto.getProveedor());
        parametros.put("egresoSeleccionado", presupuesto.getOperacionEgreso());
        parametros.put("operaciones", getJsonListaItems(egresos));

        parametros.put("tipoArchivo", presupuesto.getDocumentoComercial() != null ? presupuesto.getDocumentoComercial().getTipoDocumentoComercial() != null ? presupuesto.getDocumentoComercial().getTipoDocumentoComercial().toString().toLowerCase() : "null" : "null");

        if(presupuesto.getDocumentoComercial() != null && presupuesto.getDocumentoComercial().getNombreArchivo() != null) {
            String extension = FilenameUtils.getExtension(presupuesto.getDocumentoComercial().getNombreArchivo());
            if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")){
                byte[] encoded = Base64.getEncoder().encode(archivador.getImage(request, response, presupuesto.getOperacionEgreso().getId() + "/" + presupuesto.getDocumentoComercial().getNombreArchivo()));
                parametros.put("imagen", "data:" + archivador.getExtensionFile(presupuesto.getDocumentoComercial().getNombreArchivo()) + ";base64," + new String(encoded));
            } else if (extension.equals("pdf")){
                byte[] inFileBytes = Files.readAllBytes(Paths.get(Configuration.RUTA_ARCHIVOS + presupuesto.getOperacionEgreso().getId() + "/" + presupuesto.getDocumentoComercial().getNombreArchivo()));
                byte[] encoded = Base64.getEncoder().encode(inFileBytes);
                parametros.put("pdf", new String(encoded));
            }
        }

        parametros.put("categoriaSeleccionada", this.armarListaCategoriasParaEditar(presupuesto.getOperacionEgreso().getCategorias(), request));

        return new ModelAndView(parametros, "presupuesto.hbs");
    }

    public String crearPresupuesto(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Presupuesto presupuestoCrear = new Presupuesto();

        this.actualizarPresupuesto(presupuestoCrear, request);

        this.repoPresupuesto.agregar(presupuestoCrear);

        return JsonResponse.buildJson(false, "", false, "", String.valueOf(presupuestoCrear.getId()));
    }

    public String editarPresupuesto(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        int idPresupuesto = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));
        Presupuesto presupuestoEditar = this.repoPresupuesto.buscar(idPresupuesto);
        if (presupuestoEditar == null) {
            return JsonResponse.buildJson(false, "", true, "Este presupuesto no existe");
        }

        this.actualizarPresupuesto(presupuestoEditar, request);

        this.repoPresupuesto.modificar(presupuestoEditar);

        return JsonResponse.buildJson(false, "", false, "", String.valueOf(presupuestoEditar.getId()));
    }


    private void actualizarPresupuesto(Presupuesto presupuesto, Request request) {
        Proveedor proveedor = this.proveedorRepository.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamProveedor))));
        presupuesto.setProveedor(proveedor);

        OperacionEgreso egreso = this.operacionEgresoRepository.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamOperacion))));
        presupuesto.setOperacionEgreso(egreso);

        this.itemPresupuestoRepository.deleteItemsPresupuesto(presupuesto.getId());

        List<ItemPresupuesto> itemsNuevos = new ArrayList<>();
        for (int i = 1; i <= egreso.getItems().size(); i++) {
            final int j = i;
            Item item = egreso.getItems().stream().filter(it -> it.getId() == Integer.parseInt(paramToStringInt(request.queryParams(queryParamItemId + j)))).findFirst().orElse(new Item());
            ItemPresupuesto itemPresupuesto = new ItemPresupuesto();
            itemPresupuesto.setPrecio(Float.parseFloat(paramToStringInt(request.queryParams(queryParamItemPrecio + i))));
            itemPresupuesto.setCantidad(item.getCantidad());
            itemPresupuesto.setItem(item);
            itemPresupuesto.setPresupuesto(presupuesto);
            itemsNuevos.add(itemPresupuesto);
        }
        if(itemsNuevos==null){

        }
        presupuesto.setItems(itemsNuevos);
        presupuesto.setValorTotal(presupuesto.getItems().stream().map(o -> o.getCantidad() * o.getPrecio()).reduce(0f, (a, b) -> a + b));
        presupuesto.setDescripcion(request.queryParams(queryParamDescripcion));

        int numeroDocumento = Integer.parseInt(paramToStringInt(request.queryParams(queryParamNumeroDocumentoComercial)));
        String enlaceDocument = request.queryParams(queryParamEnlaceDocumentoComercial);

        if (numeroDocumento != 0 || (enlaceDocument != null && !enlaceDocument.isEmpty())) {
            DocumentoComercial doc = presupuesto.getDocumentoComercial() == null ? new DocumentoComercial() : presupuesto.getDocumentoComercial();

            if (numeroDocumento != 0) {
                doc.setNumero(numeroDocumento);
            }
            if (enlaceDocument != null && !enlaceDocument.isEmpty()) {
                doc.setEnlaceExterno(enlaceDocument);
                if(doc.getNombreArchivo() != null && !doc.getNombreArchivo().isEmpty()) {
                    try {
                        archivador.eliminarArchivo(presupuesto.getOperacionEgreso().getId() + "/" + doc.getNombreArchivo());
                    } catch (Exception e){
                        // creo que el eliminar archivo no tira excepcion, pero por las dudas le dejo esto
                    }
                    doc.setNombreArchivo(null);
                }
                doc.setTipoDocumentoComercial(TipoDocumentoComercial.ENLACE_EXTERNO);
            }

            presupuesto.setDocumentoComercial(doc);
        }

    }

    public Response subirDocumento(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        int idPresupuesto = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        Presupuesto presupuesto = idPresupuesto == 0 ? new Presupuesto() : this.repoPresupuesto.buscar(idPresupuesto);

        DocumentoComercial docNuevo = this.archivador.obtenerDocumentoDeRequest(request, presupuesto.getOperacionEgreso().getId(), queryParamDocumentoComercial, "Presupuesto_" + idPresupuesto);
        if (docNuevo != null) {
            if (presupuesto.getDocumentoComercial() != null) {
                archivador.eliminarArchivo(presupuesto.getOperacionEgreso().getId() + "/" + presupuesto.getDocumentoComercial().getNombreArchivo());
                presupuesto.getDocumentoComercial().setNombreArchivo(docNuevo.getNombreArchivo());
            } else {
                presupuesto.setDocumentoComercial(docNuevo);
            }
            presupuesto.getDocumentoComercial().setTipoDocumentoComercial(TipoDocumentoComercial.ARCHIVO_ADJUNTO);
            presupuesto.getDocumentoComercial().setEnlaceExterno(null);
            this.repoPresupuesto.modificar(presupuesto);
        }

        response.redirect(ServerRoutes.PRESUPUESTOS);

        return response;
    }

    public HttpServletResponse downloadImage(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        int idPresupuesto = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));
        Presupuesto presupuesto = idPresupuesto == 0 ? new Presupuesto() : this.repoPresupuesto.buscar(idPresupuesto);

        return this.archivador.downloadImage(request, response, presupuesto.getOperacionEgreso().getId() + "/" + presupuesto.getDocumentoComercial().getNombreArchivo());
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_proveedor", queryParamProveedor);
        parametros.put("query_param_egreso", queryParamOperacion);
        parametros.put("query_param_item_precio", queryParamItemPrecio);
        parametros.put("query_param_item_id", queryParamItemId);
        parametros.put("query_param_documento_comercial", queryParamDocumentoComercial);
        parametros.put("query_param_descPresupuesto", queryParamDescripcion);
        parametros.put("query_param_numero_documento", queryParamNumeroDocumentoComercial);
        parametros.put("query_param_enlace_documento", queryParamEnlaceDocumentoComercial);

        parametros.put("archivador_subida", ServerRoutes.ARCHIVADOR_PRESUPUESTO);
        parametros.put("descargar_archivo", ServerRoutes.DESCARGAR_PRESUPUESTO);
        parametros.put("presupuestos_route", ServerRoutes.PRESUPUESTOS);
        parametros.put("presupuestos_route_crear", ServerRoutes.PRESUPUESTOS_CREAR);

        return parametros;
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

    private String getJsonListaItems(List<OperacionEgreso> ops) {
        List<PresupuestoItemsDO> lista = new ArrayList<>();

        try{
            for (int i = 0; i < ops.size(); i++) {
                PresupuestoItemsDO presupItems = new PresupuestoItemsDO(ops.get(i).getId(), ops.get(i).getItems(), ops.get(i).getCategorias());
                lista.add(presupItems);
            }
            Gson gson = new Gson();
            return gson.toJson(lista);        }

        catch (Exception e){
            return JsonResponse.buildJson(false, "", true, "Error obteniendo los items del presupuesto");
        }
    }

    private String getJsonPresupuesto(Presupuesto presupuesto) {
        try{
            return new Gson().toJson(new PresupuestoDO(presupuesto.getId(), presupuesto.getItems()));
        }
        catch (Exception e){
            return JsonResponse.buildJson(false, "", true, "Error obteniendo el prespuesto");
        }
    }

    private List<CategoriaEditar> armarListaCategoriasParaEditar(List<Categoria> categoriasSeleccionadas, Request request) {
        List<Categoria> categorias = this.repoCategorias.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));
        List<CategoriaEditar> categoriasEditar = categorias.stream().map(categoria -> new CategoriaEditar(categoria, false)).collect(Collectors.toList());

        if (categoriasSeleccionadas != null) {
            categoriasEditar.forEach(categoria -> categoria.setSeleccionada(categoriasSeleccionadas.stream().anyMatch(seleccionada -> seleccionada.getId() == categoria.getCategoria().getId())));
        }

        return categoriasEditar;
    }


}
