package server;

import Domain.controllers.*;
import Domain.controllers.HomeController;
import Domain.controllers.LoginController;
import Domain.controllers.PresupuestoController;
import Domain.controllers.UsuarioController;
import Domain.controllers.UsuarioRestController;
import Domain.controllers.ItemController;
import db.EntityManagerHelper;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.SisHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {
    private static HandlebarsTemplateEngine engine;

    private static void initEngine() {
        Router.engine = HandlebarsTemplateEngineBuilder
                .create()
                .withDefaultHelpers()
                .withHelper("navbar", SisHelper.navbar)
                .withHelper("head", SisHelper.head)
                .withHelper("footer", SisHelper.footer)
                .withHelper("generalLibs", SisHelper.generalLibs)
                .withHelper("selectorSimpleHelper", SisHelper.selectorSimpleHelper)
                .withHelper("selectorMultipleHelper", SisHelper.selectorMultipleHelper)
                .withHelper("compararIgual", SisHelper.compararIgual)
                .withHelper("controlDireccionHelper", SisHelper.controlDireccion)
                .build();

    }

    public static void init() {
        Router.initEngine();
        Spark.staticFileLocation("/public");
        Router.configure();
    }

    private static void configure() {
        UsuarioController usuarioController = new UsuarioController();
        HomeController homeController = new HomeController();
        LoginController loginController = new LoginController();
        PresupuestoController presupuestoController = new PresupuestoController();
        ProveedorController proveedorController = new ProveedorController();
        OperacionIngresoController operacionIngresoController = new OperacionIngresoController();
        OperacionEgresoController operacionEgresoController = new OperacionEgresoController();
        CategoriaController categoriaController = new CategoriaController();
        CriterioController criterioController = new CriterioController();
        MensajeController mensajeController = new MensajeController();
        ItemController itemController = new ItemController();
        VinculadorController vinculadorController = new VinculadorController();
        EntidadController entidadController = new EntidadController();
        ValidadorController validadorController = new ValidadorController();

        // REST
        UsuarioRestController usuarioRestController = new UsuarioRestController();
        DireccionRestController direccionRestController = new DireccionRestController();
        SectorRestController sectorRestController = new SectorRestController();

        // TODO: Investigar poner un .before para todas las rutas que se necesite validar que esté logueado

        // Login
        Spark.get(ServerRoutes.LOGIN, loginController::mostrar, Router.engine);
        Spark.post(ServerRoutes.LOGIN, loginController::iniciarSesion);
        Spark.get(ServerRoutes.LOGOUT, loginController::desconectarSesion);

        // Home con Redirect
        Spark.get(ServerRoutes.HOME, homeController::mostrarPantallaBienvenida, Router.engine);
        Spark.get(ServerRoutes.HOME, homeController::mostrar);

        // Validador
        Spark.get(ServerRoutes.VALIDAR, validadorController::ejecutarValidador);

        // Admin, solo para los que tienen permiso SUPER_USUARIO
        Spark.get(ServerRoutes.ENTIDADES_CREAR, entidadController::crear, Router.engine);
        Spark.get(ServerRoutes.ENTIDADES_EDITAR, entidadController::editar, Router.engine);
        Spark.get(ServerRoutes.ENTIDADES, entidadController::mostrar, Router.engine);

        Spark.post(ServerRoutes.ENTIDADES_EDITAR, entidadController::actualizarEntidad);
        Spark.post(ServerRoutes.ENTIDADES, entidadController::crearEntidad);
        //Spark.delete(ServerRoutes.ENTIDADES_EDITAR, adminController::eliminar);

        // Usuarios
        Spark.get(ServerRoutes.USUARIOS_CREAR, usuarioController::crear, Router.engine);
        Spark.get(ServerRoutes.USUARIOS_EDITAR, usuarioController::editar, Router.engine);
        Spark.get(ServerRoutes.USUARIOS, usuarioController::mostrarTodos, Router.engine);

        Spark.post(ServerRoutes.USUARIOS_EDITAR, usuarioController::actualizar);
        Spark.post(ServerRoutes.USUARIOS, usuarioController::crearUsuario);
        Spark.delete(ServerRoutes.USUARIOS_EDITAR, usuarioController::eliminar);

        Spark.get(ServerRoutes.USUARIOS_API, usuarioRestController::mostrar);

        // Operaciones
        //Spark.get(ServerRoutes.OPERACIONES, operacionController::mostrarTodos, Router.engine);
        //Spark.get(ServerRoutes.OPERACION, operacionController::mostrarOperacion, Router.engine);

        // Operaciones_Egreso
        Spark.get(ServerRoutes.OPERACION_EGRESO_CREAR, operacionEgresoController::pantallaCrearEgreso, Router.engine);
        Spark.get(ServerRoutes.OPERACION_EGRESO_EDITAR, operacionEgresoController::pantallaEditarEgreso, Router.engine);

        Spark.post(ServerRoutes.OPERACION_EGRESO_CREAR, operacionEgresoController::crearYEditarOperacionEgreso);
        Spark.post(ServerRoutes.OPERACION_EGRESO_EDITAR, operacionEgresoController::crearYEditarOperacionEgreso);

        Spark.get(ServerRoutes.OPERACIONES_EGRESOS, operacionEgresoController::mostrarTodosEgresos, Router.engine);
        Spark.get(ServerRoutes.OPERACION_EGRESOS, operacionEgresoController::mostrarOperacionEgreso, Router.engine);

        Spark.post(ServerRoutes.ELEGIR_PRESUPUESTO_OPERACION, operacionEgresoController::elegirPresupuesto);
        
        // Operaciones_Ingreso

        Spark.get(ServerRoutes.OPERACION_INGRESO_CREAR, operacionIngresoController::pantallaCrearIngreso, Router.engine);
        Spark.get(ServerRoutes.OPERACION_INGRESO_EDITAR, operacionIngresoController::pantallaEditarIngreso, Router.engine);

        Spark.post(ServerRoutes.OPERACION_INGRESO_CREAR, operacionIngresoController::crearYEditarOperacionIngreso);
        Spark.post(ServerRoutes.OPERACION_INGRESO_EDITAR, operacionIngresoController::crearYEditarOperacionIngreso);

        Spark.get(ServerRoutes.OPERACIONES_INGRESOS, operacionIngresoController::mostrarTodosIngresos, Router.engine);
        Spark.get(ServerRoutes.OPERACION_INGRESOS, operacionIngresoController::mostrarOperacionIngreso, Router.engine);

        // Presupuestos
        Spark.get(ServerRoutes.PRESUPUESTOS_CREAR, presupuestoController::crear, Router.engine);
        Spark.get(ServerRoutes.PRESUPUESTOS_EDITAR, presupuestoController::editar, Router.engine);
        Spark.get(ServerRoutes.PRESUPUESTOS, presupuestoController::mostrar, Router.engine);
        Spark.post(ServerRoutes.PRESUPUESTOS, presupuestoController::crearPresupuesto);
        Spark.post(ServerRoutes.PRESUPUESTOS_EDITAR, presupuestoController::editarPresupuesto);

        //Proveedores
        Spark.get(ServerRoutes.PROVEEDORES_CREAR, proveedorController::pantallaCrearProveedor, Router.engine);
        Spark.get(ServerRoutes.PROVEEDORES_EDITAR, proveedorController::pantallaEditarProveedor, Router.engine);

        Spark.get(ServerRoutes.PROVEEDORES, proveedorController::mostrarTodosProveedor, Router.engine);
        Spark.get(ServerRoutes.PROVEEDOR, proveedorController::mostrarProveedor, Router.engine);

        Spark.post(ServerRoutes.PROVEEDORES_CREAR, proveedorController::crearYEditarProveedor);
        Spark.post(ServerRoutes.PROVEEDORES_EDITAR, proveedorController::crearYEditarProveedor);


        //Spark.delete(ServerRoutes.PROVEEDORES_EDITAR, proveedorController::eliminar);

        // Categorias
        Spark.get(ServerRoutes.CATEGORIAS, categoriaController::mostrarTodos, Router.engine);
        Spark.get(ServerRoutes.CATEGORIA_CREAR, categoriaController::pantallaCrearCategoria, Router.engine);
        Spark.get(ServerRoutes.CATEGORIA_EDITAR, categoriaController::pantallaEditarCategoria, Router.engine);

        Spark.post(ServerRoutes.CATEGORIA_CREAR, categoriaController::crearYEditarCategoria);
        Spark.post(ServerRoutes.CATEGORIA_EDITAR, categoriaController::crearYEditarCategoria);

        // Criterios
        Spark.get(ServerRoutes.CRITERIOS, criterioController::mostrarTodos, Router.engine);
        Spark.get(ServerRoutes.CRITERIO_CREAR, criterioController::pantallaCrearCriterio, Router.engine);
        Spark.get(ServerRoutes.CRITERIO_EDITAR, criterioController::pantallaEditarCriterio, Router.engine);

        Spark.post(ServerRoutes.CRITERIO_CREAR, criterioController::crearYEditarCriterio);
        Spark.post(ServerRoutes.CRITERIO_EDITAR, criterioController::crearYEditarCriterio);

        // Bandeja
        Spark.get(ServerRoutes.MENSAJES, mensajeController::mostrarTodos, Router.engine);
        Spark.post(ServerRoutes.LEER_MENSAJE, mensajeController::leerMensaje);

        // Items
        Spark.get(ServerRoutes.ITEMS, itemController::mostrarTodos, Router.engine);
        Spark.get(ServerRoutes.ITEM_CREAR, itemController::pantallaCrearItem, Router.engine);
        Spark.get(ServerRoutes.ITEM_EDITAR, itemController::pantallaEditarItem, Router.engine);

        Spark.post(ServerRoutes.ITEM_CREAR, itemController::crearYEditarItem);
        Spark.post(ServerRoutes.ITEM_EDITAR, itemController::crearYEditarItem);

        // Vinculador
        Spark.get(ServerRoutes.VINCULAR, vinculadorController::pantallaVinculador, Router.engine);
        Spark.post(ServerRoutes.VINCULAR_OPERACIONES, vinculadorController::vincularOperaciones);

        // Direcciones
        Spark.get(ServerRoutes.DIRECCIONES_PAISES_API, direccionRestController::listadoPaises);
        Spark.get(ServerRoutes.DIRECCIONES_PROVINCIAS_API, direccionRestController::listadoProvincias);
        Spark.get(ServerRoutes.DIRECCIONES_CIUDADES_API, direccionRestController::listadoCiudades);

        // Archivos
        Spark.post(ServerRoutes.ARCHIVADOR_EGRESO_SUBIR, operacionEgresoController::subirDocumento);
        Spark.post(ServerRoutes.ARCHIVADOR_PRESUPUESTO_SUBIR, presupuestoController::subirDocumento);
        Spark.post(ServerRoutes.DESCARGAR_EGRESO_ARCHIVO, operacionEgresoController::downloadImage);
        Spark.post(ServerRoutes.DESCARGAR_PRESUPUESTO_ARCHIVO, presupuestoController::downloadImage);

        // Sectores
        Spark.get(ServerRoutes.SECTORES, sectorRestController::listadoSectores);
        Spark.get(ServerRoutes.ACTIVIDADES_POR_SECTOR, sectorRestController::listadoActividades);


        Spark.after("/*", (q, a) -> EntityManagerHelper.closeIfOpenEntityManager());
    }
}
