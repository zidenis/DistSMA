package br.unb.sma.behaviors;

import br.unb.sma.agents.LawDisTrAgent;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class Register extends OneShotBehaviour {

    LawDisTrAgent agent;
    String serviceType;
    String[] services;

    public Register(LawDisTrAgent agent, String serviceType, String[] services) {
        super(agent);
        this.agent = agent;
        this.serviceType = serviceType;
        this.services = services;
    }

    @Override
    public void action() {
        try {
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(agent.getAID());
            for (String service : services) {
                ServiceDescription sd = new ServiceDescription();
                sd.setType(serviceType);
                sd.setName(service);
                dfd.addServices(sd);
            }
            DFService.register(agent, dfd);
        } catch (FIPAException fe) {
            Utils.logError(agent.getLocalName() + " - erro ao registrar serviço no DF");
        }
    }
}
