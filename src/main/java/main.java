import ApiConnector.SincronizadorUbicaciones;

import server.Server;

import static Util.Configuration.Configuration.validadorTask;


public class main {

    public static void main(String[] args) {
        //SincronizadorUbicaciones sincronizador = new SincronizadorUbicaciones();
        //sincronizador.sincronizarUbicaciones();

        validadorTask.ejecutar();

        Server.main(args);
    }

}
