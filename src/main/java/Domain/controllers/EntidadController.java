package Domain.controllers;

import Domain.Entities.Entidades.Actividad;
import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Entidades.EntidadBase;
import Domain.Entities.Entidades.EntidadJuridica;
import Domain.Entities.Entidades.TipoEntidad.CategorizadorEstructuraEmpresa;
import Domain.Entities.Entidades.TipoEntidad.Empresa;
import Domain.Entities.Entidades.TipoEntidad.OSC;
import Domain.Entities.Entidades.TipoEntidad.TipoEntidadJuridica;
import Domain.Entities.Ubicacion.Direccion;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.DireccionRepository;
import Domain.Repositories.daos.Entities.EntidadRepository;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;

public class EntidadController {
    private final EntidadRepository entidadRepository;
    private final DireccionRepository direccionRepository;
    private final Repositorio<Actividad> actividadRepositorio;

    private final static String adminArchivo = "administracion/entidades.hbs";
    private final static String entidadArchivo = "administracion/entidad.hbs";

    // Base
    private static final String queryParamNombreEntidad = "nombreEntidad";
    private static final String queryParamDescripcionEntidad = "descripcionEntidad";
    private static final String queryParamEntidadesJuridicas = "entidadesJuridicas";

    // Juridica
    private static final String queryParamCodigoIGJ = "codigoIgj";
    private static final String queryParamCuit = "cuit";
    private static final String queryParamRazonSocial = "razonSocial";
    private static final String queryParamDireccionPostal = "direccionPostal";
    private static final String queryParamTipoEntidadJuridica = "tipoEntidadJuridica";
    private static final String queryParamCantidadPersonal = "cantidadPersonal";
    private static final String queryParamPromedioVentas = "promedioVentas";
    private static final String queryParamSector = "sector";
    private static final String queryParamActividad = "actividad";

    private static final String queryParamInputCalle = "calle";
    private static final String queryParamInputAltura = "altura";
    private static final String queryParamInputPiso = "piso";
    private static final String queryParamInputDepartamento = "departamento";
    private static final String queryParamInputCodigoPostal = "codigoPostal";
    private static final String queryParamSelectPais = "pais";
    private static final String queryParamSelectProvincia = "provincia";
    private static final String queryParamSelectCiudad = "ciudad";

    // Edición
    private static final String queryParamModoModificacion = "modoModificacion";

    //Permisos
    private static final String queryParamPermisoABM = "permisoABM";
    private static final String queryParamPermisoABMBases = "permisoABMBases";
    private static final String queryParamPermisoABMJuridicas = "permisoABMJuridicas";

    public EntidadController() {
        this.entidadRepository = new EntidadRepository();
        this.direccionRepository = new DireccionRepository();
        this.actividadRepositorio = new Repositorio<>(new DAOHibernate<>(Actividad.class));
    }

    public ModelAndView mostrar(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_JURIDICA)||LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_BASE)) {

            //parametros.put("entidades", this.entidadRepository.obtenerTodasLasEntidadesBases());
            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));


            //si tiene los permisos para visualizar ambos tipos de entidades, en el parametro le paso todas y retorno
            if (puedeVisualizarTodasLasEntidades(request)) {
                parametros.put("entidades", this.entidadRepository.buscarTodos());
                //Asumimos que si puede visualizar todas las entidades, no puede modificar nada
            } else {
                if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_BASE)) {
                    parametros.put("entidades", this.entidadRepository.obtenerTodasLasEntidadesBases());

                    if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_ENTIDAD_BASE)) {
                        //parametros.put("puedeModificarBase", queryParamPermisoABMBases);
                        parametros.put("permisoABM", queryParamPermisoABM);
                    }
                }

                if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_JURIDICA)) {
                    parametros.put("entidades", this.entidadRepository.obtenerTodasLasEntidadesJuridicas());

                    if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA)) {
                        //parametros.put("puedeModificarJuridica", queryParamPermisoABMJuridicas);
                        parametros.put("permisoABM", queryParamPermisoABM);
                    }
                }
            }
        }
        else
        {
            response.redirect(ServerRoutes.HOME);
        }

        return new ModelAndView(parametros, adminArchivo);
    }

    public ModelAndView crear(Request request, Response response) {
        LoginController.estaLogueado(request, response);

        Map<String, Object> parametros = this.obtenerParametrosEditar(null);
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_ENTIDAD_BASE)) {
            parametros.put("esBase", queryParamPermisoABMBases);
            parametros.put("query_param_entidad_juridica", obtenerEntidadUsuario(request));}

        if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA)) {
            parametros.put("esJuridica", queryParamPermisoABMJuridicas);}


        return new ModelAndView(parametros, entidadArchivo);
    }

    public ModelAndView editar(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idUsuario = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Entidad entidadEditar = this.entidadRepository.buscar(idUsuario);

        Map<String, Object> parametros = this.obtenerParametrosEditar(entidadEditar);

            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_ENTIDAD_BASE)) {
                parametros.put("esBase", queryParamPermisoABMBases);
            }
            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_ENTIDAD_JURIDICA)) {
                parametros.put("esJuridica", queryParamPermisoABMJuridicas);
            }

        return new ModelAndView(parametros, entidadArchivo);
    }

    public Response crearEntidad(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Entidad entidadCrear;

        if (esCreacionEntidadBase(request)) {
            entidadCrear = new EntidadBase();
            actualizarValores((EntidadBase) entidadCrear, request);
        } else {
            entidadCrear = new EntidadJuridica();
            actualizarValores((EntidadJuridica) entidadCrear, request);
        }

        this.entidadRepository.agregar(entidadCrear);

        response.redirect(ServerRoutes.ENTIDADES);

        return response;
    }

    public Response actualizarEntidad(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Entidad entidadActualizar = this.entidadRepository.buscar(Integer.parseInt(request.params(ServerRoutes.PARAM_ID)));

        if (entidadActualizar instanceof EntidadBase) {
            actualizarValores((EntidadBase) entidadActualizar, request);
        } else {
            actualizarValores((EntidadJuridica) entidadActualizar, request);
        }

        this.entidadRepository.modificar(entidadActualizar);

        response.redirect(ServerRoutes.ENTIDADES);

        return response;
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("entidades_route", ServerRoutes.ENTIDADES);
        parametros.put("entidades_crear", ServerRoutes.ENTIDADES_CREAR);

        // Base
        parametros.put("query_param_nombre_entidad", queryParamNombreEntidad);
        parametros.put("query_param_descripcion_entidad", queryParamDescripcionEntidad);
        parametros.put("query_param_entidad_juridica", queryParamEntidadesJuridicas);

        // Juridica
        parametros.put("query_param_codigo_igj", queryParamCodigoIGJ);
        parametros.put("query_param_cuit", queryParamCuit);
        parametros.put("query_param_razon_social", queryParamRazonSocial);
        parametros.put("query_param_direccion_postal", queryParamDireccionPostal);

        parametros.put("query_param_tipo_entidad_juridica", queryParamTipoEntidadJuridica);
        parametros.put("query_param_cantidad_personal", queryParamCantidadPersonal);
        parametros.put("query_param_promedio_ventas", queryParamPromedioVentas);
        parametros.put("query_param_select_sector", queryParamSector);
        parametros.put("query_param_select_actividad", queryParamActividad);

        parametros.put("query_param_input_altura", queryParamInputAltura);
        parametros.put("query_param_input_calle", queryParamInputCalle);
        parametros.put("query_param_input_codigo_postal", queryParamInputCodigoPostal);
        parametros.put("query_param_input_departamento", queryParamInputDepartamento);
        parametros.put("query_param_input_piso", queryParamInputPiso);
        parametros.put("query_param_select_pais", queryParamSelectPais);
        parametros.put("query_param_select_provincia", queryParamSelectProvincia);
        parametros.put("query_param_select_ciudad", queryParamSelectCiudad);

        // Edicion
        parametros.put("query_param_modo_modificacion", queryParamModoModificacion);

        return parametros;
    }

    private Map<String, Object> obtenerParametrosEditar(Entidad entidad) {
        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if (entidad != null) {
            parametros.put("entidad", entidad);
        }

        final Boolean esEntidadJuridica = entidad instanceof EntidadJuridica;
        parametros.put("es_entidad_juridica", esEntidadJuridica);

        if (!esEntidadJuridica) {
            parametros.put("entidades_juridicas", this.entidadRepository.obtenerTodasLasEntidadesJuridicas());
        }

        parametros.put("es_creacion", entidad == null);

        return parametros;
    }

    private void actualizarValores(EntidadBase entidad, Request request) {
        entidad.setNombre(request.queryParams(queryParamNombreEntidad));
        entidad.setDescripcion(request.queryParams(queryParamDescripcionEntidad));
        entidad.setEntidadJuridica(this.obtenerEntidadJuridica(request));
    }

    private void actualizarValores(EntidadJuridica entidad, Request request) {
        entidad.setNombre(request.queryParams(queryParamNombreEntidad));
        entidad.setCodigoIGJ(request.queryParams(queryParamCodigoIGJ));
        entidad.setCuit(request.queryParams(queryParamCuit));
        entidad.setRazonSocial(request.queryParams(queryParamRazonSocial));

        if (entidad.getDireccionPostal() == null) {
            entidad.setDireccionPostal(new Direccion());
        }

        entidad.getDireccionPostal().setCalle(request.queryParams(queryParamInputCalle));
        entidad.getDireccionPostal().setPiso(request.queryParams(queryParamInputPiso));
        entidad.getDireccionPostal().setAltura(request.queryParams(queryParamInputAltura));
        entidad.getDireccionPostal().setCodigoPostal(request.queryParams(queryParamInputCodigoPostal));
        entidad.getDireccionPostal().setDepartamento(request.queryParams(queryParamInputDepartamento));

        entidad.getDireccionPostal().setPais(this.direccionRepository.buscarPais(Integer.parseInt(request.queryParams(queryParamSelectPais))));
        entidad.getDireccionPostal().setProvincia(this.direccionRepository.buscarProvincia(Integer.parseInt(request.queryParams(queryParamSelectProvincia))));
        entidad.getDireccionPostal().setCiudad(this.direccionRepository.buscarCiudad(Integer.parseInt(request.queryParams(queryParamSelectCiudad))));

        // Si es empresa, asignamos valores y recategorizamos
        String tipoEntidad = request.queryParams(queryParamTipoEntidadJuridica);
        if (tipoEntidad.equals("empresa")) {
            if (entidad.getTipoEntidadJuridica() == null) {
                entidad.setTipoEntidadJuridica(new Empresa());
            }

            ((Empresa) entidad.getTipoEntidadJuridica()).setActividad(this.actividadRepositorio.buscar(Integer.parseInt(request.queryParams(queryParamActividad))));
            ((Empresa) entidad.getTipoEntidadJuridica()).setCantidadPersonal(Integer.parseInt(request.queryParams(queryParamCantidadPersonal)));
            ((Empresa) entidad.getTipoEntidadJuridica()).setPromedioAnualVentas(Long.parseLong(request.queryParams(queryParamPromedioVentas)));

            CategorizadorEstructuraEmpresa categorizadorEstructuraEmpresa = CategorizadorEstructuraEmpresa.getCategorizador();
            ((Empresa) entidad.getTipoEntidadJuridica()).setEstructuraJuridica(categorizadorEstructuraEmpresa.calcularEstructura((Empresa) entidad.getTipoEntidadJuridica()));
        } else {
            if (entidad.getTipoEntidadJuridica() == null) {
                entidad.setTipoEntidadJuridica(new OSC());
            }
        }
    }

    private EntidadJuridica obtenerEntidadJuridica(Request request) {
        Integer idEntidad = obtenerEntidadUsuario(request).getId();
        if (idEntidad == null) {
            return null;
        }

        return this.entidadRepository.buscarEntidadJuridica(idEntidad);
    }

    private Entidad obtenerEntidadUsuario(Request request){
        return LoginController.obtenerUsuarioLogueado(request).getEntidad();
    }

    private Boolean esCreacionEntidadBase(Request request) {
        return request.queryParams(queryParamModoModificacion).equals("base");
    }

    private Boolean puedeVisualizarTodasLasEntidades(Request request) {
        return LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_BASE)&&LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_ENTIDAD_JURIDICA);
    }
}