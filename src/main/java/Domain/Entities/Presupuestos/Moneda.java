package Domain.Entities.Presupuestos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
@JsonIgnoreProperties(ignoreUnknown = true)
public class Moneda extends EntidadPersistente {


    @Column( name = "idMoneda")
    @JsonProperty("id")
    private String idMoneda;

    @Column
    @JsonProperty("description")
    private String descripcion;

    @Column
    @JsonProperty("symbol")
    private String symbol;

    @Column
    @JsonProperty("decimal_places")
    private int decimal_places;

    public String getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(String idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getDecimal_places() {
        return decimal_places;
    }

    public void setDecimal_places(int decimal_places) {
        this.decimal_places = decimal_places;
    }
}
