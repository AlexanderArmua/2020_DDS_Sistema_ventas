package Domain.Entities.Entidades.TipoEntidad;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("osc")
public class OSC extends TipoEntidadJuridica {

    public OSC() {}
}
