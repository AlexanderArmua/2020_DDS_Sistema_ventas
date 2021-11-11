package ApiConnector;

import Domain.Entities.Presupuestos.Moneda;
import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;

import java.util.List;

public class SincronizadorUbicaciones {
    private final Repositorio<Pais> repoPais;
    private final Repositorio<Provincia> repoProvincia;
    private final Repositorio<Ciudad> repoCiudad;
    private final Repositorio<Moneda> repoMoneda;

    public SincronizadorUbicaciones() {
        this.repoMoneda = new Repositorio<>(new DAOHibernate<>(Moneda.class));
        this.repoPais = new Repositorio<>(new DAOHibernate<>(Pais.class));
        this.repoProvincia = new Repositorio<>(new DAOHibernate<>(Provincia.class));
        this.repoCiudad = new Repositorio<>(new DAOHibernate<>(Ciudad.class));
    }

    public void sincronizarUbicaciones(){
        this.sincronizarPaises();
        this.sincronizarProvincias();
        this.sincronizarCiudades();
        this.sincronizarMonedas();
    }

    private void sincronizarPaises() {
        if(this.repoPais.buscarTodos().isEmpty()){
            MercadoLibreAdapter mercadolibre = new MercadoLibreAdapter();
            List<Pais> paises = mercadolibre.getPaises();
            for(Pais pais: paises){
                this.repoPais.agregar(pais);
            }
        }
    }

    private void sincronizarProvincias() {
        if(this.repoProvincia.buscarTodos().isEmpty()){
            MercadoLibreAdapter mercadolibre = new MercadoLibreAdapter();
            List<Provincia> provincias;
            List<Pais> paises = this.repoPais.buscarTodos();

            for(Pais pais: paises){
                provincias = mercadolibre.getProvincias(pais.getIdPais());

                for(Provincia provincia: provincias){
                    provincia.setPais(pais);
                    this.repoProvincia.agregar(provincia);
                }
            }
        }
    }

    private void sincronizarCiudades() {
        if(this.repoCiudad.buscarTodos().isEmpty()){
            MercadoLibreAdapter mercadolibre = new MercadoLibreAdapter();
            List<Ciudad> ciudades;
            List<Provincia> provincias = this.repoProvincia.buscarTodos();

            for(Provincia provincia: provincias){
                ciudades = mercadolibre.getCiudades(provincia.getIdProvincia());

                for(Ciudad ciudad: ciudades){
                    ciudad.setProvincia(provincia);
                    this.repoCiudad.agregar(ciudad);
                }
            }
        }
    }

    private void sincronizarMonedas() {
        if(this.repoMoneda.buscarTodos().isEmpty()){
            MercadoLibreAdapter mercadolibre = new MercadoLibreAdapter();
            List<Moneda> monedas = mercadolibre.getMonedas();
            for(Moneda moneda: monedas){
                this.repoMoneda.agregar(moneda);
            }
        }
    }

}
