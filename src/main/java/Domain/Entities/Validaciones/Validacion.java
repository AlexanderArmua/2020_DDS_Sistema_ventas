package Domain.Entities.Validaciones;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Repositories.Repositorio;
import Domain.Repositories.daos.DAOHibernate;
import Domain.Repositories.daos.Entities.MensajeRepository;
import db.EntidadPersistente;

import java.time.LocalDateTime;


public abstract class Validacion extends EntidadPersistente {

    private String mensajeOK;

    private String mensajeFAIL;

    private String titulo;

    public Validacion(String mensajeOK, String mensajeFAIL, String titulo) {
        this.mensajeOK = mensajeOK;
        this.mensajeFAIL = mensajeFAIL;
        this.titulo = titulo;
    }

    protected abstract Boolean esValido(ResultadoValidacion resultadoValidacion);

    public void validarcriterios(ResultadoValidacion resultadoValidacion) {
        Boolean resultado = this.esValido(resultadoValidacion);
        if(resultado != null) {
            LocalDateTime fechaMensaje = LocalDateTime.now();
            Mensaje mensaje = new Mensaje(fechaMensaje);
            mensaje.setTitulo(this.titulo);
            mensaje.setResultadoValidacion(resultadoValidacion);

            if (resultado) {
                mensaje.setDescripcion(this.mensajeOK);
                resultadoValidacion.addMensajeValidacion(mensaje);
            } else {
                mensaje.setDescripcion(this.mensajeFAIL);
                resultadoValidacion.addMensajeValidacion(mensaje);
                resultadoValidacion.setFueExitosa(false);
            }
        }
    }

}
