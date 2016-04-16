/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import br.unb.sma.utils.Status;

import java.io.Serializable;


/**
 * Magistrados que atuam em Orgaos Judicantes para julgar processos
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Magistrado implements Serializable, AgentEntity {

    private static final long serialVersionUID = 1950459652;

    private String codMagistrado;
    private String nomMagistrado;
    private Boolean indAdministracao;

    private String status = Status.LOADED;

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

    @Override
    public String toString() {
        return codMagistrado + (status.equals(Status.LOADED) ? status : " (" + status + ")");
    }

    @Override
    public String getAgentName() {
        return codMagistrado;
    }

    @Override
    public String getClassName() {
        return "br.unb.sma.agents.AM";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

}
