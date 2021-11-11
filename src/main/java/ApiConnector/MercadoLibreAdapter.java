package ApiConnector;

import Domain.Entities.Presupuestos.Moneda;
import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MercadoLibreAdapter implements IProveedorAdapter {

    private IProveedor proveedor;

    public MercadoLibreAdapter() {
        this.proveedor = new MercadoLibre();
    }

    @Override
    public List<Pais> getPaises() {
        String respuestaPaises = proveedor.getPaises();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Pais> listaPaises = new ArrayList<>();
        try {
            listaPaises = mapper.readValue(respuestaPaises, new TypeReference<List<Pais>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaPaises;
    }


    @Override
    public List<Provincia> getProvincias(String paisId) {
        String respuestaProvincias = proveedor.getProvincias(paisId);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Provincia> listaProvincias = new ArrayList<>();
        try {
            JsonNode jsonNode = mapper.readTree(respuestaProvincias);
            JsonNode array = jsonNode.get("states");
            Iterator<JsonNode> iterator = array.elements();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                String nombreProvincia = node.get("name").asText();
                String idProvincia = node.get("id").asText();
                Provincia provincia = new Provincia(nombreProvincia);
                provincia.setIdProvincia(idProvincia);
                listaProvincias.add(provincia);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaProvincias;
    }

    @Override
    public List<Ciudad> getCiudades(String provinciaId) {
        String respuestaCiudades = proveedor.getCiudades(provinciaId);
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Ciudad> listaCiudades = new ArrayList<>();
        try {
            JsonNode jsonNode = mapper.readTree(respuestaCiudades);
            JsonNode array = jsonNode.get("cities");
            Iterator<JsonNode> iterator = array.elements();
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                String nombreCiudad = node.get("name").asText();
                String idCiudad = node.get("id").asText();
                Ciudad ciudad = new Ciudad(nombreCiudad);
                ciudad.setIdCiudad(idCiudad);
                listaCiudades.add(ciudad);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listaCiudades;
    }

    @Override
    public List<Moneda> getMonedas() {
        String respuestaMonedas = proveedor.getMonedas();
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        List<Moneda> listaMonedas = new ArrayList<>();
        try {
            listaMonedas = mapper.readValue(respuestaMonedas, new TypeReference<List<Moneda>>(){});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaMonedas;
    }
}