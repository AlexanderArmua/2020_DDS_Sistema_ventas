package Domain.Entities.Ubicacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table(name = "pais")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pais extends EntidadPersistente {

    public Pais(){}

    @Column( name = "idPais")
    @JsonProperty("id")
    private String idPais;

    @Column(name = "nombrePais")
    @JsonProperty("name")
    private String nombre;

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getIdPais() {
        return idPais;
    }

    public void setIdPais(String id) {
        this.idPais = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
