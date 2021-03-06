/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;


/**
 * Ttipos de relacionamento entre dois processos
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class TipoRelacionamento implements Serializable {

    private static final long serialVersionUID = 125783184;

    private Byte codTipoRelac;
    private String desTipoRelac;

    public TipoRelacionamento() {
    }

    public TipoRelacionamento(TipoRelacionamento value) {
        this.codTipoRelac = value.codTipoRelac;
        this.desTipoRelac = value.desTipoRelac;
    }

    public TipoRelacionamento(
            Byte codTipoRelac,
            String desTipoRelac
    ) {
        this.codTipoRelac = codTipoRelac;
        this.desTipoRelac = desTipoRelac;
    }

    public Byte getCodTipoRelac() {
        return this.codTipoRelac;
    }

    public void setCodTipoRelac(Byte codTipoRelac) {
        this.codTipoRelac = codTipoRelac;
    }

    public String getDesTipoRelac() {
        return this.desTipoRelac;
    }

    public void setDesTipoRelac(String desTipoRelac) {
        this.desTipoRelac = desTipoRelac;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("TipoRelacionamento (");

        sb.append(codTipoRelac);
        sb.append(", ").append(desTipoRelac);

        sb.append(")");
        return sb.toString();
    }
}
