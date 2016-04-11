package br.unb.sma.agents;

import br.unb.sma.agents.gui.ADview;
import br.unb.sma.behaviors.ObtainOJCompetencies;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AD extends SMAgent {

    public static final String DISTRIBUTE = "distribute";

    private final String SERVICE_TYPE = "AD";
    private final String[] SERVICES = {DISTRIBUTE};

    private JFrame gui;
    private AD agent = this;
    private ADview view;
    private HashMap<String, List<String>> competencias;

    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new ObtainOJCompetencies(this));
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
    protected void processMessage(ACLMessage msg) {
        Utils.logInfo(getLocalName() + " : mensagem recebida de " + msg.getSender().getLocalName());
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
        //Utils.logInfo(competencias.toString());
    }
}
