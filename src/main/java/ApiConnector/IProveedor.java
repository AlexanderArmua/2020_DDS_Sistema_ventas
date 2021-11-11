package ApiConnector;

public interface IProveedor {

    String getPaises();
    String getProvincias(String paisId);
    String getCiudades(String provinciaId);
    String getMonedas();

}
