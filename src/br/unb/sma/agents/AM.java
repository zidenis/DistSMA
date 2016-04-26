package br.unb.sma.agents;

import br.unb.sma.agents.gui.AMview;
import br.unb.sma.behaviors.InformComposition;
import br.unb.sma.behaviors.InformImpedimentOrCompetence;
import br.unb.sma.behaviors.ObtainImpediments;
import br.unb.sma.behaviors.ObtainOJComposition;
import br.unb.sma.entities.ComposicaoOj;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.utils.Utils;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
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
        addBehaviour(new InformComposition(this, msg));
    }

    /**
     * Process the Query If Impediment message
     * @param msg the request message
     */
    private void processQueryIfImpediment(ACLMessage msg) {
        addBehaviour(new InformImpedimentOrCompetence(this, msg), msg.getConversationId());

    }

    public List<ComposicaoOj> getJudginOrganList() {
        return judginOrganList;
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

    public Set<Long> getImpedimentsInLawsuits() {
        return impedimentsInLawsuits;
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

    public Set<Long> getImpedimentsRelatedToParts() {
        return impedimentsRelatedToParts;
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

    public Set<Integer> getImpedimentsRelatedToLawyers() {
        return impedimentsRelatedToLawyers;
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

    @Override
    public String getAgentName() {
        return magistrate.getCodMagistrado();
    }
}
