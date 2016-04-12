package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 12-04-2016
 */
public class InformPlataformChange extends OneShotBehaviour {

    @Override
    public void action() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(AD.SERVICE_TYPE);
        dfd.addServices(sd);
        try {
            DFAgentDescription[] agentsDescriptions = DFService.search(myAgent, dfd);
            if (agentsDescriptions.length > 0) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                Envelope envelope = new Envelope();
                envelope.setComments(AD.INFORM_PLATAFORM_CHANGE);
                msg.setEnvelope(envelope);
                for (DFAgentDescription agentDesc : agentsDescriptions) {
                    msg.addReceiver(agentDesc.getName());
                }
                myAgent.send(msg);
            }
        } catch (FIPAException e) {
            Utils.logError(myAgent.getLocalName() + " - erro ao notificar agentes de districuição");
        }
    }
}
