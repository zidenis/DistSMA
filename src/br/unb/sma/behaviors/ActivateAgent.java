package br.unb.sma.behaviors;

import br.unb.sma.agents.AGP;
import br.unb.sma.utils.AgentEntity;
import br.unb.sma.utils.Status;
import br.unb.sma.utils.Utils;
import jade.content.onto.basic.Action;
import jade.core.Agent;
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
public class ActivateAgent extends OneShotBehaviour {

    AgentEntity agentEntity;

    public ActivateAgent(Agent agent, AgentEntity agentEntity) {
        super(agent);
        this.agentEntity = agentEntity;
    }

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " - tarefa iniciada : ActivateAgent");
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

            myAgent.addBehaviour(new AchieveREInitiator(myAgent, request) {

                @Override
                protected void handleInform(ACLMessage inform) {
                    agentEntity.setStatus(Status.ATIVADO);
                    SwingUtilities.invokeLater(() -> ((AGP) myAgent).getView().update());
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
