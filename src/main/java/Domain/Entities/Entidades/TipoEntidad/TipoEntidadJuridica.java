package Domain.Entities.Entidades.TipoEntidad;

import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn( name = "tipo")
public abstract class TipoEntidadJuridica extends EntidadPersistente {
    @Column(name="tipo", nullable=false, updatable=false, insertable=false)
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
