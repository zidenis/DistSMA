package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AM;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 11-04-2016
 */
public class RequestOJComposition extends OneShotBehaviour {

    AD ad;
    DFAgentDescription[] magistrateAgents;

    public RequestOJComposition(AD ad, DFAgentDescription[] magistrateAgents) {
        this.ad = ad;
        this.magistrateAgents = magistrateAgents;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.setContent(AM.GET_COMPOSITION);
        for (DFAgentDescription dfd : magistrateAgents) {
            msg.addReceiver(dfd.getName());
        }
        ad.send(msg);
    }
}
