package Domain.Entities.Operaciones.MediosDePago;

import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class MedioDePago extends EntidadPersistente {

    public MedioDePago(){

    }

    @Enumerated(value = EnumType.STRING)
    @Column( name = "tipoMedioPago")
    private TipoMedioPago tipoMedioPago;

    @Column
    private String numero;

    @Column
    private String vencimiento;

    @Column
    private String nombreApellido;

    @Column
    private String numeroSeguridad;

    @Column Integer cantCuotas;

    public MedioDePago(TipoMedioPago tipoMedioPago) {
        this.setTipoMedioPago(tipoMedioPago);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public TipoMedioPago getTipoMedioPago() {
        return tipoMedioPago;
    }

    public void setTipoMedioPago(TipoMedioPago tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getNombreApellido() {
        return nombreApellido;
    }

    public void setNombreApellido(String nombreApellido) {
        this.nombreApellido = nombreApellido;
    }

    public String getNumeroSeguridad() {
        return numeroSeguridad;
    }

    public void setNumeroSeguridad(String numeroSeguridad) {
        this.numeroSeguridad = numeroSeguridad;
    }

    public Integer getCantCuotas() {
        return cantCuotas;
    }

    public void setCantCuotas(Integer cantCuotas) {
        this.cantCuotas = cantCuotas;
    }
}
