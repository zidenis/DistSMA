package br.unb.sma.agents;

import br.unb.sma.agents.gui.ADview;
import br.unb.sma.behaviors.DiscoverMagistrateAgents;
import br.unb.sma.behaviors.DiscoverProtocolAgents;
import br.unb.sma.behaviors.ObtainOJCompetencies;
import br.unb.sma.behaviors.RequestOJComposition;
import br.unb.sma.entities.ComposicaoOj;
import br.unb.sma.utils.Utils;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AD extends SMAgent {

    public static final String DISTRIBUTE = "distribute";
    public static final String SERVICE_TYPE = "AD";
    public static final String INFORM_COMPOSTION = "inform-composition";

    private final String[] SERVICES = {DISTRIBUTE};

    private JFrame gui;
    private AD agent = this;
    private ADview view;
    private Map<String, List<String>> competencias; // "Classe do Processo" -> ["Órgãos Judicantes"]
    private DFAgentDescription[] protocolAgents;
    private DFAgentDescription[] magistrateAgents;
    private Map<String, Set<AID>> composicao; // "Órgão Judicante" -> [Agentes Magistrados]

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new ObtainOJCompetencies(this));
        findAgents();
    }

    @Override
    public String toString() {
        return getLocalName() + " (" + getAgentState().toString() + ")";
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    @Override
    public String[] getServices() {
        return SERVICES;
    }

    @Override
    protected void processMessages(ACLMessage msg) {
        super.processMessages(msg);
        switch (msg.getEnvelope().getComments()) {
            case INFORM_COMPOSTION:
                processCompositionInfo(msg);
                break;
        }
    }

    private void processCompositionInfo(ACLMessage msg) {
        try {
            List<ComposicaoOj> composicaoOjList = (List) msg.getContentObject();
            for (ComposicaoOj coj : composicaoOjList) {
                String sigOj = coj.getSigOj();
                if (composicao != null && composicao.containsKey(sigOj)) {
                    composicao.get(sigOj).add(msg.getSender());
                } else {
                    if (composicao == null) {
                        composicao = new HashMap<>();
                    }
                    Set<AID> ams = new HashSet<>();
                    ams.add(msg.getSender());
                    composicao.put(sigOj, ams);
                }
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao identificar OJs de " + msg.getSender().getLocalName());
        }
    }

    protected void loadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Loading the GUI
                    gui = new JFrame("AD : " + getLocalName());
                    view = new ADview(agent);
                    gui.setContentPane(view.getForm());
                    gui.pack();
                    gui.setLocation(Utils.guiLocation());
                    gui.setVisible(true);
                    gui.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                        }
                    });
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " : erro ao criar GUI");
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected JFrame getGUI() {
        return gui;
    }

    @Override
    public CyclicBehaviour receiveMessages() {
        return receiveMessages;
    }

    public void setCompetencias(HashMap<String, List<String>> competencias) {
        this.competencias = competencias;
//        Utils.logInfo(competencias.toString());
    }

    public DFAgentDescription[] getProtocolAgents() {
        return protocolAgents;
    }

    public void setProtocolAgents(DFAgentDescription[] protocolAgents) {
        this.protocolAgents = protocolAgents;
//        for (DFAgentDescription dfd : protocolAgents) {
//            Utils.logInfo(dfd.getName().toString());
//        }
    }

    public DFAgentDescription[] getMagistrateAgents() {
        return magistrateAgents;
    }

    public void setMagistrateAgents(DFAgentDescription[] magistrateAgents) {
        this.magistrateAgents = magistrateAgents;
//        for (DFAgentDescription dfd : magistrateAgents) {
//            Utils.logInfo(dfd.getName().toString());
//        }
        addBehaviour(new RequestOJComposition(this, magistrateAgents));
    }

    public void findAgents() {
        if (composicao != null) {
            composicao.clear();
        }
        addBehaviour(new DiscoverMagistrateAgents(this));
        addBehaviour(new DiscoverProtocolAgents(this));
    }

}
