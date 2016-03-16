package br.unb.sma.agents;

import br.unb.sma.agents.gui.AGPview;
import br.unb.sma.entities.Distribuidor;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.Agent;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static br.unb.sma.database.Tables.*;

public class AGP extends Agent {

    private JFrame gui;
    private AGP agent = this;
    private AGPview view;

    protected void setup() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    gui = new JFrame("DistSMA: " + getLocalName());
                    view = new AGPview(agent);
                    gui.setContentPane(view.getForm());
                    gui.pack();
                    gui.setVisible(true);
                    gui.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            agent.doDelete();
                        }
                    });
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " : erro ao criar GUI");
                } finally {
                    Utils.logInfo(getLocalName() + " : agente iniciado");
                    loadAPs();
                    loadAMs();
                    loadADs();
                }
            }
        });
    }

    private void loadAMs() {
        try (Connection conn = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            List<Magistrado> magistrados = dsl.select().from(T_MAGISTRADO).orderBy(T_MAGISTRADO.NOM_MAGISTRADO).fetch().into(Magistrado.class);
            for (Magistrado magistrado : magistrados) {
                view.getListAMModel().addElement(new AM(magistrado));
            }
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
        }
    }

    private void loadAPs() {
        try (Connection conn = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            List<Protocolo> protocolos = dsl.select().from(T_PROTOCOLO).fetch().into(Protocolo.class);
            for (Protocolo protocolo : protocolos) {
                view.getListAPModel().addElement(new AP(protocolo));
            }
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
        }
    }

    private void loadADs() {
        try (Connection conn = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            List<Distribuidor> distribuidores = dsl.select().from(T_DISTRIBUIDOR).orderBy(T_DISTRIBUIDOR.NOM_DISTRIBUIDOR).fetch().into(Distribuidor.class);
            for (Distribuidor distribuidor : distribuidores) {
                view.getListADModel().addElement(new AD(distribuidor));
            }
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
        }
    }
}
