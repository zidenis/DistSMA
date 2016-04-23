package br.unb.sma.agents;

import br.unb.sma.agents.gui.AMview;
import br.unb.sma.behaviors.ObtainImpediments;
import br.unb.sma.behaviors.ObtainOJComposition;
import br.unb.sma.entities.*;
import br.unb.sma.utils.Utils;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * AM (Agente Magistrado - Magistrate Agent)
 * @author zidenis
 * @version 2016.4.22
 */
public class AM extends LawDisTrAgent {

    public static final String SERVICE_TYPE = "AM";
    public static final String REQUEST_COMPOSITION = "request-composition";
    public static final String QUERY_IF_IMPEDIMENT = "query-if-impediment";

    private AM am = this;
    private Magistrado magistrate;
    private List<ComposicaoOj> judginOrganList;
    private Set<Long> impedimentsInLawsuits;
    private Set<Long> impedimentsRelatedToParts;
    private Set<Integer> impedimentsRelatedToLawyers;

    @Override
    protected void setup() {
        magistrate = (Magistrado) getArguments()[0];
        setServices(new String[]{AM.REQUEST_COMPOSITION, AM.QUERY_IF_IMPEDIMENT});
        setView(new AMview(am));
        super.setup();
        addBehaviour(new ObtainOJComposition(am));
        addBehaviour(new ObtainImpediments(am));
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    /**
     * Deals with the received messages by AM agents
     *
     * @param msg the message that will be processed
     */
    @Override
    protected void processReceivedMessage(ACLMessage msg) {
        super.processReceivedMessage(msg);
        switch (msg.getEnvelope().getComments()) {
            case REQUEST_COMPOSITION:
                processRequestComposition(msg);
                break;
            case QUERY_IF_IMPEDIMENT:
                processQueryIfImpediment(msg);
                break;
        }
    }

    /**
     * Process the Request Composition message
     * @param msg the request message
     */
    private void processRequestComposition(ACLMessage msg) {
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(msg.getSender());
        Envelope envelope = new Envelope();
        envelope.setComments(AD.INFORM_COMPOSTION);
        reply.setEnvelope(envelope);
        try {
            reply.setContentObject((Serializable) judginOrganList);
            send(reply);
        } catch (IOException e) {
            Utils.logError(getLocalName() + " : erro ao definir composição de magistrado");
        }
    }

    /**
     * Process the Query If Impediment message
     * @param msg the request message
     */
    private void processQueryIfImpediment(ACLMessage msg) {
        try {
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            reply.addReceiver(msg.getSender());
            reply.setConversationId(msg.getConversationId());
            Envelope envelope = new Envelope();
            ProcessoCompleto pc = (ProcessoCompleto) msg.getContentObject();
            boolean impediment = false;
            List<String> impedimentsTypes = new ArrayList<>();
            List<String> impedimentsDetails = new ArrayList<>();
            // MA is impedid to judge the specific lawsuit
            if (impedimentsInLawsuits.contains(pc.getProcesso().getCodProcesso())) {
                impediment = true;
                impedimentsTypes.add("Processo");
                impedimentsDetails.add(pc.getProcesso().toString());
            }
            // MA is impedid to judge the lawsuit because of the persons involved as defendant and victim
            for (Parte parte : pc.getPartes()) {
                if (impedimentsRelatedToParts.contains(parte.getCodParte())) {
                    impediment = true;
                    impedimentsTypes.add("Parte");
                    impedimentsDetails.add(parte.toString());
                }
            }
            // MA is impedid to judge the lawsuit because of the lawyers involved
            for (Advogado adv : pc.getAdvogados()) {
                if (impedimentsRelatedToLawyers.contains(adv.getNumAdvogado())) {
                    impediment = true;
                    impedimentsTypes.add("Advogado");
                    impedimentsDetails.add(adv.toString());
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
            send(reply);
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao processar impedimentos em processo");
        }
    }

    /**
     * Defines the OJs (Orgãos Judicantes - Judging Organs) that the magistrate is member of.
     *
     * @param judginOrganList the list of Judging Organs
     */
    public void setJudginOrganList(List<ComposicaoOj> judginOrganList) {
        this.judginOrganList = judginOrganList;
        if (isGUIEnabled) {
            SwingUtilities.invokeLater(() -> {
                ((AMview) view).setComposicaoOJ(Utils.join(judginOrganList.listIterator(), "\n"));
                gui.pack();
            });
        }
    }

    /**
     * Defines the list os lawsuits that the magistrate is impedid to judge
     *
     * @param impedimentsInLawsuits the list of lawsuit's ids
     */
    public void setImpedimentsInLawsuits(Set<Long> impedimentsInLawsuits) {
        this.impedimentsInLawsuits = impedimentsInLawsuits;
        if (isGUIEnabled) {
            SwingUtilities.invokeLater(() -> {
                ((AMview) view).setQtdProcImped(String.valueOf(impedimentsInLawsuits.size()));
                gui.pack();
            });
        }
    }

    /**
     * Defines the list of person ids that the magistrate is impedid to judge related lawsuits
     *
     * @param impedimentsRelatedToParts the list of person ids
     */
    public void setImpedimentsRelatedToParts(Set<Long> impedimentsRelatedToParts) {
        this.impedimentsRelatedToParts = impedimentsRelatedToParts;
        if (isGUIEnabled) {
            SwingUtilities.invokeLater(() -> {
                ((AMview) view).setQtdParteImped(String.valueOf(impedimentsRelatedToParts.size()));
                gui.pack();
            });
        }
    }

    /**
     * Defines the list of lawyers ids that the magistrate is impedido to judge related lawsuits
     *
     * @param impedimentsRelatedToLawyers the list of lawyers lawsuits
     */
    public void setImpedimentsRelatedToLawyers(Set<Integer> impedimentsRelatedToLawyers) {
        this.impedimentsRelatedToLawyers = impedimentsRelatedToLawyers;
        if (isGUIEnabled) {
            SwingUtilities.invokeLater(() -> {
                ((AMview) view).setQtdAdvImped(String.valueOf(impedimentsRelatedToLawyers.size()));
                gui.pack();
            });
        }
    }

    /**
     * Gets the Magistrate represented by the AM
     *
     * @return the magitrate entity
     */
    public Magistrado getMagistrate() {
        return magistrate;
    }
}
