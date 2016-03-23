package br.unb.sma.agents;

import br.unb.sma.behaviors.ReceiveMessages;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

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
            Utils.logError(getLocalName() + " : erro ao encerrar conexão com banco de dados");
        }
        closeGUI();
        Utils.logInfo(getLocalName() + " : agente finalizado");
        super.doDelete();
    }

    protected void dbConnect() {
        try {
            //Opening connection to the database
            dbConnection = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD);
            dbDSL = DSL.using(dbConnection, SQLDialect.POSTGRES);
        } catch (SQLException e) {
            Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
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

    public abstract void closeGUI();

    public abstract CyclicBehaviour receiveMessages();

}
