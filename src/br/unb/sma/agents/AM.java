package br.unb.sma.agents;

import br.unb.sma.agents.gui.AMview;
import br.unb.sma.behaviors.DFRegistration;
import br.unb.sma.behaviors.ObtainImpediments;
import br.unb.sma.behaviors.ObtainOJComposition;
import br.unb.sma.behaviors.ReceiveMessages;
import br.unb.sma.entities.ComposicaoOj;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Set;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_HIST_DISTRIBUICAO;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AM extends SMAgent {

    public static final String MSG_DO_SOMETHING = "";

    private final String SERVICE_TYPE = "magistrado";
    private final String[] SERVICES = {AM.MSG_DO_SOMETHING};

    Magistrado magistrado;
    CyclicBehaviour receiveMessages;
    List<ComposicaoOj> composicaoOjList;
    Set<Long> impedimentosProcessos;
    Set<Long> impedimentosPartes;
    Set<Integer> impedimentosAdvogados;
    private JFrame gui;
    private AM agent = this;
    private AMview view;

    @Override
    protected void setup() {
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
                    //Retrieves startup arguments
                    magistrado = (Magistrado) getArguments()[0];
                    //Registering the provided services in the yellow pages catalogue (DF agent)
                    //Starting the initial behaviours
                    receiveMessages = new ReceiveMessages();
                    addBehaviour(receiveMessages);
                    addBehaviour(new DFRegistration(agent, agent));
                    Utils.logInfo(getLocalName() + " : agente iniciado");
                    addBehaviour(new ObtainOJComposition(agent));
                    addBehaviour(new ObtainImpediments(agent));
                    updateNumProcessosBaixados();
                    updateNumProcessosDistribuidos();
                } catch (Exception e) {
                    Utils.logError(getLocalName() + " : erro ao criar GUI");
                    e.printStackTrace();
                }
            }
        });
    }


    protected void processMessage(ACLMessage msg) {
        if (msg.getContent().equals(MSG_DO_SOMETHING)) {
            //TODO processar mensagens recebidas
        }
    }

    public void setComposicaoOjList(List<ComposicaoOj> composicaoOjList) {
        this.composicaoOjList = composicaoOjList;
        view.setComposicaoOJ(Utils.join(composicaoOjList.listIterator(), "\n"));
        SwingUtilities.invokeLater(() -> gui.pack());
    }

    public void setImpedimentosProcessos(Set<Long> impedimentosProcessos) {
        this.impedimentosProcessos = impedimentosProcessos;
        view.setQtdProcImped(String.valueOf(impedimentosProcessos.size()));
    }

    public void setImpedimentosPartes(Set<Long> impedimentosPartes) {
        this.impedimentosPartes = impedimentosPartes;
        view.setQtdParteImped(String.valueOf(impedimentosPartes.size()));
    }

    public void setImpedimentosAdvogados(Set<Integer> impedimentosAdvogados) {
        this.impedimentosAdvogados = impedimentosAdvogados;
        view.setQtdAdvImped(String.valueOf(impedimentosAdvogados.size()));
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
    public void closeGUI() {
        gui.dispatchEvent(new WindowEvent(gui, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public CyclicBehaviour receiveMessages() {
        return receiveMessages;
    }
}
