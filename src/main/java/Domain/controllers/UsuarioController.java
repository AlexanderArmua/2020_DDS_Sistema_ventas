package Domain.controllers;

import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Usuarios.PermisoUsuario;
import Domain.Entities.Usuarios.Rol;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.EntidadRepository;
import Domain.Repositories.daos.Entities.UsuarioRepository;
import Security.PasswordValidator.PasswordInvalidaException;
import server.ServerRoutes;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.utils.JsonResponse;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


public class UsuarioController {
    private final UsuarioRepository repoUsuario;
    private final EntidadRepository entidadRepositorio;

    // Los guardamos, porque no se puede updatear en tiempo real sin tocar código entonces evitamos una llamada a la BD
    private final List<Rol> todosLosRoles;

    private static final String queryParamUsuario = "usuario";
    private static final String queryParamContrasenna = "contrasenna";
    private static final String queryParamContrasenna2 = "contrasenna2";
    private static final String queryParamRoles = "roles";
    private static final String queryParamEntidades = "entidades";

    private static final String queryParamPermisoABM = "permisoABM";

    public UsuarioController() {
        this.repoUsuario = new UsuarioRepository();

        this.entidadRepositorio = new EntidadRepository();

        Repositorio<Rol> rolRepositorio = new Repositorio<>(new DAOHibernate<>(Rol.class));
        this.todosLosRoles = rolRepositorio.buscarTodos();
    }

    public ModelAndView mostrarTodos(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        List<Usuario> usuarios = this.repoUsuario.buscarUsuariosDeEntidad(LoginController.obtenerUsuarioLogueado(request).getEntidad().getId());

        Map<String, Object> parametros = this.obtenerParametrosDefault();
        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.VISUALIZAR_USUARIOS)) {
            parametros.put("usuarios", usuarios);
            parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        if(LoginController.obtenerUsuarioLogueado(request).tienePermiso(PermisoUsuario.ABM_USUARIOS)) {
            parametros.put("permisoABM", queryParamPermisoABM);
        }

        }
        else{
            response.redirect(ServerRoutes.HOME);
        }
        return new ModelAndView(parametros, "usuarios.hbs");
    }

    public ModelAndView crear(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        // TODO: COntrolar que no cambie el nombre a otro ya existente

        Map<String, Object> parametros = this.getParamsParaCrear(null,LoginController.obtenerUsuarioLogueado(request));
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        return new ModelAndView(parametros, "usuario.hbs");
    }

    public ModelAndView editar(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        int idUsuario = Integer.parseInt(request.params(ServerRoutes.PARAM_ID));

        Usuario usuarioEditar = this.repoUsuario.buscar(idUsuario);

        // TODO: Controlar que exista
        Map<String, Object> parametros = this.getParamsEditar(usuarioEditar);
        parametros.put("usuario", LoginController.obtenerUsuarioLogueado(request));

        return new ModelAndView(parametros, "usuario.hbs");
    }

    public String actualizar(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        Usuario usuarioEditar = this.repoUsuario.buscar(Integer.parseInt(request.params(ServerRoutes.PARAM_ID)));

        actualizarUsuario(usuarioEditar, request);

        // TODO: Controlar que no cambie el nombre a otro ya existente

        this.repoUsuario.modificar(usuarioEditar);

        return JsonResponse.buildJson(true, ServerRoutes.USUARIOS, false, null);
    }

    public String crearUsuario(Request request, Response response) throws Exception {
        LoginController.estaLogueado(request, response);
        Usuario usuarioCrear = new Usuario();

        try {
            this.validarContrasennas(request);

            actualizarUsuario(usuarioCrear, request);

            this.repoUsuario.crearUsuario(usuarioCrear, usuarioCrear.getContrasenia());
        } catch (PasswordInvalidaException e) {
            JsonResponse jsonResponse = new JsonResponse(false, "", true, null);
            jsonResponse.setMensajeError(e.getMensajesError());

            return jsonResponse.buildJson();
        } catch (Exception e) {
            return JsonResponse.buildJson(false, "", true, e.getMessage());
        }

        Usuario usuarioObtenido = repoUsuario.obtenerUsuario(usuarioCrear.getUsuario(),usuarioCrear.getContrasenia());

        usuarioObtenido.setRoles(usuarioCrear.getRoles());
        usuarioObtenido.setEntidad(usuarioCrear.getEntidad());

        repoUsuario.modificar(usuarioObtenido);

        return JsonResponse.buildJson(true, ServerRoutes.USUARIOS);
    }

    // todo: API
    public Response eliminar(Request request, Response response) {
        LoginController.estaLogueado(request, response);
        Usuario usuarioEliminar = this.repoUsuario.buscar(Integer.parseInt(request.params("id")));
        // TODO: Validar que el usuario exista
        this.repoUsuario.eliminar(usuarioEliminar);

        response.redirect(ServerRoutes.USUARIOS);

        return response;
    }

    private void actualizarUsuario(Usuario usuario, Request request) throws Exception {
        usuario.setUsuario(request.queryParams(queryParamUsuario));
        if (usuario.getContrasenia() == null) {
            usuario.setContrasenia(request.queryParams(queryParamContrasenna));
        }
        usuario.setRoles(this.obtenerRolesDelRequest(request));

        Entidad entidad = this.obtenerEntidad(request);
        if (entidad == null) {
            throw new Exception("Entidad incorrecta");
        } else {
            usuario.setEntidad(entidad);
        }
    }

    private Map<String, Object> obtenerParametrosDefault() {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("home_route", ServerRoutes.HOME);
        parametros.put("usuarios_route", ServerRoutes.USUARIOS);
        parametros.put("usuarios_crear", ServerRoutes.USUARIOS_CREAR);
        parametros.put("query_param_usuario", queryParamUsuario);
        parametros.put("query_param_contrasenna", queryParamContrasenna);
        parametros.put("query_param_contrasenna2", queryParamContrasenna2);
        parametros.put("query_param_roles", queryParamRoles);
        parametros.put("query_param_entidades", queryParamEntidades);

        return parametros;
    }

    private List<Rol> obtenerRolesDelRequest(Request request) {
        List<Integer> idRoles = Arrays.stream(request.queryMap(queryParamRoles).values()).map(Integer::parseInt).collect(Collectors.toList());

        return this.todosLosRoles.stream().filter(rol -> idRoles.stream().anyMatch(idRol -> idRol == rol.getId())).collect(Collectors.toList());
    }

    private Entidad obtenerEntidad(Request request) throws Exception {
        String idEntidad = request.queryParams(queryParamEntidades);
        if (idEntidad.length() == 0) {
            throw new Exception("No hay entidad seleccionada");
        }

        return this.entidadRepositorio.buscarTodosParaElUsuario(LoginController.obtenerUsuarioLogueado(request)).stream().filter(e -> e.getId() == Integer.parseInt(idEntidad)).findAny().orElse(null);
    }

    private List<RolEditarUsuario> armarListaRolesParaEditar(List<Rol> rolesSeleccionados) {
        List<RolEditarUsuario> rolesEditarUsuario = this.todosLosRoles.stream().filter(rol -> rol.getId() != 2 ).map(rol -> new RolEditarUsuario(rol, false)).collect(Collectors.toList());

        if (rolesSeleccionados != null) {
            rolesEditarUsuario.stream().filter(rol -> rol.getRol().getId() != 2 ).forEach(rol -> rol.setSeleccionado(rolesSeleccionados.stream().anyMatch(seleccionado -> seleccionado.getId() == rol.getRol().getId())));
        }

        return rolesEditarUsuario;
    }

    private void validarContrasennas(Request request) throws Exception {
        if (!request.queryParams(queryParamContrasenna).equals(request.queryParams(queryParamContrasenna2))) {
            throw new Exception("Las contraseñas no coinciden");
        }
    }

    // Creación de parametros
    private Map<String, Object> getParamsParaCrear(List<Rol> roles,Usuario usuario) {
        Map<String, Object> parametros = this.obtenerParametrosDefault();

        parametros.put("roles", this.armarListaRolesParaEditar(roles));
        parametros.put("entidades", this.entidadRepositorio.buscarTodosParaElUsuario(usuario));

        return parametros;
    }

    private Map<String, Object> getParamsEditar(Usuario usuarioEditar) {
        Map<String, Object> parametros = this.getParamsParaCrear(usuarioEditar.getRoles(),usuarioEditar);

        parametros.put("usuario", usuarioEditar);
        parametros.put("entidadSeleccionada", usuarioEditar.getEntidad());

        return parametros;
    }

}
