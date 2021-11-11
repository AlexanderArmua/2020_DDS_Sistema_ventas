package Domain.Entities.Validaciones;

import Domain.Entities.BandejaDeEntrada.Mensaje;
import Domain.Entities.Operaciones.Operacion.OperacionEgreso;
import db.EntidadPersistente;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
public class ResultadoValidacion extends EntidadPersistente {

    @Column
    private Boolean fueExitosa = true;

    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn( name = "id_operacionEgreso", referencedColumnName = "id")
    private OperacionEgreso operacionEgreso;

    @OneToMany(mappedBy = "resultadoValidacion",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Mensaje> mensajes = new LinkedList<>();

    public ResultadoValidacion() {
    }

    public ResultadoValidacion(OperacionEgreso opEgreso) {
        this.operacionEgreso = opEgreso;
    }

    public void addMensajeValidacion(Mensaje mensaje) {
        this.mensajes.add(mensaje);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------


    public boolean isFueExitosa() {
        return fueExitosa;
    }
    public void setFueExitosa(boolean fueExitosa) {
        this.fueExitosa = fueExitosa;
    }

    public OperacionEgreso getOperacionEgreso() {
        return operacionEgreso;
    }

    public void setOperacionEgreso(OperacionEgreso operacionEgreso) {
        this.operacionEgreso = operacionEgreso;
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }
}
