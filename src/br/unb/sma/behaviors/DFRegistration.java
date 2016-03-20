package br.unb.sma.behaviors;

import br.unb.sma.agents.IAgent;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class DFRegistration extends OneShotBehaviour {

    IAgent agent;

    public DFRegistration(Agent a, IAgent agent) {
        super(a);
        this.agent = agent;
    }

    @Override
    public void action() {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(myAgent.getAID());
        for (String service : agent.getServices()) {
            ServiceDescription sd = new ServiceDescription();
            sd.setType(agent.getServiceType());
            sd.setName(myAgent.getLocalName() + "-" + service);
            dfd.addServices(sd);
        }
        try {
            Utils.logInfo(myAgent.getLocalName() + " : tarefa iniciada : DFRegistration");
            DFService.register(myAgent, dfd);
        } catch (FIPAException fe) {
            Utils.logError(myAgent.getLocalName() + " : erro ao registrar serviço no DF");
        }
    }
}
