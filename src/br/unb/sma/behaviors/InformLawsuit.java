package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AP;
import br.unb.sma.entities.ProcessoCompleto;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

/**
 * Created by zidenis.
 * 23-04-2016
 */
public class InformLawsuit extends OneShotBehaviour {

    AP ap;
    ACLMessage requestLawsuitMsg;

    public InformLawsuit(AP ap, ACLMessage requestLawsuitMsg) {
        super(ap);
        this.ap = ap;
        this.requestLawsuitMsg = requestLawsuitMsg;
    }

    @Override
    public void action() {
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(requestLawsuitMsg.getSender());
        reply.setConversationId(requestLawsuitMsg.getConversationId());
        Envelope envelope = new Envelope();
        if (ap.getLawsuit() != null) {
            ProcessoCompleto pc = new ProcessoCompleto(ap.getLawsuit(), ap);
            envelope.setComments(AD.INFORM_LAWSUIT);
            reply.setEnvelope(envelope);
            try {
                reply.setContentObject(pc);
                ap.send(reply);
            } catch (IOException e) {
                Utils.logError(ap.getLocalName() + " : erro ao gerar processo para distribuição");
            }
        } else {
            envelope.setComments(AD.INFORM_NO_LAWSUIT);
            reply.setEnvelope(envelope);
            ap.send(reply);
        }
    }
}
