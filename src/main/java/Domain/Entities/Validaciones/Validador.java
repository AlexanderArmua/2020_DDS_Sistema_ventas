package Domain.Entities.Validaciones;

import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import Domain.Entities.Usuarios.Usuario;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.UsuarioRepository;

import java.util.LinkedList;
import java.util.List;

public class Validador {
    private List<Validacion> validacionesARealizar = new LinkedList<>();

    public List<Validacion> getValidacionesARealizar() {
        return validacionesARealizar;
    }

    public void addValidacionesARealizar(Validacion validacionesARealizar) {
        this.validacionesARealizar.add(validacionesARealizar);
    }

    public void validarOperacionEgreso(OperacionEgreso operacionEgreso, Repositorio<ResultadoValidacion> repoResultadoValidacion, Repositorio<Usuario> repoUsuario) {
        if (!operacionEgreso.isNecesitaPresupuesto()) {
            operacionEgreso.finalizarValidacion();

            return;
        }

        ResultadoValidacion resultadoValidacion = new ResultadoValidacion(operacionEgreso);

        validacionesARealizar.forEach(validacion -> {
            validacion.validarcriterios(resultadoValidacion);

            repoResultadoValidacion.agregar(resultadoValidacion);
        });

        this.avisarUsuariosResultadoValidacion(resultadoValidacion, repoUsuario);

        operacionEgreso.finalizarValidacion();
    }

    private void avisarUsuariosResultadoValidacion(ResultadoValidacion resultadoValidacion, Repositorio<Usuario> usuarioRepositorio) {

        List<Usuario> revisores = resultadoValidacion.getOperacionEgreso().getRevisores();

        revisores.forEach(revisor -> {
            revisor.addMensaje( resultadoValidacion.getMensajes() );
            usuarioRepositorio.modificar(revisor);
        });

    }
}
