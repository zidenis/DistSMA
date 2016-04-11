package br.unb.sma.behaviors;

import br.unb.sma.utils.Utils;
import jade.content.onto.basic.Action;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.ShutdownPlatform;
import jade.lang.acl.ACLMessage;

/**
 * Created by zidenis.
 * 21-03-2016
 */
public class ShutdownSMA extends OneShotBehaviour {

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " - tarefa iniciada : ShutdownSMA");
        ShutdownPlatform sd = new ShutdownPlatform();
        try {
            Action actExpr = new Action(myAgent.getAMS(), sd);
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(myAgent.getAMS());
            request.setOntology(JADEManagementOntology.getInstance().getName());
            request.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
            request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            myAgent.getContentManager().fillContent(request, actExpr);
            myAgent.send(request);
        } catch (Exception e) {
            Utils.logError(myAgent.getLocalName() + " - erro ao encerrar o SMA");
            e.printStackTrace();
        }
    }
}
