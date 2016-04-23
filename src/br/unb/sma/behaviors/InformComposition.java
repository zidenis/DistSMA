package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AM;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zidenis.
 * 23-04-2016
 */
public class InformComposition extends OneShotBehaviour {

    AM am;
    ACLMessage msg;

    public InformComposition(AM am, ACLMessage msg) {
        super(am);
        this.am = am;
        this.msg = msg;
    }

    @Override
    public void action() {
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(msg.getSender());
        Envelope envelope = new Envelope();
        envelope.setComments(AD.INFORM_COMPOSTION);
        reply.setEnvelope(envelope);
        try {
            reply.setContentObject((Serializable) am.getJudginOrganList());
            am.send(reply);
        } catch (IOException e) {
            Utils.logError(am.getLocalName() + " : erro ao definir composição de magistrado");
        }
    }
}
