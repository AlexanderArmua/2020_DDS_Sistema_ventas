package Entidades.TipoEntidad.CategorizadorEstructura;

import Domain.Entities.Entidades.TipoEntidad.CategorizadorEstructuraEmpresa;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOMemoria;
import Domain.Entities.Entidades.Actividad;
import Domain.Entities.Entidades.Sector;
import Domain.Entities.Entidades.TamannoSector;
import Domain.Entities.Entidades.TipoEntidad.Empresa;
import Domain.Entities.Entidades.TipoSector;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class CategorizadorEstructuraTest {

    private CategorizadorEstructuraEmpresa categorizador;
    private List<Actividad> actividades = new LinkedList<>();

    Repositorio<Sector> repoSector = null;

    /**
     * Agropecuario
     *      Ventas: 12890000, 48480000, 345430000, 547890000
     *      Personal: 5, 10, 50, 215
     */

    @Before
    public void before() {
       this.initializeRepository();
    }

    @Test
    public void empresa_segun_tipo_es_mediana_tramo_1_personal() throws Exception {
        Empresa empresa = new Empresa(49, this.actividades.get(0), 5000L);

        TipoSector sector = this.categorizador.calcularEstructura(empresa);

        Assert.assertEquals(TamannoSector.MEDIANA_TRAMO_1, sector.getTamanno());
    }

    @Test
    public void empresa_segun_tipo_es_mediana_tramo_1_ventas() throws Exception {
        Empresa empresa = new Empresa(1000, this.actividades.get(0), 557890000L);

        TipoSector sector = this.categorizador.calcularEstructura(empresa);

        Assert.assertEquals(TamannoSector.MEDIANA_TRAMO_2, sector.getTamanno());
        Assert.assertEquals(547890000L, (long) sector.getValorVentas());
    }

    @Test
    public void calcular_estructura_empresa_como_mediana_tramo_2_y_personal() throws Exception {
        Empresa empresa = new Empresa(50, this.actividades.get(0), 5000L);

        TipoSector tipoSector = categorizador.calcularEstructura(empresa);

        Assert.assertEquals(TamannoSector.MEDIANA_TRAMO_2, tipoSector.getTamanno());
        Assert.assertEquals(215L, (long) tipoSector.getValorPersonal());
    }

    @Test
    public void calcular_estructura_empresa_como_mediana_tramo_2_y_ventas() throws Exception {
        Empresa empresa = new Empresa(10, this.actividades.get(0), 547990000L);

        TipoSector tipoSector = categorizador.calcularEstructura(empresa);

        Assert.assertEquals(TamannoSector.MEDIANA_TRAMO_2, tipoSector.getTamanno());
        Assert.assertEquals(547890000L, (long) tipoSector.getValorVentas());
    }

    private void saveSectoresAgropecuariosInSector(Sector sector) {
        sector.getTipoSectores().add(new TipoSector(1, sector, TamannoSector.MICRO, 12890000L, 5L));
        sector.getTipoSectores().add(new TipoSector(2, sector, TamannoSector.PEQUENA, 48480000L, 10L));
        sector.getTipoSectores().add(new TipoSector(3, sector, TamannoSector.MEDIANA_TRAMO_1, 345430000L, 50L));
        sector.getTipoSectores().add(new TipoSector(4, sector, TamannoSector.MEDIANA_TRAMO_2, 547890000L, 215L));

        this.repoSector.modificar(sector);
    }

    private void saveActividadInSector(Sector sector) {
        Actividad actividad = new Actividad("Ganado", this.repoSector.buscar(1));

        this.actividades.add(actividad);

        sector.setActividades(actividades);

        this.repoSector.modificar(sector);
    }

    private void initializeRepository() {
        this.repoSector = new Repositorio<>(new DAOMemoria<>(new LinkedList<>()));

        this.categorizador = CategorizadorEstructuraEmpresa.getCategorizador();

        this.repoSector.agregar(new Sector(1, "AGROPECUARIO"));

        this.saveSectoresAgropecuariosInSector(this.repoSector.buscar(1));

        this.saveActividadInSector(this.repoSector.buscar(1));
    }
}
