/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import br.unb.sma.utils.Status;

import java.io.Serializable;


/**
 * Responsavel pela realizacao de uma distribuicao de processo
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Distribuidor implements Serializable, AgentEntity {

    private static final long serialVersionUID = 58784861;

    private String codDistribuidor;
    private String nomDistribuidor;

    private String status = Status.LOADED;

    public Distribuidor() {
    }

    public Distribuidor(Distribuidor value) {
        this.codDistribuidor = value.codDistribuidor;
        this.nomDistribuidor = value.nomDistribuidor;
    }

    public Distribuidor(
            String codDistribuidor,
            String nomDistribuidor
    ) {
        this.codDistribuidor = codDistribuidor;
        this.nomDistribuidor = nomDistribuidor;
    }

    @Override
    public String toString() {
        return codDistribuidor + (status.equals(Status.LOADED) ? status : " (" + status + ")");
    }

    @Override
    public String getAgentName() {
        return codDistribuidor;
    }

    @Override
    public String getClassName() {
        return "br.unb.sma.agents.AD";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodDistribuidor() {
        return this.codDistribuidor;
    }

    public void setCodDistribuidor(String codDistribuidor) {
        this.codDistribuidor = codDistribuidor;
    }

    public String getNomDistribuidor() {
        return this.nomDistribuidor;
    }

    public void setNomDistribuidor(String nomDistribuidor) {
        this.nomDistribuidor = nomDistribuidor;
    }

}
