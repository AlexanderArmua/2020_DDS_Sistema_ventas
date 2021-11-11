package Domain.controllers;

import Domain.Entities.BaseObject;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Repositories.daos.Entities.OperacionEgresoRepository;
import Domain.Repositories.daos.Entities.OperacionIngresoRepository;
import Util.Tasks.Vinculador.Vinculador;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VinculadorController {

    private final Vinculador vinculador;
    private final OperacionIngresoRepository operacionIngresoRepository;
    private final OperacionEgresoRepository operacionEgresoRepository;

    private static final String queryParamCriterio = "criterioSeleccionado";
    private static final String queryParamEgresosSeleccionados = "egresosSeleccionados";
    private static final String queryParamIngresosSeleccionados = "ingresosSeleccionados";

    public VinculadorController(){
        this.vinculador = new Vinculador();
        this.operacionEgresoRepository = new OperacionEgresoRepository();
        this.operacionIngresoRepository = new OperacionIngresoRepository();
    }

    public ModelAndView pantallaVinculador(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.EJECUTAR_VINCULADOR)) {
            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

            int idEntidadUsuario = LoginController.obtenerUsuarioLogueado(request).getEntidad().getId();
            List<OperacionEgreso> egresos = this.operacionEgresoRepository.buscarTodosSinVincular(idEntidadUsuario);
            List<OperacionIngreso> ingresos = this.operacionIngresoRepository.buscarTodosSinVincular(idEntidadUsuario);

            BaseObject criterioPrimeroEgresos = new BaseObject(1, "Orden Valor - Primero Egresos");
            BaseObject criterioPrimeroIngresos = new BaseObject(2, "Orden Valor - Primero Ingresos");
            BaseObject criterioFechaPrimeroEgreso = new BaseObject(3, "Orden Fecha - Primero Egresos");
            List<BaseObject> criterios = new ArrayList<>();
            criterios.add(criterioFechaPrimeroEgreso);
            criterios.add(criterioPrimeroEgresos);
            criterios.add(criterioPrimeroIngresos);

            parametros.put("egresos", egresos);
            parametros.put("ingresos", ingresos);
            parametros.put("criterios", criterios);
        }
        else {
            response.redirect(ServerRoutes.HOME);
        }
        return new ModelAndView(parametros, "vinculador.hbs");
    }

    public Response vincularOperaciones(Request request, Response response) throws IOException {
        LoginController.estaLogueado(request, response);

        String egresos = request.queryParams(queryParamEgresosSeleccionados);
        String ingresos = request.queryParams(queryParamIngresosSeleccionados);
        String criterio = request.queryParams(queryParamCriterio);

        String[] egresosIds = egresos.split(",");
        String[] ingresosIds = ingresos.split(",");

        List<OperacionEgreso> operacionesEgreso = new ArrayList<>();
        List<OperacionIngreso> operacionesIngreso = new ArrayList<>();
        List<String> criterios = new ArrayList<>();

        for(int i = 0; i < egresosIds.length; i++) {
            OperacionEgreso op = this.operacionEgresoRepository.buscar(Integer.valueOf(egresosIds[i]));
            if(op != null) {
                operacionesEgreso.add(op);
            }
        }

        for(int j = 0; j < ingresosIds.length; j++) {
            OperacionIngreso op = this.operacionIngresoRepository.buscar(Integer.valueOf(ingresosIds[j]));
            if(op != null) {
                operacionesIngreso.add(op);
            }
        }

        criterios.add(criterio);

        this.vinculador.vincular(operacionesEgreso, operacionesIngreso, criterios);

        response.redirect(ServerRoutes.VINCULAR);

        return response;
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_criterio", queryParamCriterio);
        parametros.put("query_param_ingresos_seleccionados", queryParamIngresosSeleccionados);
        parametros.put("query_param_egresos_seleccionados", queryParamEgresosSeleccionados);

        parametros.put("vinculador", ServerRoutes.VINCULAR);
        parametros.put("vincular", ServerRoutes.VINCULAR_OPERACIONES);

        return parametros;
    }

}
