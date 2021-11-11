package Domain.Entities.Entidades;

// TODO: Separar Sector Personal de Ventas que retornen la ClasificacionTIpo de manera hardcodeada??¿¿
// PRO 1: Evitaria pasar un parametro en el constructor.
// PRO 2: Al añadir TipoSector a un Sector no pueden ocurrir errores de agregar TipoPersonal en TipoVentas.
// Contra 1: Creamos 2 clases mas.
// Contra 2: Si surgen nuevos tipos de sectores aparte de Personal y Ventas, hay que agregar una clase nueva.

import db.EntidadPersistente;

import javax.persistence.*;

@Entity
@Table
public class TipoSector extends EntidadPersistente {

    public TipoSector(){

    }

    @ManyToOne(cascade = CascadeType.ALL)
    //@JoinColumn( name = "idSector"0, referencedColumnName = "id")
    private Sector sector;          // AGROPECUARIO

    @Enumerated(value = EnumType.STRING)
    private TamannoSector tamanno;  // MICRO

    @Column(name = "valor_clasificacion_ventas")
    private Long valorVentas;       // Ventas: 1.234.567

    @Column(name = "valor_clasificacion_personal")
    private Long valorPersonal;     // Personal: 12

    public TipoSector(Integer id, Sector sector, TamannoSector tamanno, Long valorVentas, Long valorPersonal) {
        this.setId(id);
        this.sector = sector;
        this.tamanno = tamanno;
        this.valorVentas = valorVentas;
        this.valorPersonal = valorPersonal;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public TamannoSector getTamanno() {
        return tamanno;
    }

    public void setTamanno(TamannoSector tamanno) {
        this.tamanno = tamanno;
    }

    public Long getValorVentas() {
        return valorVentas;
    }

    public void setValorVentas(Long ventas) {
        this.valorVentas = ventas;
    }

    public Long getValorPersonal() {
        return valorPersonal;
    }

    public void setValorPersonal(Long valorPersonal) {
        this.valorPersonal = valorPersonal;
    }

}
