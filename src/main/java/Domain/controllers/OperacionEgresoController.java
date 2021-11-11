package Domain.controllers;

import Domain.Entities.BaseObject;
import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Entidades.Criterio.Criterio;
import Domain.Entities.Operaciones.DocumentosComerciales.DocumentoComercial;
import Domain.Entities.Operaciones.DocumentosComerciales.TipoDocumentoComercial;
import Domain.Entities.Operaciones.Item.Item;
import Domain.Entities.Operaciones.Item.TipoItem;
import Domain.Entities.Operaciones.MediosDePago.MedioDePago;
import Domain.Entities.Operaciones.MediosDePago.TipoMedioPago;
import Domain.Entities.Operaciones.Operacion.CriteriosDeSeleccion.TipoCriterioSeleccion;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Presupuestos.Moneda;
import Domain.Entities.Presupuestos.Presupuesto;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.daos.Entities.*;
import Util.Archivador.Archivador;
import Util.Configuration.Configuration;
import org.apache.commons.io.FilenameUtils;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.JsonResponse;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OperacionEgresoController {
    private final OperacionEgresoRepository repoEgresos;
    private final ProveedorRepository repoProveedores;
    private final MediosDePagoRepository repoMediosDePago;
    private final CategoriaRepository repoCategorias;
    private final CriterioRepository repoCriterios;
    private final ItemRepository repoItems;
    private final Archivador archivador;
    private final UsuarioRepository revisores;
    private final MonedaRepository repoMoneda;
    private final PresupuestoRepository repoPresupuestos;

    private static final String queryparamDescEgreso = "descEgreso";
    private static final String queryParamFecha = "fecha";
    private static final String queryParamValorTotal = "valorTotal";
    private static final String queryParamPresupuestosOption = "presupuestosOption";
    private static final String queryParamCantidadPresupuestos = "cantidadPresupuestos";
    private static final String queryParamProveedor = "proveedor";
    private static final String queryParamMedioPago = "medioPago";
    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamCategorias = "categoriasInput";
    private static final String queryParamCriteriosFiltro = "criteriosFiltro";
    private static final String queryParamCategoriasFiltro = "categoriasFiltro";
    private static final String queryParamFechaHasta = "fechaHasta";
    private static final String queryParamNombreResponsable = "responsable";
    private static final String queryParamNumeroComprobante = "numeroComprobante";
    private static final String queryParamCodigoSeguridad = "codigoSeguridad";
    private static final String queryParamFechaVencimiento = "fechaVencimiento";
    private static final String valorEfectivo = "efectivo";
    private static final String valorTarjetaDebito = "tarjetaDebito";
    private static final String valorTarjetaCredito = "tarjetaCredito";
    private static final String valorCheque = "cheque";
    private static final String queryParamCantidadTotalItems = "cantidadItems";
    private static final String queryParamItemDescripcion = "itemDescripcion";
    private static final String queryParamItemDetalle = "itemDetalle";
    private static final String queryParamItemCantidad = "itemCantidad";
    private static final String queryParamItemTipo = "itemTipo";
    private static final String queryParamItemId = "itemId";
    private static final String queryParamItemPrecio = "itemPrecio";
    private static final String queryParamDocumentoComercial = "documentoComercial";
    private static final String queryParamRevisor = "revisores";
    private static final String queryParamMoneda = "moneda";
    private static final String queryParamPresupuestoSeleccionado = "presupuestoSeleccionado";
    private static final String queryParamNumeroDocumentoComercial = "numeroDocumentoComercial";
    private static final String queryParamEnlaceDocumentoComercial = "enlaceDocumentoComercial";
    private static final String queryParamCriterioSeleccion = "criterioSeleccion";

    private static final String queryParamPermisoABM = "permisoABM";

    //private final List<Categoria> todasLasCategorias;
    //private final List<Usuario> todosLosUsuarios;



    public OperacionEgresoController() {
        this.revisores = new UsuarioRepository();
        this.repoProveedores = new ProveedorRepository();
        this.repoMediosDePago = new MediosDePagoRepository();
        this.repoEgresos = new OperacionEgresoRepository();
        this.repoCategorias = new CategoriaRepository();
        this.repoCriterios = new CriterioRepository();
        this.repoItems = new ItemRepository();
        this.archivador = new Archivador();
        this.repoMoneda = new MonedaRepository();
        this.repoPresupuestos = new PresupuestoRepository();
    }


    public ModelAndView mostrarTodosEgresos(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        List<Criterio> criterios = this.repoCriterios.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        int criterioIdSeleccionado = Integer.parseInt(paramToStringInt(request.queryParams(queryParamCriteriosFiltro)));
        int categoriaIdSeleccionada = Integer.parseInt(paramToStringInt(request.queryParams(queryParamCategoriasFiltro)));

        List<Categoria> categorias = this.repoCategorias.listarSegunCriterio(criterioIdSeleccionado);

        List<OperacionEgreso> egresos = this.repoEgresos.listarSegunCriterioYCategoria(criterioIdSeleccionado, categoriaIdSeleccionada,LoginController.obtenerUsuarioLogueado(request));

        egresos.sort((o1, o2) -> {
            if (o1.getFecha() == null || o2.getFecha() == null)
                return 0;
            return o1.getFecha().compareTo(o2.getFecha());
        });

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_OPERACION_EGRESO)) {

            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_OPERACION_EGRESO)) {
                parametros.put("permisoABM", queryParamPermisoABM);
            }
        }
        else {
            response.redirect(ServerRoutes.HOME);
        }
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("query_param_categorias_filtro", queryParamCategoriasFiltro);
        parametros.put("query_param_criterios_filtro", queryParamCriteriosFiltro);

        parametros.put("criterioSeleccionado", criterioIdSeleccionado);
        parametros.put("categoriaSeleccionada", categoriaIdSeleccionada);

        parametros.put("egresos", egresos);
        parametros.put("categorias", categorias);
        parametros.put("criterios", criterios);

        return new ModelAndView(parametros, "operacionesEgreso.hbs");
    }

    public ModelAndView mostrarOperacionEgreso(Request request, Response response) throws Exception{


        int idOperacion = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        OperacionEgreso operacion = idOperacion == 0 ? new OperacionEgreso() : this.repoEgresos.buscar(idOperacion);

        if(operacion != null){
            return pantallaEditarEgreso(request, response);
        } else {
            throw new Exception("Operacion no encontrada") ;
        }
    }

    public ModelAndView pantallaEditarEgreso(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        int idOperacion = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        OperacionEgreso operacion = this.repoEgresos.buscar(idOperacion);
        Map<String, Object> parametros = this.obtenerParametrosDefault();
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("operacion", operacion);
        parametros.put("editable", !operacion.isFueValidada() && operacion.getPresupuestos().isEmpty());

        String medioPagoSeleccionado = getMedioPagoSeleccionado(operacion.getMedioDePago());
        parametros.put("medioPagoSeleccionado", medioPagoSeleccionado);
        parametros.put("proveedorSeleccionado", operacion.getProveedor() != null ? operacion.getProveedor() : new Proveedor());
        //parametros.put("categoriaSeleccionada", operacion.getCategorias().size() > 0 ? operacion.getCategorias().get(0) : new Categoria());

        parametros.put("categoriaSeleccionada", this.armarListaCategoriasParaEditar(operacion.getCategorias(),request));
        parametros.put("revisorSeleccionado", this.armarListaUsuarioParaEditar(operacion.getRevisores(),request));

        parametros.put("monedaSeleccionado", operacion.getMoneda() != null ? operacion.getMoneda() : new Moneda());
        parametros.put("presupuestoSeleccionado", operacion.getPresupuestoElegido() != null ? operacion.getPresupuestoElegido() : new Presupuesto());
        parametros.put("tipoArchivo", operacion.getDocumentoComercial() != null ? operacion.getDocumentoComercial().getTipoDocumentoComercial() != null ? operacion.getDocumentoComercial().getTipoDocumentoComercial().toString().toLowerCase() : "null" : "null");
        parametros.put("criterioSeleccionSeleccionado", operacion.getTipoCriterioSeleccion() != null ? operacion.getTipoCriterioSeleccion().toString().replace("_", " ") : 0);


        parametros.put("proveedores", buscarProveedores(request));
        parametros.put("mediosPago", buscarMediosDePago());
        parametros.put("categorias", buscarCategorias(request));
        parametros.put("revisores", buscarUsuarios(request));
        parametros.put("moneda", buscarMonedas());
        parametros.put("presupuestos", buscarPresupuestos(idOperacion));
        parametros.put("listaCriteriosSeleccion", listarCriteriosSeleccion());

        if(operacion.getDocumentoComercial() != null && operacion.getDocumentoComercial().getNombreArchivo() != null) {
            String extension = FilenameUtils.getExtension(operacion.getDocumentoComercial().getNombreArchivo());
            if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")){
                byte[] encoded = Base64.getEncoder().encode(archivador.getImage(request, response, operacion.getId() + "/" + operacion.getDocumentoComercial().getNombreArchivo()));
                parametros.put("imagen", "data:" + archivador.getExtensionFile(operacion.getDocumentoComercial().getNombreArchivo()) + ";base64," + new String(encoded));
            } else if (extension.equals("pdf")){
                byte[] inFileBytes = Files.readAllBytes(Paths.get(Configuration.RUTA_ARCHIVOS + operacion.getId() + "/" + operacion.getDocumentoComercial().getNombreArchivo()));
                byte[] encoded = Base64.getEncoder().encode(inFileBytes);
                parametros.put("pdf", new String(encoded));
            }
        }

        return new ModelAndView(parametros, "operacionEgreso.hbs");
    }

    public String crearYEditarOperacionEgreso(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        OperacionEgreso operacion = idOperacion == 0 ? new OperacionEgreso() : this.repoEgresos.buscar(idOperacion);

        if(operacion.isFueValidada() || !operacion.getPresupuestos().isEmpty()) {
            return JsonResponse.buildJson(false, "", true, "Esta operacion no puede ser editada.");
        }

        String str = request.queryParams(queryParamFecha);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(str, formatter);
        } catch(Exception e) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            dateTime = LocalDateTime.parse(str, formatter);
            return JsonResponse.buildJson(false, "", true, e.getMessage());
        }

        operacion.setFecha(dateTime);

        if (request.queryParams(queryparamDescEgreso)==null){
            return JsonResponse.buildJson(false, "", true, "Complete la descripción");
        }
        operacion.setDescripcion(request.queryParams(queryparamDescEgreso));

        int cantidadPresupuestos = Integer.parseInt(paramToStringInt(request.queryParams(queryParamCantidadPresupuestos)));
        if(cantidadPresupuestos > 0) {
            operacion.setCantidadPresupuestoRequeridos(cantidadPresupuestos);
            operacion.setNecesitaPresupuesto(true);
        } else {
            operacion.setNecesitaPresupuesto(false);
        }

        Proveedor proveedor= this.repoProveedores.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamProveedor))));
        operacion.setProveedor(proveedor);

        Moneda moneda = this.repoMoneda.buscar(1);
        operacion.setMoneda(moneda);

        saveMedioPago(request, operacion);

        //operacion.setCategorias(this.obtenerCategoriasDelRequest(request));
        /*Categoria categoria = this.repoCategorias.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamCategorias))));
        //operacion.setCategorias(new ArrayList<>());
        operacion.addCategoroa(categoria);
*/
        operacion.setCategorias(this.obtenerCategoriasDelRequest(request));
        operacion.setRevisores(this.obtenerRevisoresDelRequest(request));

        if(Integer.parseInt(paramToStringInt(request.queryParams(queryParamCantidadTotalItems)))==0){
            return JsonResponse.buildJson(false, "", true, "La operación debe tener items");
        }
        saveItems(request, operacion);

        operacion.setValorTotal(operacion.getItems().stream().map(o -> o.getCantidad() * o.getPrecio()).reduce(0f, (a,b) -> a + b));

        int numeroDocumento = Integer.parseInt(paramToStringInt(request.queryParams(queryParamNumeroDocumentoComercial).toString()));
        String enlaceDocument = request.queryParams(queryParamEnlaceDocumentoComercial);

        if(numeroDocumento != 0 || (enlaceDocument != null && !enlaceDocument.isEmpty())) {
            DocumentoComercial doc = operacion.getDocumentoComercial() == null ? new DocumentoComercial() : operacion.getDocumentoComercial();

            if (numeroDocumento != 0) {
                doc.setNumero(numeroDocumento);
            }
            if (enlaceDocument != null && !enlaceDocument.isEmpty()) {
                doc.setEnlaceExterno(enlaceDocument);
                if(doc.getNombreArchivo() != null && !doc.getNombreArchivo().isEmpty()) {
                    try {
                        archivador.eliminarArchivo(operacion.getId() + "/" + doc.getNombreArchivo());
                    } catch (Exception e){
                        // creo que el eliminar archivo no tira excepcion, pero por las dudas le dejo esto
                    }
                    doc.setNombreArchivo(null);
                }
                doc.setTipoDocumentoComercial(TipoDocumentoComercial.ENLACE_EXTERNO);
            }

            operacion.setDocumentoComercial(doc);
        }

        operacion.setEntidad(LoginController.obtenerUsuarioLogueado(request).getEntidad());

        String criterioSeleccionSeleccionado = request.queryParams(queryParamCriterioSeleccion);
        if(TipoCriterioSeleccion.CRITERIO_MENOR_VALOR.toString().replace("_", " ").equals(criterioSeleccionSeleccionado)) {
            operacion.setTipoCriterioSeleccion(TipoCriterioSeleccion.CRITERIO_MENOR_VALOR);
        } else {
            operacion.setTipoCriterioSeleccion(null);
        }

        if(idOperacion == 0) {
            this.repoEgresos.agregar(operacion);
        } else {
            this.repoEgresos.modificar(operacion);
        }

        //response.redirect(ServerRoutes.ARCHIVADOR_EGRESO_SUBIR.replace(":" + ServerRoutes.PARAM_ID, String.valueOf(operacion.getId())));

        return JsonResponse.buildJson(false, "", false, "", String.valueOf(operacion.getId()));
    }

    public ModelAndView pantallaCrearEgreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();


        OperacionEgreso operacion = new OperacionEgreso();
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("operacion", operacion);
        parametros.put("editable", true);

        parametros.put("medioPagoSeleccionado", "null");
        parametros.put("proveedorSeleccionado", new Proveedor());
        //parametros.put("categoriaSeleccionada", new Categoria());
        parametros.put("categoriaSeleccionada", this.armarListaCategoriasParaEditar(null,request));
        parametros.put("revisorSeleccionado", this.armarListaUsuarioParaEditar(null,request));
        parametros.put("monedaSeleccionado", new Moneda());
        parametros.put("presupuestoSeleccionado", new Presupuesto());
        parametros.put("criterioSeleccionSeleccionado", 0);
        parametros.put("tipoArchivo", "null");
        parametros.put("proveedores", buscarProveedores(request));
        parametros.put("mediosPago", buscarMediosDePago());
        parametros.put("categorias", buscarCategorias(request));
        parametros.put("revisores", buscarUsuarios(request));
        parametros.put("moneda", buscarMonedas());
        parametros.put("presupuestos", new ArrayList<>());
        parametros.put("listaCriteriosSeleccion", listarCriteriosSeleccion());

        return new ModelAndView(parametros, "operacionEgreso.hbs");
    }

    public Response subirDocumento(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        OperacionEgreso operacion = idOperacion == 0 ? new OperacionEgreso() : this.repoEgresos.buscar(idOperacion);

        if(operacion.isFueValidada() || !operacion.getPresupuestos().isEmpty()) {
            throw new Exception("Esta operacion no puede ser editada.");
        }

        DocumentoComercial docNuevo = this.archivador.obtenerDocumentoDeRequest(request, idOperacion, queryParamDocumentoComercial, "Egreso");
        if (docNuevo != null) {
            if (operacion.getDocumentoComercial() != null) {
                archivador.eliminarArchivo(operacion.getId() + "/" + operacion.getDocumentoComercial().getNombreArchivo());
                operacion.getDocumentoComercial().setNombreArchivo(docNuevo.getNombreArchivo());
            } else {
                operacion.setDocumentoComercial(docNuevo);
            }
            operacion.getDocumentoComercial().setTipoDocumentoComercial(TipoDocumentoComercial.ARCHIVO_ADJUNTO);
            operacion.getDocumentoComercial().setEnlaceExterno(null);
            this.repoEgresos.modificar(operacion);
        }

        response.redirect(ServerRoutes.OPERACIONES_EGRESOS);

        return response;
    }

    public HttpServletResponse downloadImage(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));
        OperacionEgreso operacion = idOperacion == 0 ? new OperacionEgreso() : this.repoEgresos.buscar(idOperacion);

        return this.archivador.downloadImage(request, response, operacion.getId() + "/" + operacion.getDocumentoComercial().getNombreArchivo());
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_descEgreso",queryparamDescEgreso);
        parametros.put("query_param_valor", queryParamValorTotal);
        parametros.put("query_param_fecha", queryParamFecha);
        parametros.put("query_param_presupuestosOption", queryParamPresupuestosOption);
        parametros.put("query_param_cantidadPresupuestos", queryParamCantidadPresupuestos);
        parametros.put("query_param_proveedor", queryParamProveedor);
        parametros.put("query_param_medioPago", queryParamMedioPago);
        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_categoria", queryParamCategorias);
        parametros.put("query_param_fecha_hasta", queryParamFechaHasta);
        parametros.put("valorCheque", valorCheque);
        parametros.put("valorTarjetaCredito", valorTarjetaCredito);
        parametros.put("valorTarjetaDebito", valorTarjetaDebito);
        parametros.put("valorEfectivo", valorEfectivo);
        parametros.put("query_param_item_descripcion", queryParamItemDescripcion);
        parametros.put("query_param_item_detalle", queryParamItemDetalle);
        parametros.put("query_param_item_cantidad", queryParamItemCantidad);
        parametros.put("query_param_item_tipo", queryParamItemTipo);
        parametros.put("query_param_cantidad_total_items", queryParamCantidadTotalItems);
        parametros.put("query_param_item_id", queryParamItemId);
        parametros.put("query_param_item_precio", queryParamItemPrecio);
        parametros.put("query_param_nombre_responsable", queryParamNombreResponsable);
        parametros.put("query_param_numero_comprobante", queryParamNumeroComprobante);
        parametros.put("query_param_fecha_vencimiento", queryParamFechaVencimiento);
        parametros.put("query_param_codigo_seguridad", queryParamCodigoSeguridad);
        parametros.put("query_param_documento_comercial", queryParamDocumentoComercial);
        parametros.put("query_param_revisor", queryParamRevisor);
        parametros.put("query_param_moneda", queryParamMoneda);
        parametros.put("query_param_presupuesto", queryParamPresupuestoSeleccionado);
        parametros.put("query_param_numero_documento", queryParamNumeroDocumentoComercial);
        parametros.put("query_param_enlace_documento", queryParamEnlaceDocumentoComercial);
        parametros.put("query_param_criterio_seleccion", queryParamCriterioSeleccion);

        parametros.put("archivador_subida", ServerRoutes.ARCHIVADOR_EGRESO);
        parametros.put("descargar_archivo", ServerRoutes.DESCARGAR_EGRESO);

        parametros.put("operacion_egreso_crear", ServerRoutes.OPERACION_EGRESO_CREAR);
        parametros.put("operacion_egreso_editar", ServerRoutes.OPERACION_EGRESO_EDITAR);

        parametros.put("operacion_egreso", ServerRoutes.OPERACION_EGRESOS);

        parametros.put("operacion_view_egreso", ServerRoutes.OPERACIONES_EGRESOS);
        parametros.put("elegir_presupuesto", ServerRoutes.ELEGIR_PRESUPUESTO);


        return parametros;
    }

    private List<Proveedor> buscarProveedores(Request request) {
        return this.repoProveedores.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));
    }

    private List<Moneda> buscarMonedas(){
        return this.repoMoneda.buscarTodos();
    }

    private List<MedioDePago> buscarMediosDePago() {
        return this.repoMediosDePago.buscarTodos();
    }

    private List<Categoria> buscarCategorias(Request request) {
        return this.repoCategorias.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));
    }

    private List<Usuario> buscarUsuarios(Request request) {
        return this.revisores.buscarUsuariosDeEntidad(LoginController.obtenerUsuarioLogueado(request).getEntidad().getId());
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

    private void saveMedioPago(Request request, OperacionEgreso operacionEgreso) {
        MedioDePago medioDePago = operacionEgreso.getMedioDePago() != null ? operacionEgreso.getMedioDePago() : new MedioDePago();
        TipoMedioPago tmp;
        switch(request.queryParams(queryParamMedioPago)) {
            case valorTarjetaCredito: {
                tmp = TipoMedioPago.TARJETA_CREDITO;
                medioDePago.setNombreApellido(request.queryParams(queryParamNombreResponsable));
                medioDePago.setNumero(request.queryParams(queryParamNumeroComprobante));
                medioDePago.setVencimiento(request.queryParams(queryParamFechaVencimiento));
                medioDePago.setNumeroSeguridad(request.queryParams(queryParamCodigoSeguridad));
                break;
            }
            case valorTarjetaDebito: {
                tmp = TipoMedioPago.TARJETA_DEBITO;
                medioDePago.setNombreApellido(request.queryParams(queryParamNombreResponsable));
                medioDePago.setNumero(request.queryParams(queryParamNumeroComprobante));
                medioDePago.setVencimiento(request.queryParams(queryParamFechaVencimiento));
                medioDePago.setNumeroSeguridad(request.queryParams(queryParamCodigoSeguridad));
                break;
            }
            case valorCheque: {
                tmp = TipoMedioPago.CHEQUE;
                medioDePago.setNombreApellido(request.queryParams(queryParamNombreResponsable));
                medioDePago.setNumero(request.queryParams(queryParamNumeroComprobante));
                medioDePago.setVencimiento(request.queryParams(queryParamFechaVencimiento));
                break;
            }
            default: {
                tmp = TipoMedioPago.EFECTIVO;
                break;
            }
        }
        medioDePago.setTipoMedioPago(tmp);
        operacionEgreso.setMedioDePago(medioDePago);
    }

    private String getMedioPagoSeleccionado(MedioDePago mdp) {
        switch(mdp.getTipoMedioPago()) {
            case TARJETA_CREDITO: return valorTarjetaCredito;
            case CHEQUE: return valorCheque;
            case TARJETA_DEBITO: return valorTarjetaDebito;
            case EFECTIVO:
            default: return valorEfectivo;
        }
    }

    private void saveItems(Request request, OperacionEgreso operacion) {
        List<Item> itemsABorrar = operacion.getItems();
        operacion.setItems(new ArrayList<>());
        for(int i = 1; i <= Integer.parseInt(paramToStringInt(request.queryParams(queryParamCantidadTotalItems))); i++) {
            //int idItem = Integer.parseInt(paramToStringInt(request.queryParams(queryParamItemId + i)));
            //Item item = operacion.getItems().stream().filter(it -> it.getId() == idItem ).findFirst().orElse(new Item());
            Item item = new Item();

            item.setDescripcion(request.queryParams(queryParamItemDescripcion + i));
            item.setDetalle(request.queryParams(queryParamItemDetalle + i));
            item.setCantidad(Integer.parseInt(paramToStringInt(request.queryParams(queryParamItemCantidad + i))));
            item.setPrecio(Float.parseFloat(paramToStringInt(request.queryParams(queryParamItemPrecio + i))));
            if ("servicio".equals(request.queryParams(queryParamItemTipo + i))) {
                item.setTipoItem(TipoItem.SERVICIO);
            } else {
                item.setTipoItem(TipoItem.PRODUCTO);
            }

            operacion.addItem(item);
        }
        itemsABorrar.forEach(this.repoItems::eliminar);
    }

    private List<CategoriaEditar> armarListaCategoriasParaEditar(List<Categoria> categoriasSeleccionadas,Request request){
        List<CategoriaEditar> categoriasEditar = this.buscarCategorias(request).stream().map(categoria-> new CategoriaEditar(categoria, false)).collect(Collectors.toList());

        if (categoriasSeleccionadas != null){
            categoriasEditar.forEach(categoria-> categoria.setSeleccionada(categoriasSeleccionadas.stream().anyMatch(seleccionada->seleccionada.getId()==categoria.getCategoria().getId())));
        }

        return categoriasEditar;
    }


    private List<UsuarioEditar> armarListaUsuarioParaEditar(List<Usuario> revisorElegido,Request request){
        List<UsuarioEditar> usuarioEditar = this.buscarUsuarios(request).stream().map(usuario-> new UsuarioEditar(usuario, false)).collect(Collectors.toList());

        if (revisorElegido != null){
            usuarioEditar.forEach(usuario-> usuario.setRevisorElegido(revisorElegido.stream().anyMatch(seleccionada->seleccionada.getId()==usuario.getRevisor().getId())));
        }

        return usuarioEditar;
    }

    private List<Categoria> obtenerCategoriasDelRequest(Request request){
        if(request.queryMap(queryParamCategorias) != null && request.queryMap(queryParamCategorias).values() != null) {
            List<Integer> idCategorias = Arrays.stream(request.queryMap(queryParamCategorias).values()).map(Integer::parseInt).collect(Collectors.toList());
            return this.repoCategorias.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request)).stream().filter(categoria -> idCategorias.stream().anyMatch(idCategoria -> idCategoria == categoria.getId())).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    private List<Usuario> obtenerRevisoresDelRequest(Request request){
        if(request.queryMap(queryParamRevisor) != null && request.queryMap(queryParamRevisor).values() != null) {
            List<Integer> idRevisores = Arrays.stream(request.queryMap(queryParamRevisor).values()).map(Integer::parseInt).collect(Collectors.toList());
            return this.buscarUsuarios(request).stream().filter(usuario -> idRevisores.stream().anyMatch(idRevisor -> idRevisor == usuario.getId())).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    private List<Presupuesto> buscarPresupuestos(int idOperacion) {
        return this.repoPresupuestos.buscarPorOperacion(idOperacion);
    }

    public String elegirPresupuesto(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        OperacionEgreso operacion = idOperacion == 0 ? new OperacionEgreso() : this.repoEgresos.buscar(idOperacion);

        Presupuesto presu = this.repoPresupuestos.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamPresupuestoSeleccionado))));
        operacion.setPresupuestoElegido(presu);

        this.repoEgresos.modificar(operacion);

        return JsonResponse.buildJson(true, ServerRoutes.OPERACIONES_EGRESOS);
    }

    private List<BaseObject> listarCriteriosSeleccion() {
        List<BaseObject> lista = new ArrayList<>();
        BaseObject baseObject = new BaseObject(0, TipoCriterioSeleccion.CRITERIO_MENOR_VALOR.toString().replace("_", " "));
        lista.add(baseObject);
        return lista;
    }
    
}
