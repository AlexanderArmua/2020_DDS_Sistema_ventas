package Domain.Entities.Entidades.TipoEntidad;

import Domain.Entities.Entidades.TipoSector;

public class CategorizadorEstructuraEmpresa {

    private static CategorizadorEstructuraEmpresa singletonCalculadora;

    public static CategorizadorEstructuraEmpresa getCategorizador() {
        if (singletonCalculadora == null) {
            singletonCalculadora = new CategorizadorEstructuraEmpresa();
        }

        return singletonCalculadora;
    }

    private CategorizadorEstructuraEmpresa() {}

    public TipoSector calcularEstructura(final Empresa empresa) {
        TipoSector sectorVentas = null;
        TipoSector sectorPersonal = null;

        for (TipoSector sector : empresa.getSector().getTipoSectores()) {
            if (sectorVentas == null && empresa.getPromedioAnualVentas() < sector.getValorVentas()) {
                sectorVentas = sector;
            }

            if (sectorPersonal == null && empresa.getCantidadPersonal() < sector.getValorPersonal()) {
                sectorPersonal = sector;
            }
        }

        if (sectorVentas == null) {
             sectorVentas = empresa.getSector().getSectorMasGrande();
        }
        if (sectorPersonal == null) {
            sectorPersonal = empresa.getSector().getSectorMasGrande();
        }

        return getSectorDeMayorTamanno(sectorVentas, sectorPersonal);
    }

    // En caso de que no guste, se pueden comparar los tipos de tamaño existentes. MedianaTramo1 > Micro y así.
    private TipoSector getSectorDeMayorTamanno(TipoSector sector1, TipoSector sector2) {
        if (sector1.getTamanno().compareTo(sector2.getTamanno()) > 0) {
            return sector1;
        } else {
            return sector2;
        }
    }
}
