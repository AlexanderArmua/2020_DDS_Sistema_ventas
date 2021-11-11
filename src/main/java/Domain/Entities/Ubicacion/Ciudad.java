package Domain.Entities.Ubicacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table( name = "ciudad")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ciudad extends EntidadPersistente {

    public Ciudad(){}

    @Column( name = "idCiudad" )
    @JsonProperty("id")
    private String idCiudad;

    @Column( name = "nombreCiudad")
    @JsonProperty("name")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_provincia")
    private Provincia provincia;

    public Ciudad(String nombre) {
        this.setNombre(nombre);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(String id) {
        this.idCiudad = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}
