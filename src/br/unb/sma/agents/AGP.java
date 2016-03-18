package br.unb.sma.agents;

import br.unb.sma.agents.gui.AGPview;
import br.unb.sma.entities.Distribuidor;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.AgentEntity;
import br.unb.sma.utils.DBconf;
import br.unb.sma.utils.Status;
import br.unb.sma.utils.Utils;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.basic.Action;
import jade.core.AID;
import jade.core.Agent;
import jade.core.ContainerID;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.CreateAgent;
import jade.domain.JADEAgentManagement.JADEManagementOntology;
import jade.domain.JADEAgentManagement.KillAgent;
import jade.lang.acl.ACLMessage;
import jade.proto.AchieveREInitiator;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import static br.unb.sma.database.Tables.*;

public class AGP extends Agent {

    private JFrame gui;
    private AGP agent = this;
    private AGPview view;

    protected void setup() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    gui = new JFrame(getLocalName());
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
                    getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
                    getContentManager().registerOntology(JADEManagementOntology.getInstance());
                    Utils.logInfo(getLocalName() + " : agente iniciado");
                    loadAgents();
                }
            }
        });
    }

    private void loadAgents() {
        try (Connection conn = DriverManager.getConnection(DBconf.URL, DBconf.USERNAME, DBconf.PASSWORD)) {
            DSLContext dsl = DSL.using(conn, SQLDialect.POSTGRES);
            for (Protocolo protocolo : dsl.select().from(T_PROTOCOLO).fetch().into(Protocolo.class)) {
                view.getListAPModel().addElement(protocolo);
            }
            for (Distribuidor distribuidor : dsl.select().from(T_DISTRIBUIDOR).fetch().into(Distribuidor.class)) {
                view.getListADModel().addElement(distribuidor);
            }
            for (Magistrado magistrado : dsl.select().from(T_MAGISTRADO).orderBy(T_MAGISTRADO.NOM_MAGISTRADO).fetch().into(Magistrado.class)) {
                view.getListAMModel().addElement(magistrado);
            }
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao conectar com banco de dados");
        }
    }

    public void activateAgent(AgentEntity agentEntity) {
        CreateAgent ca = new CreateAgent();
        ca.setAgentName(agentEntity.getAgentName());
        ca.setClassName(agentEntity.getClassName());
        ca.addArguments(agentEntity);
        try {
            ca.setContainer(new ContainerID(getContainerController().getContainerName(), null));
            Action actExpr = new Action(getAMS(), ca);
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(getAMS());
            request.setOntology(JADEManagementOntology.getInstance().getName());
            request.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
            request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            getContentManager().fillContent(request, actExpr);
            addBehaviour(new AchieveREInitiator(this, request) {
                @Override
                protected void handleInform(ACLMessage inform) {
                    agentEntity.setStatus(Status.ATIVADO);
                    view.update();
                }

                @Override
                protected void handleFailure(ACLMessage failure) {
                    Utils.logError(getLocalName() + " : erro ao iniciar o " + agentEntity.getAgentName());
                    Utils.logError(failure.getContent());
                }
            });

        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao iniciar " + agentEntity.getAgentName());
            e.printStackTrace();
        }
    }

    public void deactivateAgent(AgentEntity agentEntity) {
        KillAgent ka = new KillAgent();
        AID aid = new AID(agentEntity.getAgentName(), AID.ISLOCALNAME);
        ka.setAgent(aid);
        try {
            Action actExpr = new Action(getAMS(), ka);
            ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
            request.addReceiver(getAMS());
            request.setOntology(JADEManagementOntology.getInstance().getName());
            request.setLanguage(FIPANames.ContentLanguage.FIPA_SL);
            request.setProtocol(FIPANames.InteractionProtocol.FIPA_REQUEST);
            getContentManager().fillContent(request, actExpr);
            addBehaviour(new AchieveREInitiator(this, request) {
                @Override
                protected void handleInform(ACLMessage inform) {
                    agentEntity.setStatus(Status.DESATIVADO);
                    view.update();
                }

                @Override
                protected void handleFailure(ACLMessage failure) {
                    Utils.logError(getLocalName() + " : erro ao desativar " + agentEntity.getAgentName());
                    Utils.logError(failure.getContent());
                }
            });

        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao desativar " + agentEntity.getAgentName());
            e.printStackTrace();
        }
    }
}
