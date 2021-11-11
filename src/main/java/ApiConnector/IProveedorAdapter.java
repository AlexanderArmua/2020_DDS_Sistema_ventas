package ApiConnector;

import Domain.Entities.Presupuestos.Moneda;
import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;

import java.util.List;

public interface IProveedorAdapter {

    List<Pais> getPaises();
    List<Provincia> getProvincias(String paisId);
    List<Ciudad> getCiudades(String provinciaId);
    List<Moneda> getMonedas();

}
