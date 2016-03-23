package br.unb.sma.agents;

import br.unb.sma.utils.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AD extends SMAgent {

    CyclicBehaviour receiveMessages;

    @Override
    protected void setup() {
        Utils.logInfo(getLocalName() + " : agente iniciado");
    }

    @Override
    public String toString() {
        return getLocalName() + " (" + getAgentState().toString() + ")";
    }

    @Override
    public String getServiceType() {
        return null;
    }

    @Override
    public String[] getServices() {
        return new String[0];
    }

    @Override
    protected void processMessage(ACLMessage msg) {

    }

    @Override
    public void closeGUI() {
//        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public CyclicBehaviour receiveMessages() {
        return receiveMessages;
    }
}
