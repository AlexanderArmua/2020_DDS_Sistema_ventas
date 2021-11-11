package Domain.controllers;

import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Entities.Presupuestos.Moneda;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Repositories.daos.Entities.*;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.JsonResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OperacionIngresoController {
    private final OperacionIngresoRepository repoIngresos;
    private final MonedaRepository repoMoneda;


    private static final String queryParamFecha = "fecha";
    private static final String queryParamValorTotal = "valorTotal";
    private static final String queryParamDescripcion = "descripcion";
    private static final String queryParamFechaHasta = "fechaHasta";
    private static final String queryParamMoneda = "moneda";

    private static final String queryParamPermisoABM = "permisoABM";

    public OperacionIngresoController() {

        this.repoIngresos = new OperacionIngresoRepository();
        this.repoMoneda = new MonedaRepository();
    }

    public ModelAndView mostrarTodosIngresos(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        List<OperacionIngreso> ingresos = this.repoIngresos.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        ingresos.sort((o1, o2) -> {
            if (o1.getFecha() == null || o2.getFecha() == null)
                return 0;
            return o1.getFecha().compareTo(o2.getFecha());
        });

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_OPERACION_INGRESO)) {

            if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_OPERACION_INGRESO)){
            parametros.put("permisoABM", queryParamPermisoABM);
        }}
        else {
            response.redirect(ServerRoutes.HOME);
        }
        parametros.put("ingresos", ingresos);
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        return new ModelAndView(parametros, "operacionesIngreso.hbs");
    }


    public ModelAndView mostrarOperacionIngreso(Request request, Response response) throws Exception{

        int idOperacionIngreso = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        OperacionIngreso operacion = idOperacionIngreso == 0 ? new OperacionIngreso() : this.repoIngresos.buscar(idOperacionIngreso);

        if(operacion != null) {
            return pantallaEditarIngreso(request, response);
        } else {
            throw new Exception("Operacion no encontrada") ;
        }
    }


    public String crearYEditarOperacionIngreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idOperacion = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        OperacionIngreso operacion = idOperacion == 0 ? new OperacionIngreso() : this.repoIngresos.buscar(idOperacion);

        String str = request.queryParams(queryParamFecha);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(str, formatter);
        } catch(Exception e) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            dateTime = LocalDateTime.parse(str, formatter);
        }

        String fechaHastaString = request.queryParams(queryParamFechaHasta);
        DateTimeFormatter formatterHasta = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fechaHasta = LocalDate.parse(fechaHastaString, formatterHasta);

        if(ChronoLocalDate.from(dateTime).isAfter(fechaHasta)){
            return JsonResponse.buildJson(false, "", true, "La Fecha de Creación debe ser menor a la Fecha Hasta");
        }

        operacion.setFecha(dateTime);

        operacion.setFechaHasta(fechaHasta);
        operacion.setValorTotal(Float.parseFloat(paramToStringInt(request.queryParams(queryParamValorTotal))));

        Moneda moneda = this.repoMoneda.buscar(1);
        operacion.setMoneda(moneda);

        operacion.setDescripcion(request.queryParams(queryParamDescripcion));

        operacion.setEntidad(LoginController.obtenerUsuarioLogueado(request).getEntidad());

        if(idOperacion == 0) {
            this.repoIngresos.agregar(operacion);
        } else {
            this.repoIngresos.modificar(operacion);
        }

        return JsonResponse.buildJson(true, ServerRoutes.OPERACIONES_INGRESOS);
    }

    public ModelAndView pantallaCrearIngreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        OperacionIngreso operacion = new OperacionIngreso();
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        parametros.put("ingreso", operacion);
        parametros.put("monedaSeleccionado", new Moneda());
        parametros.put("moneda", buscarMonedas());

        return new ModelAndView(parametros, "operacionIngreso.hbs");
    }

    public ModelAndView pantallaEditarIngreso(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        int idOperacion = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        OperacionIngreso operacion = this.repoIngresos.buscar(idOperacion);

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        parametros.put("monedaSeleccionado", operacion.getMoneda() != null ? operacion.getMoneda() : new Moneda());

        parametros.put("ingreso", operacion);
        parametros.put("moneda", buscarMonedas());

        return new ModelAndView(parametros, "operacionIngreso.hbs");
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_valor", queryParamValorTotal);
        parametros.put("query_param_fecha", queryParamFecha);
        parametros.put("query_param_descripcion", queryParamDescripcion);
        parametros.put("query_param_fecha_hasta", queryParamFechaHasta);
        parametros.put("query_param_moneda", queryParamMoneda);

        parametros.put("operacion_ingreso_crear", ServerRoutes.OPERACION_INGRESO_CREAR);
        parametros.put("operacion_ingreso_editar", ServerRoutes.OPERACION_INGRESO_EDITAR);

        parametros.put("ingreso", ServerRoutes.OPERACION_INGRESOS);

        parametros.put("operacion_view_ingreso", ServerRoutes.OPERACIONES_INGRESOS);

        return parametros;
    }


    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }

    private List<Moneda> buscarMonedas(){
        return this.repoMoneda.buscarTodos();
    }
}
