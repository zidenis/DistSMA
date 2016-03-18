package br.unb.sma.agents;

import br.unb.sma.agents.gui.APview;
import br.unb.sma.behaviors.ObtainLawsuitsAwaintingDistribution;
import br.unb.sma.entities.Processo;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
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
public class AP extends Agent {

    Connection dbConnection;
    DSLContext dbDSL;
    Protocolo protocolo;
    List<Processo> processos;
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
                } catch (SQLException e) {
                    Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " : erro ao criar GUI");
                } finally {
                    processos = new ArrayList<Processo>();
                    //Registering the provided services in the yellow pages catalogue (DF agent)

                    //Starting the initial behaviours
                    addBehaviour(new ObtainLawsuitsAwaintingDistribution(agent, protocolo.getNumTribunal(), 100));
                    Utils.logInfo(getLocalName() + " : agente iniciado");
                }
            }
        });
    }

    @Override
    public void doDelete() {
        super.doDelete();
        // Closing the database connection

        //Closing the GUI
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
        Utils.logInfo(getLocalName() + " : agente finalizado");
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (gui.isDisplayable()) {
                    view.getListProcessosModel().clear();
                    processos.forEach(processo -> view.getListProcessosModel().addElement(processo));
                }
            }
        });
    }
}
