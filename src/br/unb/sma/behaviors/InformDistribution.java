package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AP;
import br.unb.sma.entities.HistDistribuicao;
import br.unb.sma.utils.Utils;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

/**
 * Created by zidenis.
 * 14-04-2016
 */
public class InformDistribution extends OneShotBehaviour {

    HistDistribuicao distribuicao;
    AD agent;
    AID receiver;
    String idDistribuicao;

    public InformDistribution(AD agent, HistDistribuicao distribuicao, AID receiver, String idDistribuicao) {
        this.agent = agent;
        this.distribuicao = distribuicao;
        this.receiver = receiver;
        this.idDistribuicao = idDistribuicao;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        Envelope envelope = new Envelope();
        envelope.setComments(AP.INFORM_DISTRIBUTION);
        msg.setEnvelope(envelope);
        msg.setConversationId(idDistribuicao);
        msg.addReceiver(receiver);
        try {
            msg.setContentObject(distribuicao);
            myAgent.send(msg);
        } catch (IOException e) {
            Utils.logError(myAgent.getLocalName() + " : erro ao informar distribuição");
        }
    }
}
