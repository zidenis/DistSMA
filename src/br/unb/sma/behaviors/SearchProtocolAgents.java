package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AP;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 * Created by zidenis.
 * 11-04-2016
 */
public class SearchProtocolAgents extends OneShotBehaviour {

    private AD ad;

    public SearchProtocolAgents(AD agent) {
        super(agent);
        this.ad = agent;
    }

    @Override
    public void action() {
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(AP.SERVICE_TYPE);
        dfd.addServices(sd);
        try {
            ad.setProtocolAgents(DFService.search(ad, dfd));
        } catch (FIPAException e) {
            Utils.logError(myAgent.getLocalName() + " - erro ao procurar agentes de protocolo");
        }
    }
}
