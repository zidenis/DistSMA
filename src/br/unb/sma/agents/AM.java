package br.unb.sma.agents;

import br.unb.sma.agents.gui.AMview;
import br.unb.sma.behaviors.ObtainImpediments;
import br.unb.sma.behaviors.ObtainOJComposition;
import br.unb.sma.entities.*;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.Envelope;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_HIST_DISTRIBUICAO;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AM extends SMAgent {

    public static final String SERVICE_TYPE = "AM";
    public static final String REQUEST_COMPOSITION = "request-composition";
    public static final String QUERY_IF_IMPEDIMENT = "query-if-impediment";
    private final String[] SERVICES = {AM.REQUEST_COMPOSITION};

    Magistrado magistrado;
    List<ComposicaoOj> composicaoOjList;
    Set<Long> impedimentosProcessos;
    Set<Long> impedimentosPartes;
    Set<Integer> impedimentosAdvogados;
    private JFrame gui;
    private AM agent = this;
    private AMview view;

    @Override
    protected void setup() {
        magistrado = (Magistrado) getArguments()[0];
        super.setup();
        addBehaviour(new ObtainOJComposition(agent));
        addBehaviour(new ObtainImpediments(agent));
    }

    @Override
    protected void processMessages(ACLMessage msg) {
        super.processMessages(msg);
        switch (msg.getEnvelope().getComments()) {
            case REQUEST_COMPOSITION:
                processRequestComposition(msg);
                break;
            case QUERY_IF_IMPEDIMENT:
                processQueryIfImpediment(msg);
                break;
        }
    }

    private void processRequestComposition(ACLMessage msg) {
        ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
        reply.addReceiver(msg.getSender());
        Envelope envelope = new Envelope();
        envelope.setComments(AD.INFORM_COMPOSTION);
        reply.setEnvelope(envelope);
        try {
            reply.setContentObject((Serializable) composicaoOjList);
            send(reply);
        } catch (IOException e) {
            Utils.logError(getLocalName() + " : erro ao definir composição de magistrado");
        }
    }

    private void processQueryIfImpediment(ACLMessage msg) {
        try {
            ProcessoCompleto pc = (ProcessoCompleto) msg.getContentObject();
            boolean impedido = false;
            StringBuilder razaoImpedimento = new StringBuilder();
            // MA declarou-se impedido para julgar o processo determinado
            if (impedimentosProcessos.contains(pc.getProcesso().getCodProcesso())) {
                razaoImpedimento.append(getLocalName()).append(" impedido para julgar processo ").append(pc.getProcesso().toString()).append(" ");
                impedido = true;
            }
            for (Parte parte : pc.getPartes()) {
                if (impedimentosPartes.contains(parte.getCodParte())) {
                    razaoImpedimento.append(getLocalName()).append(" impedido para julgar processo em que figura a parte ").append(parte.getNomParte()).append(" ");
                    impedido = true;
                }
            }
            for (Advogado adv : pc.getAdvogados()) {
                if (impedimentosAdvogados.contains(adv.getNumAdvogado())) {
                    razaoImpedimento.append(getLocalName()).append(" impedido para julgar processo em que figura o advogado ").append(adv.getNomAdvogado()).append(" ");
                    impedido = true;
                }
            }
            ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
            reply.addReceiver(msg.getSender());
            Envelope envelope = new Envelope();
            if (impedido) {
                envelope.setComments(AD.INFORM_IMPEDIMENT);
                reply.addUserDefinedParameter("razao", razaoImpedimento.toString());
            } else {
                envelope.setComments(AD.INFORM_COMPETENCE);
            }
            reply.setEnvelope(envelope);
            reply.setContentObject(pc);
            send(reply);
        } catch (Exception e) {
            Utils.logError(getLocalName() + " : erro ao processar impedimentos em processo");
        }
    }

    @Override
    protected void loadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Loading the GUI
                    gui = new JFrame("AM : " + getLocalName());
                    view = new AMview(agent);
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
                    updateNumProcessosBaixados();
                    updateNumProcessosDistribuidos();

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

    public void setComposicaoOjList(List<ComposicaoOj> composicaoOjList) {
        this.composicaoOjList = composicaoOjList;
        SwingUtilities.invokeLater(() -> {
            view.setComposicaoOJ(Utils.join(composicaoOjList.listIterator(), "\n"));
            gui.pack();
        });
    }

    public void setImpedimentosProcessos(Set<Long> impedimentosProcessos) {
        this.impedimentosProcessos = impedimentosProcessos;
        SwingUtilities.invokeLater(() -> {
            view.setQtdProcImped(String.valueOf(impedimentosProcessos.size()));
            gui.pack();
        });
    }

    public void setImpedimentosPartes(Set<Long> impedimentosPartes) {
        this.impedimentosPartes = impedimentosPartes;
        SwingUtilities.invokeLater(() -> {
            view.setQtdParteImped(String.valueOf(impedimentosPartes.size()));
            gui.pack();
        });
    }

    public void setImpedimentosAdvogados(Set<Integer> impedimentosAdvogados) {
        this.impedimentosAdvogados = impedimentosAdvogados;
        SwingUtilities.invokeLater(() -> {
            view.setQtdAdvImped(String.valueOf(impedimentosAdvogados.size()));
            gui.pack();
        });
    }

    private void updateNumProcessosDistribuidos() {
        /*
        SELECT COUNT(cod_processo)
          FROM t_hist_distribuicao
         WHERE cod_magistrado = ?codMagistrado?
         */
        int count = getDbDSL().selectCount()
                .from(T_HIST_DISTRIBUICAO)
                .where(T_HIST_DISTRIBUICAO.COD_MAGISTRADO.equal(magistrado.getCodMagistrado()))
                .fetchOne()
                .value1();
        view.setQtdProcRec(String.valueOf(count));
    }

    private void updateNumProcessosBaixados() {
        /*
        SELECT COUNT(cod_processo)
          FROM t_fase_processual
         WHERE cod_magistrado = ?codMagistrado?
           AND dta_termino_fase IS NOT NULL
         */
        int count = getDbDSL().selectCount()
                .from(T_FASE_PROCESSUAL)
                .where(T_FASE_PROCESSUAL.COD_MAGISTRADO.equal(magistrado.getCodMagistrado()))
                .and(T_FASE_PROCESSUAL.DTA_TERMINO_FASE.isNotNull())
                .fetchOne()
                .value1();
        view.setQtdProcBaixa(String.valueOf(count));
    }

    public Magistrado getMagistrado() {
        return magistrado;
    }

    @Override
    public String toString() {
        return getLocalName() + " (" + getAgentState().toString() + ")";
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
}
