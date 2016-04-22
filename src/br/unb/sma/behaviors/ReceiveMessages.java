package br.unb.sma.behaviors;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public class ReceiveMessages extends CyclicBehaviour {

    public static final String REVEIVED_MESSAGE = "received-message";

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive();
        if (msg != null) {
            //Utils.logInfo(myAgent.getLocalName() + " : mensagem recebida de " + msg.getSender().getLocalName());
            getDataStore().put(REVEIVED_MESSAGE, msg);
            myAgent.doActivate();
        } else {
            // used to make the CyclicBehaviour be executed only when a new message is received
            block();
        }
    }
}
