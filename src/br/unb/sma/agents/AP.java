package br.unb.sma.agents;

import br.unb.sma.agents.gui.APview;
import br.unb.sma.behaviors.ObtainLawsuitAwaintingDistribution;
import br.unb.sma.behaviors.UpdateLawsuitDB;
import br.unb.sma.entities.HistDistribuicao;
import br.unb.sma.entities.Processo;
import br.unb.sma.entities.ProcessoCompleto;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_PROCESSO;
/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AP extends SMAgent {

    public static final String SERVICE_TYPE = "AP";
    public static final String REQUEST_LAWSUIT = "request-lawsuit";
    public static final String INFORM_DISTRIBUTION = "inform-distribution";
    private final String[] SERVICES = {AP.REQUEST_LAWSUIT, INFORM_DISTRIBUTION};

    Protocolo protocolo;
    Processo processo;
    Integer qtdProcessos;

    private JFrame gui;
    private AP agent = this;
    private APview view;

    @Override
    protected void setup() {
        protocolo = (Protocolo) getArguments()[0];
        super.setup();
        addBehaviour(new ObtainLawsuitAwaintingDistribution(agent));
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
    public CyclicBehaviour receiveMessages() {
        return receiveMessages;
    }

    @Override
    public String toString() {
        return protocolo.getNomProtocolo() + " (" + getAgentState().toString() + ")";
    }

    public void setProcesso(Processo processo) {
        if (processo != null) {
            this.processo = processo;
            if (isGUIenable) {
                SwingUtilities.invokeLater(() -> {
                    if (gui.isDisplayable()) {
                        view.setProcesso(processo.toString());
                    }
                });
            }
        } else {
            this.processo = null;
            if (isGUIenable) {
                SwingUtilities.invokeLater(() -> {
                    if (gui.isDisplayable()) {
                        view.setProcesso("sem processos");
                    }
                });
            }
        }
    }

    @Override
    protected void processMessages(ACLMessage msg) {
        super.processMessages(msg);
        if (!(msg.getEnvelope() == null)) {
            switch (msg.getEnvelope().getComments()) {
                case REQUEST_LAWSUIT:
                    processRequestLawsuit(msg);
                    break;
                case INFORM_DISTRIBUTION:
                    processInformDistribution(msg);
                    break;
            }
        }
    }

    private void processRequestLawsuit(ACLMessage msg) {
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(msg.getSender());
        reply.setConversationId(msg.getConversationId());
        Envelope envelope = new Envelope();
        if (processo != null) {
            ProcessoCompleto pc = new ProcessoCompleto(processo, this);
            envelope.setComments(AD.INFORM_LAWSUIT);
            reply.setEnvelope(envelope);
            try {
                reply.setContentObject(pc);
                send(reply);
            } catch (IOException e) {
                Utils.logError(getLocalName() + " : erro ao gerar processo para distribuição");
            }
        } else {
            envelope.setComments(AD.INFORM_NO_LAWSUIT);
            reply.setEnvelope(envelope);
            send(reply);
        }
    }

    private void processInformDistribution(ACLMessage msg) {
        try {
            HistDistribuicao distribuicao = (HistDistribuicao) msg.getContentObject();
            addBehaviour(new UpdateLawsuitDB(this, distribuicao), msg.getConversationId());
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao obter informação de distribuição");
        }
    }

    protected void loadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Loading the GUI
                    gui = new JFrame("AP : " + protocolo.getNomProtocolo());
                    view = new APview(agent);
                    gui.setContentPane(view.getForm());
                    gui.pack();
                    gui.setLocation(Utils.guiLocation("AP"));
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

    private Integer getQtdProcessos() {
        if (qtdProcessos == null) {
            qtdProcessos = getDbDSL()
                    .selectCount()
                    .from(T_PROCESSO)
                    .innerJoin(T_FASE_PROCESSUAL)
                    .on(T_PROCESSO.COD_PROCESSO.equal(T_FASE_PROCESSUAL.COD_PROCESSO))
                    .where(T_PROCESSO.NUM_TRIBUNAL.equal(protocolo.getNumTribunal()))
                    .and(T_FASE_PROCESSUAL.COD_MAGISTRADO.isNull())
                    .and(T_FASE_PROCESSUAL.COD_MOTIVO_REDIST.isNull())
                    .fetchOne()
                    .value1();
        }
        return qtdProcessos--;
    }

    public void updateQtdProcessos() {
        if (view != null) {
            if (view.getForm().isDisplayable()) {
                view.setQtdProcessosFila(getQtdProcessos());
            }
        }
    }

    public byte getNumTribunal() {
        return protocolo.getNumTribunal();
    }
}
