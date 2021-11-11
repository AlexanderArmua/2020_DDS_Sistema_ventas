package server;

public class ServerRoutes {
    private static final String CREAR = "/crear";

    public static final String PARAM_ID = "id";
    public static final String API = "/api";

    // Login
    public static final String LOGIN = "/login";
    public static final String LOGOUT = "/logout";

    // Validador
    public static final String VALIDAR = "/validar";

    // Home
    public static final String HOME = "/";

    // Admin
    public static final String ENTIDADES = "/entidades";
    public static final String ENTIDADES_CREAR = ENTIDADES + CREAR;
    public static final String ENTIDADES_EDITAR = ENTIDADES + "/:" + PARAM_ID;
    public static final String ENTIDADES_EDITAR_JURIDICA = ENTIDADES + "/juridica/:" + PARAM_ID;
    public static final String ENTIDADES_EDITAR_BASE = ENTIDADES + "/base/:" + PARAM_ID;

    // Usuarios
    public static final String USUARIOS = "/usuarios";
    public static final String USUARIOS_CREAR = USUARIOS + CREAR;
    public static final String USUARIOS_EDITAR = USUARIOS + "/:" + PARAM_ID;
    public static final String USUARIOS_API = API + USUARIOS_EDITAR;


    public static final String OPERACIONES_INGRESOS = "/operaciones/ingresos";
    public static final String OPERACION_INGRESOS = OPERACIONES_INGRESOS + "/:" + PARAM_ID;

    public static final String OPERACIONES_EGRESOS = "/operaciones/egresos";
    public static final String OPERACION_EGRESOS = OPERACIONES_EGRESOS + "/:" + PARAM_ID;

    public static final String OPERACION_EGRESO_CREAR = OPERACIONES_EGRESOS + CREAR;
    public static final String OPERACION_EGRESO_EDITAR = OPERACIONES_EGRESOS + "/:" + PARAM_ID;

    public static final String ELEGIR_PRESUPUESTO = "/elegir_presupuesto";
    public static final String ELEGIR_PRESUPUESTO_OPERACION = ELEGIR_PRESUPUESTO + "/:" + PARAM_ID;


    public static final String OPERACION_INGRESO_CREAR = OPERACIONES_INGRESOS + CREAR;
    public static final String OPERACION_INGRESO_EDITAR = OPERACIONES_INGRESOS + "/:" + PARAM_ID;


    // Presupuestos
    public static final String PRESUPUESTOS = "/presupuestos";
    public static final String PRESUPUESTOS_CREAR = PRESUPUESTOS + CREAR;
    public static final String PRESUPUESTOS_EDITAR = PRESUPUESTOS + "/:" + PARAM_ID;

    // Proveedores
    public static final String PROVEEDORES = "/proveedores";
    public static final String PROVEEDOR = PROVEEDORES + "/:" + PARAM_ID;

    public static final String PROVEEDORES_CREAR = PROVEEDORES + CREAR;
    public static final String PROVEEDORES_EDITAR = PROVEEDORES + "/:" + PARAM_ID;

    // Categorias
    public static final String CATEGORIAS = "/categorias";
    public static final String CATEGORIA_CREAR = CATEGORIAS + "/crear";
    public static final String CATEGORIA_EDITAR = CATEGORIAS + "/:" + PARAM_ID;

    // Criterios
    public static final String CRITERIOS = "/criterios";
    public static final String CRITERIO_CREAR = CRITERIOS + "/crear";
    public static final String CRITERIO_EDITAR = CRITERIOS + "/:" + PARAM_ID;

    // Bandeja de mensajes
    public static final String MENSAJES  = "/mensajes";
    public static final String MENSAJE_CREAR = MENSAJES + CREAR;
    public static final String LEER_MENSAJE = "/leer_mensaje" + "/:" + PARAM_ID;
    public static final String LEER = "/leer_mensaje";

    // Items
    public static final String ITEMS = "/items";
    public static final String ITEM_CREAR = ITEMS + "/crear";
    public static final String ITEM_EDITAR = ITEMS + "/:" + PARAM_ID;

    // Vinculador
    public static final String VINCULAR = "/vinculador";
    public static final String VINCULAR_OPERACIONES = VINCULAR + "/vincular";

    // Direccion
    public static final String DIRECCION = "/direccion";
    public static final String DIRECCIONES_PAISES_API = DIRECCION +"/paises";
    public static final String DIRECCIONES_PROVINCIAS_API = DIRECCION + "/provincias" + "/:" + PARAM_ID;
    public static final String DIRECCIONES_CIUDADES_API = DIRECCION + "/ciudades" + "/:" + PARAM_ID;

    // Archivos
    public static final String ARCHIVADOR = "/subirArchivo";
    public static final String ARCHIVADOR_EGRESO = ARCHIVADOR + "/egreso";
    public static final String ARCHIVADOR_EGRESO_SUBIR = ARCHIVADOR_EGRESO +  "/:" + PARAM_ID;
    public static final String ARCHIVADOR_PRESUPUESTO = ARCHIVADOR + "/presupuesto";
    public static final String ARCHIVADOR_PRESUPUESTO_SUBIR = ARCHIVADOR_PRESUPUESTO + "/:" + PARAM_ID;
    public static final String DESCARGAR = "/descargarArchivo";
    public static final String DESCARGAR_EGRESO = DESCARGAR + "/egreso";
    public static final String DESCARGAR_EGRESO_ARCHIVO = DESCARGAR_EGRESO + "/:" + PARAM_ID;
    public static final String DESCARGAR_PRESUPUESTO = DESCARGAR + "/presupuesto";
    public static final String DESCARGAR_PRESUPUESTO_ARCHIVO = DESCARGAR_PRESUPUESTO + "/:" + PARAM_ID;

    // Sector
    public static final String SECTORES = "/sectores";
    public static final String ACTIVIDADES = "/actividades";
    public static final String ACTIVIDADES_POR_SECTOR = ACTIVIDADES + "/:" + PARAM_ID;

}
