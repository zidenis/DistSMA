package br.unb.sma.agents;

import br.unb.sma.agents.gui.APview;
import br.unb.sma.behaviors.DFRegistration;
import br.unb.sma.behaviors.ObtainLawsuitsAwaintingDistribution;
import br.unb.sma.behaviors.ReceiveMessages;
import br.unb.sma.entities.Processo;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AP extends Agent implements IAgent {

    public static final String MSG_GET_PROCESSES = "get-processes";
    public static final String MSG_UPDATE_DATABASE = "update-database";

    private final String SERVICE_TYPE = "protocolo";
    private final String[] SERVICES = {AP.MSG_GET_PROCESSES, MSG_UPDATE_DATABASE};

    Connection dbConnection;
    DSLContext dbDSL;
    Protocolo protocolo;
    List<Processo> processos;
    CyclicBehaviour receiveMessages;
    private JFrame gui;
    private AP agent = this;
    private APview view;

    @Override
    protected void setup() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Loading the GUI
                    gui = new JFrame(getLocalName());
                    view = new APview(agent);
                    gui.setContentPane(view.getForm());
                    gui.pack();
                    gui.setLocation(Utils.guiLocation());
                    gui.setVisible(true);
                    gui.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            //agent.doDelete();
                        }
                    });
                    //Retrieves startup arguments
                    protocolo = (Protocolo) getArguments()[0];
                    //Opening connection to the database
                    dbConnection = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD);
                    dbDSL = DSL.using(dbConnection, SQLDialect.POSTGRES);
                    processos = new ArrayList<>();
                    //Registering the provided services in the yellow pages catalogue (DF agent)

                    //Starting the initial behaviours
                    receiveMessages = new ReceiveMessages();
                    addBehaviour(receiveMessages);
                    addBehaviour(new DFRegistration(agent, agent));
                    Utils.logInfo(getLocalName() + " : agente iniciado");
                } catch (SQLException e) {
                    Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " : erro ao criar GUI");
                }
            }
        });
    }

    @Override
    public void doDelete() {
        // Closing the database connection
        try {
            dbConnection.close();
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao encerrar conexão com banco de dados");
        }
        //Closing the GUI
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
        Utils.logInfo(getLocalName() + " : agente finalizado");
        super.doDelete();
    }

    private void dfServiceRegistration() {

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
    public String toString() {
        return getLocalName() + " (" + getAgentState().toString() + ")";
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public DSLContext getDbDSL() {
        return dbDSL;
    }

    public List<Processo> getProcessos() {
        return processos;
    }

    public void setProcessos(List<Processo> processos) {
        this.processos = processos;
        SwingUtilities.invokeLater(() -> {
            if (gui.isDisplayable()) {
                view.getListProcessosModel().clear();
                processos.forEach(processo -> view.getListProcessosModel().addElement(processo));
            }
        });
    }

    @Override
    public void doActivate() {
        doSuspend();
        if (receiveMessages != null) {
            ACLMessage msg = (ACLMessage) receiveMessages.getDataStore().get(ReceiveMessages.RCVD_MSG);
            processMessage(msg);
        }
        super.doActivate();
    }

    private void processMessage(ACLMessage msg) {
        if (msg.getContent().equals(MSG_GET_PROCESSES)) {
            int numProcesses = Integer.valueOf(msg.getUserDefinedParameter("numProcessos"));
            addBehaviour(new ObtainLawsuitsAwaintingDistribution(agent, protocolo.getNumTribunal(), numProcesses));
        }
    }
}
