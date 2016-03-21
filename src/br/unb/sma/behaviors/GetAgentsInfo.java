package br.unb.sma.behaviors;

import br.unb.sma.agents.AGP;
import br.unb.sma.entities.Distribuidor;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;

import static br.unb.sma.database.Tables.*;

/**
 * Created by zidenis.
 * 21-03-2016
 */
public class GetAgentsInfo extends OneShotBehaviour {

    AGP agp;

    public GetAgentsInfo(AGP agent) {
        super(agent);
        agp = agent;
    }

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " : tarefa iniciada : GetAgentsInfo");
        try (Connection conn = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            for (Protocolo protocolo : dsl.select().from(T_PROTOCOLO).fetch().into(Protocolo.class)) {
                SwingUtilities.invokeLater(() -> agp.getView().getListAPModel().addElement(protocolo));
            }
            for (Distribuidor distribuidor : dsl.select().from(T_DISTRIBUIDOR).fetch().into(Distribuidor.class)) {
                SwingUtilities.invokeLater(() -> agp.getView().getListADModel().addElement(distribuidor));
            }
            for (Magistrado magistrado : dsl.select().from(T_MAGISTRADO).orderBy(T_MAGISTRADO.NOM_MAGISTRADO).fetch().into(Magistrado.class)) {
                SwingUtilities.invokeLater(() -> agp.getView().getListAMModel().addElement(magistrado));
            }
        } catch (Exception e) {
            Utils.logError(myAgent.getLocalName() + " : erro ao conectar com banco de dados");
        }
    }
}
