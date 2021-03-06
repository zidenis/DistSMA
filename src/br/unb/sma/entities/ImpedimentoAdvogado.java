/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;
import java.sql.Date;


/**
 * Impedimento de Magistrados em relacao a Advogados que atuam em processos
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ImpedimentoAdvogado implements Serializable {

    private static final long serialVersionUID = -195513804;

    private String codMagistrado;
    private Integer numAdvogado;
    private Date dtaRegistro;

    public ImpedimentoAdvogado() {
    }

    public ImpedimentoAdvogado(ImpedimentoAdvogado value) {
        this.codMagistrado = value.codMagistrado;
        this.numAdvogado = value.numAdvogado;
        this.dtaRegistro = value.dtaRegistro;
    }

    public ImpedimentoAdvogado(
            String codMagistrado,
            Integer numAdvogado,
            Date dtaRegistro
    ) {
        this.codMagistrado = codMagistrado;
        this.numAdvogado = numAdvogado;
        this.dtaRegistro = dtaRegistro;
    }

    public String getCodMagistrado() {
        return this.codMagistrado;
    }

    public void setCodMagistrado(String codMagistrado) {
        this.codMagistrado = codMagistrado;
    }

    public Integer getNumAdvogado() {
        return this.numAdvogado;
    }

    public void setNumAdvogado(Integer numAdvogado) {
        this.numAdvogado = numAdvogado;
    }

    public Date getDtaRegistro() {
        return this.dtaRegistro;
    }

    public void setDtaRegistro(Date dtaRegistro) {
        this.dtaRegistro = dtaRegistro;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ImpedimentoAdvogado (");

        sb.append(codMagistrado);
        sb.append(", ").append(numAdvogado);
        sb.append(", ").append(dtaRegistro);

        sb.append(")");
        return sb.toString();
    }
}
