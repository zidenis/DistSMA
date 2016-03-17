package br.unb.sma.agents;

import br.unb.sma.utils.Utils;
import jade.core.Agent;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AP extends Agent {

    @Override
    protected void setup() {
        Utils.logInfo(getLocalName() + " : agente iniciado");
    }

    @Override
    public void doDelete() {
        Utils.logInfo(getLocalName() + " : agente finalizado");
        super.doDelete();
    }

    @Override
    public String toString() {
        return getLocalName() + " (" + getAgentState().toString() + ")";
    }
}
