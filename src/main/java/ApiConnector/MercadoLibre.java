package ApiConnector;

public class MercadoLibre implements IProveedor {

    private String URL = "https://api.mercadolibre.com";

    @Override
    public String getPaises() {
        try {
            HttpConector conector = new HttpConector();
            String respuesta =  conector.conectarse(URL + "/classified_locations/countries", "GET", null);
            return respuesta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getProvincias(String paisId) {
        try {
            HttpConector conector = new HttpConector();
            String respuesta =  conector.conectarse(URL + "/classified_locations/countries/" + paisId, "GET", null);
            return respuesta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getCiudades(String provinciaId) {
        try {
            HttpConector conector = new HttpConector();
            String respuesta =  conector.conectarse(URL + "/classified_locations/states/" + provinciaId, "GET", null);
            return respuesta;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getMonedas() {
        try {
            HttpConector conector = new HttpConector();
            String respuesta =  conector.conectarse(URL + "/currencies", "GET", null);
            return respuesta;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
