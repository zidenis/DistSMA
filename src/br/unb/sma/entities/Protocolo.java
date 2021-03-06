/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import br.unb.sma.agents.AGP;

import java.io.Serializable;


/**
 * Unidades de protocolo
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Protocolo implements Serializable, AgentEntity {

    private static final long serialVersionUID = 7641848;

    private String codProtocolo;
    private String nomProtocolo;
    private Byte numTribunal;

    private String status = AGP.STATUS_LOADED;

    public Protocolo() {
    }

    public Protocolo(Protocolo value) {
        this.codProtocolo = value.codProtocolo;
        this.nomProtocolo = value.nomProtocolo;
        this.numTribunal = value.numTribunal;
    }

    public Protocolo(
            String codProtocolo,
            String nomProtocolo,
            Byte numTribunal
    ) {
        this.codProtocolo = codProtocolo;
        this.nomProtocolo = nomProtocolo;
        this.numTribunal = numTribunal;
    }

    @Override
    public String toString() {
        return nomProtocolo + (status.equals(AGP.STATUS_LOADED) ? status : " (" + status + ")");
    }

    @Override
    public String getAgentName() {
        return codProtocolo;
    }

    @Override
    public String getClassName() {
        return "br.unb.sma.agents.AP";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCodProtocolo() {
        return this.codProtocolo;
    }

    public void setCodProtocolo(String codProtocolo) {
        this.codProtocolo = codProtocolo;
    }

    public String getNomProtocolo() {
        return this.nomProtocolo;
    }

    public void setNomProtocolo(String nomProtocolo) {
        this.nomProtocolo = nomProtocolo;
    }

    public Byte getNumTribunal() {
        return this.numTribunal;
    }

    public void setNumTribunal(Byte numTribunal) {
        this.numTribunal = numTribunal;
    }

}
