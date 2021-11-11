package Domain.controllers;

import Domain.Entities.Entidades.Criterio.Categoria;
import Domain.Entities.Entidades.Criterio.Criterio;
import Domain.Entities.Operaciones.MediosDePago.MedioDePago;
import Domain.Entities.Operaciones.MediosDePago.TipoMedioPago;
import Domain.Entities.Operaciones.Operacion.Operacion;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Repositories.daos.Entities.*;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class OperacionController {
    private final OperacionRepository repoOperaciones;
    private final OperacionEgresoRepository repoEgresos;
    private final OperacionIngresoRepository repoIngresos;

    private static final String queryParamFecha = "fecha";
    private static final String queryParamValorTotal = "valorTotal";
    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamFechaHasta = "fechaHasta";
    private static final String queryParamNombreResponsable = "responsable";
    private static final String queryParamNumeroComprobante = "numeroComprobante";
    private static final String queryParamCodigoSeguridad = "codigoSeguridad";
    private static final String queryParamFechaVencimiento = "fechaVencimiento";
    private static final String valorEfectivo = "efectivo";
    private static final String valorTarjetaDebito = "tarjetaDebito";
    private static final String valorTarjetaCredito = "tarjetaCredito";
    private static final String valorCheque = "cheque";

    public OperacionController() {
        this.repoOperaciones = new OperacionRepository();
        this.repoIngresos = new OperacionIngresoRepository();
        this.repoEgresos = new OperacionEgresoRepository();
    }


    /*
    public ModelAndView mostrarTodos(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        List<OperacionEgreso> egresos = this.repoEgresos.buscarTodos();
        egresos.forEach(e -> e.setValorTotal(e.getValorTotal() * -1));

        List<OperacionIngreso> ingresos = this.repoIngresos.buscarTodos();

        List<Operacion> operaciones = new ArrayList<>(ingresos);
        operaciones.addAll(egresos);

        operaciones.sort((o1, o2) -> {
            if (o1.getFecha() == null || o2.getFecha() == null)
                return 0;
            return o1.getFecha().compareTo(o2.getFecha());
        });

        Map<String, Object> parametros = this.obtenerParametrosDefault();

        parametros.put("operaciones", operaciones);

        return new ModelAndView(parametros, "operaciones.hbs");
    }

    public ModelAndView mostrarOperacion(Request request, Response response) throws Exception{
        int idOperacion = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        OperacionEgreso egreso =  this.repoEgresos.buscar(idOperacion);
        OperacionIngreso ingreso = this.repoIngresos.buscar(idOperacion);

        if(egreso != null){
            return pantallaEditarEgreso(request, response);
        } else if(ingreso != null) {
            return pantallaEditarIngreso(request, response);
        } else {
            throw new Exception("Operacion no encontrada") ;
        }
    }

    public Response crearYEditarOperacionEgreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        OperacionEgreso operacion = idOperacion == 0 ? new OperacionEgreso() : (OperacionEgreso) this.repoOperaciones.buscar(idOperacion);

        String str = request.queryParams(queryParamFecha);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        operacion.setFecha(dateTime);
        operacion.setValorTotal(Float.parseFloat(paramToStringInt(request.queryParams(queryParamValorTotal))));

        int cantidadPresupuestos = Integer.parseInt(paramToStringInt(request.queryParams(queryParamCantidadPresupuestos)));
        if(cantidadPresupuestos > 0) {
            operacion.setCantidadPresupuestoRequeridos(cantidadPresupuestos);
            operacion.setNecesitaPresupuesto(true);
        } else {
            operacion.setNecesitaPresupuesto(false);
        }

        Proveedor proveedor= this.repoProveedores.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamProveedor))));
        operacion.setProveedor(proveedor);

        saveMedioPago(request, operacion);

        Categoria categoria = this.repoCategorias.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamCategorias))));
        operacion.setCategorias(new ArrayList<>());
        operacion.addCategoroa(categoria);

        if(idOperacion == 0) {
            this.repoOperaciones.agregar(operacion);
        } else {
            this.repoOperaciones.modificar(operacion);
        }

        response.redirect(ServerRoutes.OPERACIONES);

        return response;
    }

    public ModelAndView pantallaCrearEgreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        OperacionEgreso egreso = new OperacionEgreso();
        parametros.put("operacion", egreso);
        parametros.put("medioPagoSeleccionado", "null");
        parametros.put("proveedorSeleccionado", new Proveedor());
        parametros.put("categoriaSeleccionada", new Categoria());

        parametros.put("proveedores", buscarProveedores());
        parametros.put("mediosPago", buscarMediosDePago());
        parametros.put("categorias", buscarCategorias());

        return new ModelAndView(parametros, "operacionEgreso.hbs");
    }

    public ModelAndView pantallaEditarEgreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idOperacion = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        OperacionEgreso operacion = this.repoEgresos.buscar(idOperacion);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        parametros.put("operacion", operacion);
        String medioPagoSeleccionado = getMedioPagoSeleccionado(operacion.getMedioDePago());
        parametros.put("medioPagoSeleccionado", medioPagoSeleccionado);
        parametros.put("proveedorSeleccionado", operacion.getProveedor() != null ? operacion.getProveedor() : new Proveedor());
        parametros.put("categoriaSeleccionada", operacion.getCategorias().size() > 0 ? operacion.getCategorias().get(0) : new Categoria());

        parametros.put("proveedores", buscarProveedores());
        parametros.put("mediosPago", buscarMediosDePago());
        parametros.put("categorias", buscarCategorias());

        return new ModelAndView(parametros, "operacionEgreso.hbs");
    }

    public Response crearYEditarOperacionIngreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        OperacionIngreso operacion = idOperacion == 0 ? new OperacionIngreso() : (OperacionIngreso) this.repoOperaciones.buscar(idOperacion);

        String str = request.queryParams(queryParamFecha);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        String fechaHastaString = request.queryParams(queryParamFechaHasta);
        DateTimeFormatter formatterHasta = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDate fechaHasta = LocalDate.parse(fechaHastaString, formatterHasta);

        operacion.setFecha(dateTime);

        operacion.setFechaHasta(fechaHasta);
        operacion.setValorTotal(Float.parseFloat(paramToStringInt(request.queryParams(queryParamValorTotal))));

        operacion.setDescripcion(request.queryParams(queryParamDescripcion));

        if(idOperacion == 0) {
            this.repoOperaciones.agregar(operacion);
        } else {
            this.repoOperaciones.modificar(operacion);
        }

        response.redirect(ServerRoutes.OPERACIONES);

        return response;
    }

    public ModelAndView pantallaCrearIngreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        OperacionIngreso ingreso = new OperacionIngreso();
        parametros.put("operacion", ingreso);

        return new ModelAndView(parametros, "operacionIngreso.hbs");
    }

    public ModelAndView pantallaEditarIngreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        int idOperacion = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Operacion operacion = this.repoOperaciones.buscar(idOperacion);

        parametros.put("operacion", operacion);

        return new ModelAndView(parametros, "operacionIngreso.hbs");
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_valor", queryParamValorTotal);
        parametros.put("query_param_fecha", queryParamFecha);
        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_fecha_hasta", queryParamFechaHasta);
        parametros.put("query_param_nombre_responsable", queryParamNombreResponsable);
        parametros.put("query_param_numero_comprobante", queryParamNumeroComprobante);
        parametros.put("query_param_codigo_seguridad", queryParamCodigoSeguridad);
        parametros.put("query_param_fecha_vencimiento", queryParamFechaVencimiento);
        parametros.put("valorCheque", valorCheque);
        parametros.put("valorTarjetaCredito", valorTarjetaCredito);
        parametros.put("valorTarjetaDebito", valorTarjetaDebito);
        parametros.put("valorEfectivo", valorEfectivo);

        parametros.put("operacion_view", ServerRoutes.OPERACIONES);

        return parametros;
    }*/

}
