package br.unb.sma.agents;

import br.unb.sma.agents.gui.AgentView;
import br.unb.sma.behaviors.DFRegistration;
import br.unb.sma.behaviors.ReceiveMessages;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * An agent of LawDisTrA system (Lawsuit Distribution with Transpent Agents)
 * @author zidenis
 * @version 2016.4.19
 */
public abstract class LawDisTrAgent extends Agent {

    protected boolean isGUIEnabled;
    protected AgentView view;
    protected JFrame gui;
    protected CyclicBehaviour receiveMessages;
    private String[] services = {""};
    private Connection dbConnection;
    private DSLContext dbDSL;

    @Override
    protected void setup() {
        isGUIEnabled = AGP.ENABLE_GUI;
        if (isGUIEnabled) loadGUI();
        Utils.logInfo(getLocalName() + " - agente iniciado");
        //Registration of the agent in JADE Directory Facilitator (DF)
        addBehaviour(new DFRegistration(this, getServiceType(), getServices()));
        //Activating the behaviour that enables messages reception
        receiveMessages = new ReceiveMessages();
        addBehaviour(receiveMessages);
    }

    @Override
    public void doActivate() {
        doSuspend();
        // The agent will process the received messages when active
        if (receiveMessages != null) {
            ACLMessage msg = (ACLMessage) receiveMessages.getDataStore().get(ReceiveMessages.REVEIVED_MESSAGE);
            processReceivedMessage(msg);
        }
        super.doActivate();
    }

    @Override
    protected void takeDown() {
        if (isGUIEnabled) closeGUI();
        try {
            dbConnection.close();
            DFService.deregister(this);
        } catch (Exception e) {
            Utils.logError(getLocalName() + " - erro ao desabilitar agente");
        }
        Utils.logInfo(getLocalName() + " - agente finalizado");
    }

    /**
     * Loads the agent's GUI.
     */
    protected void loadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    gui = new JFrame(getServiceType() + " : " + getLocalName());
                    view = getView();
                    gui.setContentPane(view.getForm());
                    gui.pack();
                    gui.setLocation(Utils.guiLocation(getServiceType()));
                    gui.setVisible(true);
                    gui.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                        }
                    });
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " - erro ao criar GUI");
                }
            }
        });
    }

    /**
     * Gets the reference to the agent's view
     * @return
     */
    public AgentView getView() {
        return view;
    }

    /**
     * Defines the agent view
     *
     * @param view the agent view
     */
    public void setView(AgentView view) {
        this.view = view;
    }

    /**
     * Closes the agent's GUI
     */
    private void closeGUI() {
        if (isGUIEnabled) gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
    }

    /**
     * Gets the JOOq's DSL for execute queries to the connected database
     *
     * @return the contextual DSL entry point
     */
    public DSLContext getDbDSL() {
        if (dbDSL == null) dbConnect();
        return dbDSL;
    }

    /**
     * Connects the database and configures the JOOq's DSL
     */
    private void dbConnect() {
        try {
            //Opening connection to the database
            dbConnection = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD);
            dbDSL = DSL.using(dbConnection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            Utils.logError(getLocalName() + " - erro ao conectar com banco de dados");
        }
    }

    /**
     * Gets the service type description implemented by the agent
     */
    protected abstract String getServiceType();

    /**
     * Gets the services descriptions implemented by the agent
     */
    public String[] getServices() {
        return services;
    }

    /**
     * Defines the services implemented by the agent
     *
     * @param services an array with the names of the services
     */
    public void setServices(String[] services) {
        this.services = services;
    }

    /**
     * Process the messages received by the agent
     * Common tasks for all LawDisTrA agents when processing received messages
     * @param msg the message that will be processed
     */
    protected void processReceivedMessage(ACLMessage msg) {
        String id = (msg.getConversationId() == null) ? "" : "(dist. id: " + msg.getConversationId() + ") ";
        Utils.logInfo(getLocalName() + " - mensagem " + id + "recebida de " + msg.getSender().getLocalName() + " : " + msg.getEnvelope().getComments());
    }

    /**
     * Common tasks for all LawDisTrA agents when adding new behaviours
     * @param behaviour the behaviour that will be added
     */
    @Override
    public void addBehaviour(Behaviour behaviour) {
        String behaviourName = behaviour.getClass().getSimpleName();
        if (!behaviourName.equals("")) {
            Utils.logInfo(getLocalName() + " - tarefa iniciada : " + behaviour.getClass().getSimpleName());
        }
        super.addBehaviour(behaviour);
    }

    /**
     * Used when adding behaviours related to distributions
     * For the purpose of tracking the distribution ID
     *
     * @param behaviour      the behaviour that will be added
     * @param distributionID the distribution id
     */
    public void addBehaviour(Behaviour behaviour, String distributionID) {
        Utils.logInfo(getLocalName() + " - tarefa (dist. id: " + distributionID + ") : " + behaviour.getClass().getSimpleName());
        super.addBehaviour(behaviour);
    }


}
