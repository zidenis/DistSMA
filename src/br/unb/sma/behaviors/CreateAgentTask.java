package br.unb.sma.behaviors;

import br.unb.sma.agents.AGP;
import br.unb.sma.agents.gui.AGPview;
import br.unb.sma.entities.AgentEntity;
import br.unb.sma.utils.Utils;
import jade.content.onto.basic.Action;
import jade.core.ContainerID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;

import javax.swing.*;

/**
 * Created by zidenis.
 * 21-03-2016
 */
public class CreateAgentTask extends OneShotBehaviour {

    AgentEntity agentEntity;
    AGP agp;

    public CreateAgentTask(AGP agp, AgentEntity agentEntity) {
        super(agp);
        this.agp = agp;
        this.agentEntity = agentEntity;
    }

    @Override
    public void action() {
        try {
            CreateAgent ca = new CreateAgent();
            ca.setAgentName(agentEntity.getAgentName());
            ca.setClassName(agentEntity.getClassName());
            ca.addArguments(agentEntity);
            ca.setContainer(new ContainerID(myAgent.getContainerController().getContainerName(), null));
            Action actExpr = new Action(myAgent.getAMS(), ca);
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(myAgent.getAMS());
            request.setOntology(JADEManagementOntology.getInstance().getName());
            request.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
            request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            myAgent.getContentManager().fillContent(request, actExpr);
            agp.addBehaviour(new AchieveREInitiator(myAgent, request) {
                @Override
                protected void handleInform(ACLMessage inform) {
                    agentEntity.setStatus(AGP.STATUS_ENABLED);
                    SwingUtilities.invokeLater(() -> ((AGPview) agp.getView()).update());
                    myAgent.addBehaviour(new InformPlataformChange());
                }

                @Override
                protected void handleFailure(ACLMessage failure) {
                    Utils.logError(myAgent.getLocalName() + " - erro ao iniciar o " + agentEntity.getAgentName());
                    Utils.logError(failure.getContent());
                }
            });
        } catch (Exception e) {
            Utils.logError(myAgent.getLocalName() + " - erro ao iniciar " + agentEntity.getAgentName());
            e.printStackTrace();
        }
    }
}
