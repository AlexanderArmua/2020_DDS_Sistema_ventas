package Domain.Entities.Ubicacion.DataObject;

import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;

public class DODireccion {

    private String calle;

    private String altura;

    private String piso;

    private String departamento;

    private String codigoPostal;

    private Ciudad ciudad;

    private Provincia provincia;

    private Pais pais;

    public DODireccion(String calle, String altura, String piso, String departamento,
                       Ciudad ciudad, Provincia provincia, Pais pais){

        this.setCalle(calle);
        this.setAltura(altura);
        this.setPiso(piso);
        this.setDepartamento(departamento);
        this.setCiudad(ciudad);
        this.setProvincia(provincia);
        this.setPais(pais);

    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
