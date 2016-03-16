/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;


/**
 * Magistrados que atuam em Orgaos Judicantes para julgar processos
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Magistrado implements Serializable {

    private static final long serialVersionUID = 1950459652;

    private String codMagistrado;
    private String nomMagistrado;
    private Boolean indAdministracao;

    public Magistrado() {
    }

    public Magistrado(Magistrado value) {
        this.codMagistrado = value.codMagistrado;
        this.nomMagistrado = value.nomMagistrado;
        this.indAdministracao = value.indAdministracao;
    }

    public Magistrado(
            String codMagistrado,
            String nomMagistrado,
            Boolean indAdministracao
    ) {
        this.codMagistrado = codMagistrado;
        this.nomMagistrado = nomMagistrado;
        this.indAdministracao = indAdministracao;
    }

    public String getCodMagistrado() {
        return this.codMagistrado;
    }

    public void setCodMagistrado(String codMagistrado) {
        this.codMagistrado = codMagistrado;
    }

    public String getNomMagistrado() {
        return this.nomMagistrado;
    }

    public void setNomMagistrado(String nomMagistrado) {
        this.nomMagistrado = nomMagistrado;
    }

    public Boolean getIndAdministracao() {
        return this.indAdministracao;
    }

    public void setIndAdministracao(Boolean indAdministracao) {
        this.indAdministracao = indAdministracao;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Magistrado (");

        sb.append(codMagistrado);
        sb.append(", ").append(nomMagistrado);
        sb.append(", ").append(indAdministracao);

        sb.append(")");
        return sb.toString();
    }
}
