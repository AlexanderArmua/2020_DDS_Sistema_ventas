package Util.Tasks.Vinculador;

import ApiConnector.HttpConector;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Operaciones.Operacion.OperacionIngreso;
import Domain.Repositories.daos.Entities.OperacionRepository;
import Util.Configuration.Configuration;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Vinculador {

    public void vincular(List<OperacionEgreso> egresos, List<OperacionIngreso> ingresos, List<String> criterios) throws IOException {
        HttpConector httpConector = new HttpConector();

        Gson gson = new Gson();

        List<OperacionVinc> egresosVinc = new ArrayList<>();
        List<OperacionVinc> ingresosVinc = new ArrayList<>();

        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        egresos.stream().filter(o -> !o.isFueVinculada()).forEach(p -> {
            String date;
            Date fecha = Date.from(p.getFecha().atZone(ZoneId.systemDefault()).toInstant());

            date = simpleDateFormat.format(fecha);
            OperacionVinc op = new OperacionVinc();
            op.setId(p.getId());
            op.setFecha(date);
            op.setValorTotal(p.getValorTotal());
            op.setEgreso(true);
            egresosVinc.add(op);

        });

        ingresos.stream().filter(o -> !o.isFueVinculada()).forEach(p -> {
            String date;
            Date fecha = Date.from(p.getFecha().atZone(ZoneId.systemDefault()).toInstant());

            String dateHasta;

            date = simpleDateFormat.format(fecha);
            dateHasta = p.getFechaHasta().format(DateTimeFormatter.ofPattern(pattern));
            OperacionVinc op = new OperacionVinc();
            op.setId(p.getId());
            op.setFecha(date);
            op.setValorTotal(p.getValorTotal());
            op.setFechaHasta(dateHasta);
            ingresosVinc.add(op);
        });

        OperacionesDTO dto = new OperacionesDTO();
        dto.setOperacionesEgresos(egresosVinc);
        dto.setOperacionIngresos(ingresosVinc);
        dto.setCriterios(criterios);

        String json = gson.toJson(dto);

        final String response = httpConector.conectarse(Configuration.URL_VINCULADOR_ONLINE, Configuration.METHOD_VINCULADOR, json);

        OperacionResponse[] operaciones = gson.fromJson(response, OperacionResponse[].class);
        for(OperacionResponse op : operaciones) {
            if(op.isEgreso()) {
                OperacionEgreso operacionEgreso = egresos.stream().filter(p -> p.getId() == op.getId()).findFirst().get();
                if(op.getResponseOperacion().size() > 0)
                    operacionEgreso.setFueVinculada(true);
                OperacionRepository operacionRepository = new OperacionRepository();

                op.getResponseOperacion().forEach(o -> {
                    OperacionIngreso operacionIngreso = ingresos.stream().filter(i -> i.getId() == o.getId()).findFirst().get();
                    operacionIngreso.setFueVinculada(true);
                    operacionIngreso.addOperacionEgresoAsociada(operacionEgreso);
                    operacionRepository.modificar(operacionIngreso);
                });
            } else {
                OperacionIngreso operacionIngreso = ingresos.stream().filter(p -> p.getId() == op.getId()).findFirst().get();
                if(op.getResponseOperacion().size() > 0)
                    operacionIngreso.setFueVinculada(true);
                OperacionRepository operacionRepository = new OperacionRepository();

                op.getResponseOperacion().forEach(o -> {
                    OperacionEgreso operacionEgreso = egresos.stream().filter(i -> i.getId() == o.getId()).findFirst().get();
                    operacionEgreso.setFueVinculada(true);
                    operacionRepository.modificar(operacionEgreso);
                    operacionIngreso.addOperacionEgresoAsociada(operacionEgreso);
                });
                operacionRepository.modificar(operacionIngreso);
            }
        }
    }

}
