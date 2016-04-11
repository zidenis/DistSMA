package br.unb.sma.agents;

import br.unb.sma.behaviors.DFRegistration;
import br.unb.sma.behaviors.ReceiveMessages;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public abstract class SMAgent extends Agent {

    Connection dbConnection;
    DSLContext dbDSL;

    CyclicBehaviour receiveMessages;

    @Override
    protected void setup() {
        loadGUI();
        Utils.logInfo(getLocalName() + " - agente iniciado");
        //Retrieves startup arguments
        //Registering the provided services in the yellow pages catalogue (DF agent)
        addBehaviour(new DFRegistration(this));
        //Starting the initial behaviours
        receiveMessages = new ReceiveMessages();
        addBehaviour(receiveMessages);
    }

    @Override
    public void doActivate() {
        doSuspend();
        if (receiveMessages() != null) {
            ACLMessage msg = (ACLMessage) receiveMessages().getDataStore().get(ReceiveMessages.RCVD_MSG);
            processMessage(msg);
        }
        super.doActivate();
    }

    @Override
    public void doDelete() {
        try {
            dbConnection.close();
        } catch (Exception e) {
            // Do nothing, db connection already closed
        }
        closeGUI();
        Utils.logInfo(getLocalName() + " - agente finalizado");
        super.doDelete();
    }

    @Override
    protected void takeDown() {
        try {
            DFService.deregister(this);
        } catch (FIPAException e) {
            Utils.logError(getLocalName() + " - erro ao desregistrar agente no DF");
        }
    }

    protected void dbConnect() {
        try {
            //Opening connection to the database
            dbConnection = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD);
            dbDSL = DSL.using(dbConnection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            Utils.logError(getLocalName() + " - erro ao conectar com banco de dados");
        }
    }

    public DSLContext getDbDSL() {
        if (dbDSL == null) {
            dbConnect();
        }
        return dbDSL;
    }

    public abstract String getServiceType();

    public abstract String[] getServices();

    protected abstract void processMessage(ACLMessage msg);

    protected abstract void loadGUI();

    protected abstract JFrame getGUI();

    protected void closeGUI() {
        getGUI().dispatchEvent(new WindowEvent(getGUI(), WindowEvent.WINDOW_CLOSING));
    }

    public abstract CyclicBehaviour receiveMessages();

}
