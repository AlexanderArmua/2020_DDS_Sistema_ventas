package Domain.Entities.Operaciones.DocumentosComerciales;

import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class DocumentoComercial extends EntidadPersistente {

    @Enumerated(value = EnumType.STRING)
    private TipoDocumentoComercial tipoDocumentoComercial;

    @Column
    private int numero;

    @Column
    private String enlaceExterno;

    @Column
    private String nombreArchivo;

    public DocumentoComercial() {}

    public DocumentoComercial(int numero, TipoDocumentoComercial tipoDocumentoComercial, String enlaceExterno) {
        this.setNumero(numero);
        this.setTipoDocumentoComercial(tipoDocumentoComercial);
        this.setEnlaceExterno(enlaceExterno);
    }

    //------------------------GETTER & SETTERS -------------------------------------------------

    public TipoDocumentoComercial getTipoDocumentoComercial() {
        return tipoDocumentoComercial;
    }

    public void setTipoDocumentoComercial(TipoDocumentoComercial tipoDocumentoComercial) {
        this.tipoDocumentoComercial = tipoDocumentoComercial;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEnlaceExterno() {
        return enlaceExterno;
    }

    public void setEnlaceExterno(String enlaceExterno) {
        this.enlaceExterno = enlaceExterno;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
