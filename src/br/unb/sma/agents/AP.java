package br.unb.sma.agents;

import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.Utils;
import jade.core.Agent;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AP extends Agent {

    Protocolo protocolo;
    String status;

    public AP(Protocolo protocolo) {
        this.protocolo = protocolo;
        status = "não iniciado";
        Utils.logInfo(protocolo.getCodProtocolo() + ": agente iniciado");
    }

    @Override
    public String toString() {
        return protocolo.getCodProtocolo() + " (" + getAgentState().toString() + ")";
    }
}
