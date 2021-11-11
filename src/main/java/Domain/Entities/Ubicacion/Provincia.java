package Domain.Entities.Ubicacion;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table( name = "provincia")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Provincia extends EntidadPersistente {

    public Provincia() {}

    @JsonProperty("id")
    @Column( name = "idProvincia")
    private String idProvincia;

    @Column(name = "nombreProvincia")
    @JsonProperty("name")
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

    public Provincia(String nombre) {
        this.setNombre(nombre);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdProvincia() {
        return idProvincia;
    }

    public void setIdProvincia(String id) {
        this.idProvincia = id;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }
}
