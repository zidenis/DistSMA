package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AM;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 11-04-2016
 */
public class RequestComposition extends OneShotBehaviour {

    AD ad;
    DFAgentDescription[] magistrateAgents;
    String idDistribuicao;

    public RequestComposition(AD ad, DFAgentDescription[] magistrateAgents, String idDistribuicao) {
        this.ad = ad;
        this.magistrateAgents = magistrateAgents;
        this.idDistribuicao = idDistribuicao;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        Envelope envelope = new Envelope();
        envelope.setComments(AM.REQUEST_COMPOSITION);
        msg.setEnvelope(envelope);
        for (DFAgentDescription dfd : magistrateAgents) {
            msg.addReceiver(dfd.getName());
        }
        ad.send(msg);
    }
}
