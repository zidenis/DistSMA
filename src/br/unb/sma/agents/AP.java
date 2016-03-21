package br.unb.sma.agents;

import br.unb.sma.agents.gui.APview;
import br.unb.sma.behaviors.DFRegistration;
import br.unb.sma.behaviors.ObtainLawsuitAwaintingDistribution;
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

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_PROCESSO;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AP extends Agent implements IAgent {

    public static final String GET_LAWSUIT = "get-lawsuit";
    public static final String UPDATE_LAWSUIT_DB = "update-lawsuit-db";

    private final String SERVICE_TYPE = "AP";
    private final String[] SERVICES = {AP.GET_LAWSUIT, UPDATE_LAWSUIT_DB};

    Connection dbConnection;
    DSLContext dbDSL;
    Protocolo protocolo;
    Processo processo;
    Integer qtdProcessos;
    CyclicBehaviour receiveMessages;

    private JFrame gui;
    private AP agent = this;
    private APview view;

    @Override
    protected void setup() {
        protocolo = (Protocolo) getArguments()[0];
        dbConnect();
        loadGUI();
        Utils.logInfo(getLocalName() + " : agente iniciado");
        //Retrieves startup arguments
        //Registering the provided services in the yellow pages catalogue (DF agent)
        addBehaviour(new DFRegistration(agent, agent));
        //Starting the initial behaviours
        receiveMessages = new ReceiveMessages();
        addBehaviour(receiveMessages);
        addBehaviour(new ObtainLawsuitAwaintingDistribution(agent, protocolo.getNumTribunal()));
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
        return protocolo.getNomProtocolo() + " (" + getAgentState().toString() + ")";
    }

    public DSLContext getDbDSL() {
        return dbDSL;
    }

    public void setProcesso(Processo processo) {
        if (processo != null) {
            this.processo = processo;
            SwingUtilities.invokeLater(() -> {
                if (gui.isDisplayable()) {
                    view.setProcesso(processo.toString());
                }
            });
        } else {
            SwingUtilities.invokeLater(() -> {
                if (gui.isDisplayable()) {
                    view.setProcesso("sem processos");
                }
            });
        }
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
        if (msg.getContent().equals(GET_LAWSUIT)) {

        }
    }

    private void loadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Loading the GUI
                    gui = new JFrame("AP : " + protocolo.getNomProtocolo());
                    view = new APview(agent);
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
                    view.setQtdProcessosFila(getQtdProcessos());
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " : erro ao criar GUI");
                    e.printStackTrace();
                }
            }

        });
    }

    private void dbConnect() {
        try {
            //Opening connection to the database
            dbConnection = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD);
            dbDSL = DSL.using(dbConnection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
        }
    }

    private Integer getQtdProcessos() {
        if (qtdProcessos == null) {
            return getDbDSL()
                    .selectCount()
                    .from(T_PROCESSO)
                    .innerJoin(T_FASE_PROCESSUAL)
                    .on(T_PROCESSO.COD_PROCESSO.equal(T_FASE_PROCESSUAL.COD_PROCESSO))
                    .where(T_PROCESSO.NUM_TRIBUNAL.equal(protocolo.getNumTribunal()))
                    .and(T_FASE_PROCESSUAL.COD_MAGISTRADO.isNull())
                    .fetchOne()
                    .value1();
        } else {
            return qtdProcessos;
        }
    }
}
