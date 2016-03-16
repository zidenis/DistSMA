package br.unb.sma.agents;

import br.unb.sma.entities.Distribuidor;
import br.unb.sma.utils.Utils;
import jade.core.Agent;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AD extends Agent {

    Distribuidor distribuidor;
    String status;

    public AD(Distribuidor distribuidor) {
        this.distribuidor = distribuidor;
        status = "não iniciado";
        Utils.logInfo(distribuidor.getCodDistribuidor() + ": agente iniciado");
    }

    @Override
    public String toString() {
        return distribuidor.getCodDistribuidor() + " (" + getAgentState().toString() + ")";
    }
}
