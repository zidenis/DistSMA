package br.unb.sma.agents;

import br.unb.sma.entities.Magistrado;
import br.unb.sma.utils.Utils;
import jade.core.Agent;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AM extends Agent {

    Magistrado magistrado;
    String status;

    public AM(Magistrado magistrado) {
        this.magistrado = magistrado;
        status = "não iniciado";
        Utils.logInfo(magistrado.getCodMagistrado() + ": agente iniciado");
    }

    @Override
    public String toString() {
        return magistrado.getCodMagistrado() + " (" + getAgentState().toString() + ")";
    }
}
