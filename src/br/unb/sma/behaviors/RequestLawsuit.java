package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AP;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 11-04-2016
 */
public class RequestLawsuit extends OneShotBehaviour {

    AD ad;
    DFAgentDescription[] protocolAgents;

    public RequestLawsuit(AD ad, DFAgentDescription[] protocolAgents) {
        this.ad = ad;
        this.protocolAgents = protocolAgents;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        Envelope envelope = new Envelope();
        envelope.setComments(AP.REQUEST_LAWSUIT);
        msg.setEnvelope(envelope);
        for (DFAgentDescription dfd : protocolAgents) {
            msg.addReceiver(dfd.getName());
        }
        ad.send(msg);
    }
}
