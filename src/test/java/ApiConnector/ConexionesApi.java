package ApiConnector;

import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionEgreso;
import Domain.Entities.Operaciones.Operacion.DataObject.DOOperacionIngreso;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Repositories.daos.Entities.OperacionRepository;
import Util.Tasks.Vinculador.Vinculador;
import Domain.Entities.Ubicacion.Ciudad;
import Domain.Entities.Ubicacion.Pais;
import Domain.Entities.Ubicacion.Provincia;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConexionesApi {

    @Test
    public void testeoPaises() {
        MercadoLibreAdapter mercadolibre = new MercadoLibreAdapter();

        Pais argentina = mercadolibre.getPaises().stream().filter(p -> p.getNombre().equals("Argentina")).findFirst().get();
        //mercadolibre.getPaises().stream().forEach(p -> System.out.println(p.getNombre()));

        Provincia capitalFederal = mercadolibre.getProvincias(argentina.getIdPais()).stream()
                        .filter(p -> p.getNombre().equals("Capital Federal")).findFirst().get();
        //mercadolibre.getProvincias("AR").stream().forEach(p -> System.out.println(p.getNombre()));

        List<Ciudad> ciudadesCapitalFederal = mercadolibre.getCiudades(capitalFederal.getIdProvincia());

        Assert.assertEquals(ciudadesCapitalFederal.size(), 1);
    }

    @Test
    public void testeoMonedas() {
        MercadoLibreAdapter ml = new MercadoLibreAdapter();

        ml.getMonedas().forEach(m -> System.out.println(m.getDescripcion()));
    }

    //@Test
    public void testeoVinculador() throws ParseException {
        Vinculador vinc = new Vinculador();
        try {
            List<OperacionEgreso> egre = new ArrayList<>();
            List<OperacionIngreso> ingr = new ArrayList<>();
            List<String> criterios = new ArrayList<>();
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            Date beforeDate = simpleDateFormat.parse("01/06/2020");

            LocalDateTime beforeLocalDate = beforeDate.toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();

            DOOperacionEgreso doOperacionEgreso = new DOOperacionEgreso(null, null, 20.0f, null, null,null, false);
            OperacionEgreso operacionEgreso = new OperacionEgreso(doOperacionEgreso);
            operacionEgreso.setFecha(LocalDateTime.now());

            DOOperacionEgreso doOperacionEgreso2 = new DOOperacionEgreso(null, null, 50.0f, null, null,null, false);
            OperacionEgreso operacionEgreso2 = new OperacionEgreso(doOperacionEgreso2);
            operacionEgreso2.setFecha(LocalDateTime.now());


            DOOperacionEgreso doOperacionEgreso3 = new DOOperacionEgreso(null, null, 5f, null, null,null, false);
            OperacionEgreso operacionEgreso3 = new OperacionEgreso(doOperacionEgreso3);
            operacionEgreso3.setFecha(LocalDateTime.now());

            egre.add(operacionEgreso);
            egre.add(operacionEgreso2);
            egre.add(operacionEgreso3);

            DOOperacionIngreso doIngreso = new DOOperacionIngreso(60f, null);
            OperacionIngreso operacionIngreso = new OperacionIngreso(doIngreso);
            operacionIngreso.setFecha(beforeLocalDate);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
            LocalDate afterDate = LocalDate.parse("01/10/2021", formatter);
            operacionIngreso.setFechaHasta(afterDate);

            DOOperacionIngreso doIngreso2 = new DOOperacionIngreso(25f, null);
            OperacionIngreso operacionIngreso2 = new OperacionIngreso(doIngreso2);
            operacionIngreso2.setFecha(beforeLocalDate);
            operacionIngreso2.setFechaHasta(afterDate);

            ingr.add(operacionIngreso);
            ingr.add(operacionIngreso2);

            OperacionRepository repo = new OperacionRepository();
            repo.agregar(operacionEgreso);
            repo.agregar(operacionEgreso2);
            repo.agregar(operacionEgreso3);
            repo.agregar(operacionIngreso);
            repo.agregar(operacionIngreso2);

            criterios.add("1");

            vinc.vincular(egre, ingr, criterios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
