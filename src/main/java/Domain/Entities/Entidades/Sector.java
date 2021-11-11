package Domain.Entities.Entidades;

import db.EntidadPersistente;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class Sector extends EntidadPersistente {

    public Sector(){}

    @Column( name = "nombre_sector" )
    private String nombre;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL)
    private List<TipoSector> tipoSectores = new LinkedList<>();

    @OneToMany(mappedBy = "sector",cascade = CascadeType.ALL )
    private List<Actividad> actividades = new LinkedList<>();

    public Sector(Integer id, String nombre) {
        this.setId(id);
        this.nombre = nombre;
    }

    public TipoSector getSectorMasGrande() {
        int lenTipoSector = tipoSectores.size();

        return tipoSectores.get(lenTipoSector - 1);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<TipoSector> getTipoSectores() {
        return tipoSectores;
    }

    public void setTipoSectores(List<TipoSector> tamannos) {
        this.tipoSectores = tamannos;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

}
