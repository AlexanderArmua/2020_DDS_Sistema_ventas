package Domain.Entities.Entidades.TipoEntidad;

import Domain.Entities.Entidades.Actividad;
import Domain.Entities.Entidades.Sector;
import Domain.Entities.Entidades.TipoSector;
import Util.Configuration.Configuration;
import javax.persistence.*;


@Entity
@DiscriminatorValue( "empresa")
public class Empresa extends TipoEntidadJuridica {

    public Empresa(){}

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn( name = "id_estrutura", referencedColumnName = "id")
    private TipoSector estructuraJuridica;

    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn( name = "id_actividad", referencedColumnName = "id")
    private Actividad actividad;

    @Column
    private Integer cantidadPersonal;

    @Column
    private Long promedioAnualVentas;

    public Empresa(int cantidadPersonal, Actividad actividad, Long promedioAnualVentas) throws Exception {
        if (!this.esValidoElPromedioDeventasAnual(promedioAnualVentas)) {
            throw new Exception("El monto ingresado es inválido");
        }

        this.setCantidadPersonal(cantidadPersonal);
        this.setActividad(actividad);
        this.setPromedioAnualVentas(promedioAnualVentas);

        this.calcularEstructuraJuridica();
    }

    private boolean esValidoElPromedioDeventasAnual(float promedioAnualVentas) {
        return promedioAnualVentas > Configuration.EMPRESA_MIN_PROMEDIO_VENTAS && promedioAnualVentas < Configuration.EMPRESA_MAX_PROMEDIO_VENTAS;
    }

    private void calcularEstructuraJuridica() throws Exception  {
        CategorizadorEstructuraEmpresa calculadora = CategorizadorEstructuraEmpresa.getCategorizador();
        this.setEstructuraJuridica(calculadora.calcularEstructura(this));
    }

    public Sector getSector(){return this.actividad.getSector();}

    //------------------------GETTER & SETTERS -------------------------------------------------
    public TipoSector getEstructuraJuridica() {
        return estructuraJuridica;
    }

    public void setEstructuraJuridica(TipoSector estructuraJuridica) {
        this.estructuraJuridica = estructuraJuridica;
    }

    public int getCantidadPersonal() {
        return cantidadPersonal;
    }

    public void setCantidadPersonal(int cantidadPersonal) {
        this.cantidadPersonal = cantidadPersonal;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Long getPromedioAnualVentas() {
        return promedioAnualVentas;
    }

    public void setPromedioAnualVentas(Long promedioAnualVentas) {
        this.promedioAnualVentas = promedioAnualVentas;
    }
}
