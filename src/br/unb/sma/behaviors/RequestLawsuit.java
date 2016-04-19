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
    DFAgentDescription protocolAgent;
    String idDistribuicao;

    public RequestLawsuit(AD ad, DFAgentDescription protocolAgent, String idDistribuicao) {
        this.ad = ad;
        this.protocolAgent = protocolAgent;
        this.idDistribuicao = idDistribuicao;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        Envelope envelope = new Envelope();
        envelope.setComments(AP.REQUEST_LAWSUIT);
        msg.setConversationId(idDistribuicao.toString());
        msg.setEnvelope(envelope);
        msg.addReceiver(protocolAgent.getName());
        ad.send(msg);
    }
}
