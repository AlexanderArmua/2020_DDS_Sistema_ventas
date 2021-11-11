package Util.Configuration;


import Util.Tasks.ValidadorOperacionEgresoTask;

public class Configuration {
    // Desarrollo
    public static final boolean LOGIN_SEGURO = true;
    public static final int PUERTO_DEFAULT = 9000;

    // Vinculador
    public static final String URL_VINCULADOR_LOCAL = "http://localhost:8080/vincular";
    public static final String URL_VINCULADOR_ONLINE = "https://sleepy-coast-37130.herokuapp.com/vincular";
    public static final String METHOD_VINCULADOR = "POST";

    // Archivos
    public static final String RUTA_ARCHIVOS = "./tmp/uploads/";

    // Validador
    public static final  ValidadorOperacionEgresoTask validadorTask = new ValidadorOperacionEgresoTask();
    public static final long PERIODO_VALIDACION = 10000000L;
    public static final long DELAY_VALIDACION = 1000000L;

    // Empresa
    public static final float EMPRESA_MIN_PROMEDIO_VENTAS = 0;
    public static final float EMPRESA_MAX_PROMEDIO_VENTAS = 755740000;

    // Heroku
    public static final String ENVIRONMENT_PATH_PORT = "PORT";
    public static final String ENVIRONMENT_PATH_SERVER = "SERVER";

    public static final String PERSISTENCE_MODE_LOCAL = "local-persistance";
    public static final String PERSISTENCE_MODE_ONLINE = "online-persistance";

    private static final String ENVIRONMENT_PATH_VALUE_SERVER_LOCAL = "local";
    private static final String ENVIRONMENT_PATH_VALUE_SERVER_PROD = "getPersistenceMode";

    public static Boolean isRunningLocal() {
        String herokuServer = System.getenv(Configuration.ENVIRONMENT_PATH_SERVER);
        if (herokuServer == null || herokuServer.equals(ENVIRONMENT_PATH_VALUE_SERVER_LOCAL)) {
            return true;
        }
        return herokuServer.equals(ENVIRONMENT_PATH_VALUE_SERVER_PROD);
    }

    public static String getPersistenceMode() {
        if (isRunningLocal()) {
            return PERSISTENCE_MODE_LOCAL;
        }
        return PERSISTENCE_MODE_ONLINE;
    }
}
