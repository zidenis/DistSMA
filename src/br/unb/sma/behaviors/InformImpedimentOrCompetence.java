package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AM;
import br.unb.sma.entities.Advogado;
import br.unb.sma.entities.Parte;
import br.unb.sma.entities.ProcessoCompleto;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zidenis.
 * 23-04-2016
 */
public class InformImpedimentOrCompetence extends OneShotBehaviour {

    AM am;
    ACLMessage msg;

    public InformImpedimentOrCompetence(AM am, ACLMessage msg) {
        super(am);
        this.am = am;
        this.msg = msg;
    }

    @Override
    public void action() {
        try {
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            reply.addReceiver(msg.getSender());
            reply.setConversationId(msg.getConversationId());
            Envelope envelope = new Envelope();
            ProcessoCompleto pc = (ProcessoCompleto) msg.getContentObject();
            boolean impediment = false;
            List<String> impedimentsTypes = new ArrayList<>();
            List<Object> impedimentsDetails = new ArrayList<>();
            // MA is impedid to judge the specific lawsuit
            if (am.getImpedimentsInLawsuits().contains(pc.getProcesso().getCodProcesso())) {
                impediment = true;
                impedimentsTypes.add("Processo");
                impedimentsDetails.add(pc.getProcesso());
            }
            // MA is impedid to judge the lawsuit because of the persons involved as defendant and victim
            for (Parte parte : pc.getPartes()) {
                if (am.getImpedimentsRelatedToParts().contains(parte.getCodParte())) {
                    impediment = true;
                    impedimentsTypes.add("Parte");
                    impedimentsDetails.add(parte);
                }
            }
            // MA is impedid to judge the lawsuit because of the lawyers involved
            for (Advogado adv : pc.getAdvogados()) {
                if (am.getImpedimentsRelatedToLawyers().contains(adv.getNumAdvogado())) {
                    impediment = true;
                    impedimentsTypes.add("Advogado");
                    impedimentsDetails.add(adv);
                }
            }
            if (impediment) {
                envelope.setComments(AD.INFORM_IMPEDIMENT);
                reply.addUserDefinedParameter("tipo", impedimentsTypes.toString());
                reply.addUserDefinedParameter("detalhamento", impedimentsDetails.toString());
            } else {
                envelope.setComments(AD.INFORM_COMPETENCE);
            }
            reply.setEnvelope(envelope);
            reply.setContentObject(pc);
            am.send(reply);
        } catch (Exception e) {
            Utils.logError(am.getLocalName() + " : erro ao processar impedimentos em processo");
        }

    }
}
