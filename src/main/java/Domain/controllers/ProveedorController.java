package Domain.controllers;

import Domain.Entities.Operaciones.Proveedor.Proveedor;
import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Direccion;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.CiudadRepository;
import Domain.Repositories.daos.Entities.PaisRepository;
import Domain.Repositories.daos.Entities.ProveedorRepository;
import Domain.Repositories.daos.Entities.ProvinciaRepository;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProveedorController {
    private final ProveedorRepository repoProveedor;
    private final PaisRepository repoPais;
    private final ProvinciaRepository repoProvincia;
    private final CiudadRepository repoCiudad;

    private static final String queryParamNombre = "nombre";
    private static final String queryParamApellido = "apellido";
    private static final String queryParamRazonSocial = "razonSocial";
    private static final String queryParamCuit = "cuit";
    private static final String queryParamDni = "dni";
    private static final String queryParamCalle = "calle";
    private static final String queryParamAltura = "altura";
    private static final String queryParamPiso = "piso";
    private static final String queryParamDepartamento = "departamento";
    private static final String queryParamCodigoPostal = "codigoPostal";
    private static final String queryParamPais = "pais";
    private static final String queryParamProvincia = "provincia";
    private static final String queryParamCiudad = "ciudad";

    private static final String queryParamPermisoABM = "permisoABM";

    public ProveedorController() {
        this.repoPais = new PaisRepository();
        this.repoProvincia = new ProvinciaRepository();
        this.repoCiudad = new CiudadRepository();
        this.repoProveedor = new ProveedorRepository();
    }

    public ModelAndView mostrarTodosProveedor(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);

        List<Proveedor> proveedores = this.repoProveedor.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request));

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_PROVEEDOR)) {

            if (LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_PROVEEDOR)) {
                parametros.put("permisoABM", queryParamPermisoABM);
            }
        }
        else {
            response.redirect(ServerRoutes.HOME);
        }

        parametros.put("proveedores", proveedores);
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        return new ModelAndView(parametros, "proveedores.hbs");
    }


    public ModelAndView mostrarProveedor(Request request, Response response) throws Exception {

        int idProveedor = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Proveedor proveedor = idProveedor == 0 ? new Proveedor() : this.repoProveedor.buscar(idProveedor);

        if(proveedor != null) {
            return pantallaEditarProveedor(request, response);
        } else {
            throw new Exception("Proveedor no encontrado") ;
        }
    }


    public Response crearYEditarProveedor(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idProveedor = Integer.parseInt(paramToStringInt(request.params(ServerRoutes.PARAM_ID)));

        Proveedor proveedor = idProveedor == 0 ? new Proveedor() : this.repoProveedor.buscar(idProveedor);

        this.actualizarProveedor(proveedor, request);

        if(idProveedor == 0) {
            this.repoProveedor.agregar(proveedor);
        } else {
            this.repoProveedor.modificar(proveedor);
        }

        response.redirect(ServerRoutes.PROVEEDORES);

        return response;
    }


    public ModelAndView pantallaCrearProveedor(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        Proveedor proveedor = new Proveedor();
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("proveedor", proveedor);

        return new ModelAndView(parametros, "proveedor.hbs");
    }


    public ModelAndView pantallaEditarProveedor(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        int idProveedor = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Proveedor proveedor = this.repoProveedor.buscar(idProveedor);

        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));
        parametros.put("proveedor", proveedor);

        return new ModelAndView(parametros, "proveedor.hbs");
    }

    private void actualizarProveedor(Proveedor proveedor, Request request) {
        proveedor.setNombre(request.queryParams(queryParamNombre));
        proveedor.setApellido(request.queryParams(queryParamApellido));
        proveedor.setRazonSocial(request.queryParams(queryParamRazonSocial));
        proveedor.setCuit(request.queryParams(queryParamCuit));
        proveedor.setDni(request.queryParams(queryParamDni));

        Usuario usuarioLogueado = LoginController.obtenerUsuarioLogueado(request);
        proveedor.setEntidad(usuarioLogueado.getEntidad());

        Direccion direccionPostal = new Direccion();
        direccionPostal.setCalle(request.queryParams(queryParamCalle));
        direccionPostal.setAltura(request.queryParams(queryParamAltura));
        direccionPostal.setPiso(request.queryParams(queryParamPiso));
        direccionPostal.setDepartamento(request.queryParams(queryParamDepartamento));
        direccionPostal.setCodigoPostal(request.queryParams(queryParamCodigoPostal));

        Pais pais = this.repoPais.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamPais))));
        direccionPostal.setPais(pais);

        Provincia provincia = this.repoProvincia.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamProvincia))));
        direccionPostal.setProvincia(provincia);

        Ciudad ciudad = this.repoCiudad.buscar(Integer.parseInt(paramToStringInt(request.queryParams(queryParamCiudad))));
        direccionPostal.setCiudad(ciudad);

        proveedor.setDireccionPostal(direccionPostal);

    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("query_param_nombre", queryParamNombre);
        parametros.put("query_param_apellido", queryParamApellido);
        parametros.put("query_param_cuit", queryParamCuit);
        parametros.put("query_param_dni", queryParamDni);
        parametros.put("query_param_razonSocial", queryParamRazonSocial);
        parametros.put("query_param_calle", queryParamCalle);
        parametros.put("query_param_altura", queryParamAltura);
        parametros.put("query_param_piso", queryParamPiso);
        parametros.put("query_param_departamento", queryParamDepartamento);
        parametros.put("query_param_codigo_postal", queryParamCodigoPostal);
        parametros.put("query_param_pais", queryParamPais);
        parametros.put("query_param_provincia", queryParamProvincia);
        parametros.put("query_param_ciudad", queryParamCiudad);
        parametros.put("proveedores_route_crear", ServerRoutes.PROVEEDORES_CREAR);

        parametros.put("proveedores_route", ServerRoutes.PROVEEDORES);

        return parametros;
    }

    private String paramToStringInt(String parametro) {
        return parametro != null && !parametro.isEmpty() ? parametro : "0";
    }
}
