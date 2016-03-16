/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;


/**
 * Relaciona as Partes interessadas em Processos Judiciais
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ProcessoParte implements Serializable {

    private static final long serialVersionUID = 1011079008;

    private Long codProcesso;
    private Long codParte;

    public ProcessoParte() {
    }

    public ProcessoParte(ProcessoParte value) {
        this.codProcesso = value.codProcesso;
        this.codParte = value.codParte;
    }

    public ProcessoParte(
            Long codProcesso,
            Long codParte
    ) {
        this.codProcesso = codProcesso;
        this.codParte = codParte;
    }

    public Long getCodProcesso() {
        return this.codProcesso;
    }

    public void setCodProcesso(Long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public Long getCodParte() {
        return this.codParte;
    }

    public void setCodParte(Long codParte) {
        this.codParte = codParte;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ProcessoParte (");

        sb.append(codProcesso);
        sb.append(", ").append(codParte);

        sb.append(")");
        return sb.toString();
    }
}
