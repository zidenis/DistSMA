package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AM;
import br.unb.sma.entities.ProcessoCompleto;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

/**
 * Created by zidenis.
 * 12-04-2016
 */
public class CheckAMImpediment extends OneShotBehaviour {

    AD ad;
    ProcessoCompleto pc;
    DFAgentDescription[] magistrateAgents;

    public CheckAMImpediment(AD ad, ProcessoCompleto pc, DFAgentDescription[] magistrateAgents) {
        this.ad = ad;
        this.pc = pc;
        this.magistrateAgents = magistrateAgents;
    }

    @Override
    public void action() {
        if (magistrateAgents.length > 0) {
            try {
                ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
                Envelope envelope = new Envelope();
                envelope.setComments(AM.QUERY_IF_IMPEDIMENT);
                msg.setEnvelope(envelope);
                msg.setContentObject(pc);
                for (DFAgentDescription agentDesc : magistrateAgents) {
                    msg.addReceiver(agentDesc.getName());
                }
                myAgent.send(msg);
            } catch (IOException e) {
                Utils.logError(myAgent.getLocalName() + " - erro ao verificar impedimento para processo");
            }
        } else {
            Utils.logInfo(myAgent.getLocalName() + " - não há MA disponíveis");
        }
    }
}
