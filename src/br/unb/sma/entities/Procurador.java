/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;


/**
 * Procuradores que atuam em Processos Judiciais
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Procurador implements Serializable {

    private static final long serialVersionUID = -1164633009;

    private Integer numProcurador;
    private String nomProcurador;
    private String nomProcuradoria;

    public Procurador() {
    }

    public Procurador(Procurador value) {
        this.numProcurador = value.numProcurador;
        this.nomProcurador = value.nomProcurador;
        this.nomProcuradoria = value.nomProcuradoria;
    }

    public Procurador(
            Integer numProcurador,
            String nomProcurador,
            String nomProcuradoria
    ) {
        this.numProcurador = numProcurador;
        this.nomProcurador = nomProcurador;
        this.nomProcuradoria = nomProcuradoria;
    }

    public Integer getNumProcurador() {
        return this.numProcurador;
    }

    public void setNumProcurador(Integer numProcurador) {
        this.numProcurador = numProcurador;
    }

    public String getNomProcurador() {
        return this.nomProcurador;
    }

    public void setNomProcurador(String nomProcurador) {
        this.nomProcurador = nomProcurador;
    }

    public String getNomProcuradoria() {
        return this.nomProcuradoria;
    }

    public void setNomProcuradoria(String nomProcuradoria) {
        this.nomProcuradoria = nomProcuradoria;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Procurador (");

        sb.append(numProcurador);
        sb.append(", ").append(nomProcurador);
        sb.append(", ").append(nomProcuradoria);

        sb.append(")");
        return sb.toString();
    }
}
