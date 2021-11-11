package Domain.Entities.Entidades;

import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class Actividad extends EntidadPersistente {

    public Actividad(){

    }

    @Column
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_sector", referencedColumnName = "id")
    private Sector sector;

    public Actividad(String descripcion, Sector sector) {
        this.descripcion = descripcion;
        this.sector = sector;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }
}
