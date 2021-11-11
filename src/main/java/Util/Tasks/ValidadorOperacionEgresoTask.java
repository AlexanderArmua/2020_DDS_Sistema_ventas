package Util.Tasks;

import Domain.Entities.Entidades.EntidadJuridica;
import Domain.Entities.Usuarios.Usuario;
import Domain.Entities.Validaciones.ResultadoValidacion;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Entities.Entidades.Entidad;
import Domain.Entities.Operaciones.Operacion.Operacion;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Repositories.daos.Entities.UsuarioRepository;
import Util.Configuration.Configuration;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class ValidadorOperacionEgresoTask extends TimerTask {

    public void ejecutar(){
        Timer timer = new Timer("Timer");

        long delay = Configuration.DELAY_VALIDACION;
        long period = Configuration.PERIODO_VALIDACION;
        timer.scheduleAtFixedRate(new ValidadorOperacionEgresoTask(), delay, period);
    }

    @Override
    public void run(){
        Repositorio<EntidadJuridica> repositorio = new Repositorio<>(new DAOHibernate<>(EntidadJuridica.class));

        List<EntidadJuridica> entidades = repositorio.buscarTodos();

        for(EntidadJuridica entidad: entidades){
            ejecutarParaEntidad(entidad);
        }

    }

    public void ejecutarParaEntidad(EntidadJuridica entidad) {
        Repositorio<EntidadJuridica> repositorio = new Repositorio<>(new DAOHibernate<>(EntidadJuridica.class));
        Repositorio<ResultadoValidacion> repoResultadoValidacion = new Repositorio<>(new DAOHibernate<>(ResultadoValidacion.class));
        UsuarioRepository repoUsuario = new UsuarioRepository();

        Set<Operacion> operaciones = new HashSet<>(entidad.getOperaciones());
        operaciones.stream()
                .filter(Operacion::isNecesitaValidacion)
                .map(o -> (OperacionEgreso) o)
                .forEach(e -> entidad.getValidador().validarOperacionEgreso(e, repoResultadoValidacion, repoUsuario));

        repositorio.modificar(entidad);
    }
}
