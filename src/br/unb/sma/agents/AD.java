package br.unb.sma.agents;

import br.unb.sma.agents.gui.ADview;
import br.unb.sma.behaviors.*;
import br.unb.sma.entities.*;
import br.unb.sma.utils.Utils;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.KnowledgeBaseFactory;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderError;
import org.kie.internal.builder.KnowledgeBuilderErrors;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.Timer;

import static br.unb.sma.database.Tables.T_HIST_DISTRIBUICAO;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AD extends SMAgent {

    public static final String SERVICE_TYPE = "AD";
    public static final String DISTRIBUTE = "distribute";
    public static final String INFORM_COMPOSTION = "inform-composition";
    public static final String INFORM_LAWSUIT = "inform-lawsuit";
    public static final String INFORM_PLATAFORM_CHANGE = "inform-plataform-change";
    public static final String INFORM_IMPEDIMENT = "inform-impediment";
    public static final String INFORM_COMPETENCE = "inform-competence";
    private final String[] SERVICES = {DISTRIBUTE};
    boolean findAgentsMutex = false;
    private Distribuidor distribuidor;
    private Integer seqDistribuicao;
    private JFrame gui;
    private AD agent = this;
    private ADview view;
    private Map<String, List<String>> competencias; // "Classe do Processo" -> ["Órgãos Judicantes"]
    private DFAgentDescription[] protocolAgents;
    private DFAgentDescription[] magistrateAgents;
    private Map<String, Set<AID>> composicao; // "Órgão Judicante" -> {Agentes Magistrados}
    private Map<Long, Set<String>> magistradosQuestionados = new HashMap<>(); // "Código do Processo" -> {Magistrados}
    private Map<Long, Set<String>> magistradosImpedidos = new HashMap<>(); // "Código do Processo" -> {Magistrados}
    private Map<Long, Set<String>> magistradosCompetentes = new HashMap<>(); // "Código do Processo" -> {Magistrados}
    private Map<Long, AID> protocoloResponsavel = new HashMap<>(); // "Código do Processo" -> Agente Protocolo

    private static KnowledgeBase buildDroolsKnowledgeBase() {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource("br/unb/sma/rules/Distribution.drl"), ResourceType.DRL);
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error : errors) {
                Utils.logError(error.toString());
            }
        }
        KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
        kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
        return kbase;
    }

    @Override
    protected void setup() {
        distribuidor = (Distribuidor) getArguments()[0];
        super.setup();
        seqDistribuicao = getNextSeqDistribuicaoFromDB();
        addBehaviour(new ObtainOJCompetencies(this));
        if (composicao == null) findAgents();
    }

    private Integer getNextSeqDistribuicaoFromDB() {
        /*
        SELECT max(seq_distribuicao)
        FROM t_hist_distribuicao
        WHERE cod_distribuidor = 'AD01'
         */
        Integer actualSeq = getDbDSL()
                .select(T_HIST_DISTRIBUICAO.SEQ_DISTRIBUICAO.max())
                .from(T_HIST_DISTRIBUICAO)
                .where(T_HIST_DISTRIBUICAO.COD_DISTRIBUIDOR.equal(distribuidor.getCodDistribuidor()))
                .fetchOne().value1();
        if (actualSeq == null) actualSeq = 0;
        return actualSeq + 1;
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
    protected void processMessages(ACLMessage msg) {
        super.processMessages(msg);
        switch (msg.getEnvelope().getComments()) {
            case INFORM_COMPOSTION:
                processInformComposition(msg);
                break;
            case INFORM_LAWSUIT:
                processInformLawsuit(msg);
                break;
            case INFORM_PLATAFORM_CHANGE:
                processInformPlataformChange();
                break;
            case INFORM_IMPEDIMENT:
                processInformImpediment(msg, true);
                break;
            case INFORM_COMPETENCE:
                processInformImpediment(msg, false);
                break;
        }
    }

    private void processInformComposition(ACLMessage msg) {
        try {
            List<ComposicaoOj> composicaoOjList = (List) msg.getContentObject();
            for (ComposicaoOj coj : composicaoOjList) {
                String sigOj = coj.getSigOj();
                if (composicao != null && composicao.containsKey(sigOj)) {
                    composicao.get(sigOj).add(msg.getSender());
                } else {
                    if (composicao == null) {
                        composicao = new HashMap<>();
                    }
                    Set<AID> ams = new HashSet<>();
                    ams.add(msg.getSender());
                    composicao.put(sigOj, ams);
                }
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao identificar OJs de " + msg.getSender().getLocalName());
        }
    }

    private void processInformLawsuit(ACLMessage msg) {
        try {
            ProcessoCompleto pc = (ProcessoCompleto) msg.getContentObject();
            protocoloResponsavel.put(pc.getProcesso().getCodProcesso(), msg.getSender());
            if (hasRelatedDistribution(pc)) {
                HistDistribuicao distribuicao = applyDistributionRules(pc);
                requestAplicationOfDistribution(distribuicao, msg.getSender());
            } else {
                Set<String> magistradosDisponiveis = new HashSet<>();
                for (DFAgentDescription dfd : magistrateAgents) {
                    magistradosDisponiveis.add(dfd.getName().getLocalName());
                }
                magistradosQuestionados.put(pc.getProcesso().getCodProcesso(), magistradosDisponiveis);
                addBehaviour(new CheckAMImpediment(this, pc, magistrateAgents));
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro no Processo recebido de " + msg.getSender().getLocalName());
        }
    }

    private HistDistribuicao applyDistributionRules(ProcessoCompleto pc) {
        KnowledgeBase kbase = buildDroolsKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        HistDistribuicao distribuicao = new HistDistribuicao();
        distribuicao.setCodDistribuidor(distribuidor.getCodDistribuidor());
        distribuicao.setSeqDistribuicao(seqDistribuicao++);
        ksession.insert(distribuicao);
        ksession.insert(pc);
        ksession.fireAllRules();
        ksession.dispose();
        return distribuicao;
    }

    private boolean hasRelatedDistribution(ProcessoCompleto pc) {
        if (pc.getFaseAnterior() != null) {
            if (!pc.getFaseAnterior().getCodMagistrado().equals("null")) {
                return true;
            }
        } else if (pc.getFasesProcRel() != null) {
            for (FaseProcessual fp : pc.getFasesProcRel()) {
                if (!fp.getCodMagistrado().equals("null")) return true;
            }
        }
        return false;
    }

    private void processInformPlataformChange() {
        if (findAgentsMutex) {
            findAgentsMutex = false;
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    findAgents();
                }
            }, 1000);
        }
    }

    private void processInformImpediment(ACLMessage msg, boolean impedido) {
        try {
            ProcessoCompleto pc = (ProcessoCompleto) msg.getContentObject();
            String magistrado = msg.getSender().getLocalName();
            Long codProcesso = pc.getProcesso().getCodProcesso();
            magistradosQuestionados.get(codProcesso).remove(magistrado);
            if (impedido) {
                if (magistradosImpedidos.get(codProcesso) == null) {
                    Set<String> magistradosImpedidosSet = new HashSet<>();
                    magistradosImpedidosSet.add(magistrado);
                    magistradosImpedidos.put(codProcesso, magistradosImpedidosSet);
                } else {
                    magistradosImpedidos.get(codProcesso).add(magistrado);
                }
                Utils.logInfo(msg.getUserDefinedParameter("razao"));
            } else {
                if (magistradosCompetentes.get(codProcesso) == null) {
                    Set<String> magistradosCompetentesSet = new HashSet<>();
                    magistradosCompetentesSet.add(magistrado);
                    magistradosCompetentes.put(codProcesso, magistradosCompetentesSet);
                } else {
                    magistradosCompetentes.get(codProcesso).add(magistrado);
                }
            }
            if (magistradosQuestionados.get(codProcesso).size() == 0) {
                HistDistribuicao distribuicao = applyDistributionRules(pc);
                AID protocolAgentReceiver = protocoloResponsavel.get(codProcesso);
                requestAplicationOfDistribution(distribuicao, protocolAgentReceiver);
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao processar informação de impedimento de " + msg.getSender().getLocalName());
        }
    }

    private void requestAplicationOfDistribution(HistDistribuicao distribuicao, AID protocolAgentReceiver) {
        Utils.logInfo(getLocalName() + " Distribuir para " + protocolAgentReceiver.getLocalName() + ": " + distribuicao);
//        if (distribuicao != null) {
//            addBehaviour(new UpdateDistributionDB(this, distribuicao));
//        }
    }

    protected void loadGUI() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Loading the GUI
                    gui = new JFrame("AD : " + getLocalName());
                    view = new ADview(agent);
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

    @Override
    public CyclicBehaviour receiveMessages() {
        return receiveMessages;
    }

    public void setCompetencias(HashMap<String, List<String>> competencias) {
        this.competencias = competencias;
//        Utils.logInfo(competencias.toString());
    }

    public DFAgentDescription[] getProtocolAgents() {
        return protocolAgents;
    }

    public void setProtocolAgents(DFAgentDescription[] protocolAgents) {
        this.protocolAgents = protocolAgents;
//        for (DFAgentDescription dfd : protocolAgents) {
//            Utils.logInfo(dfd.getName().toString());
//        }
    }

    public DFAgentDescription[] getMagistrateAgents() {
        return magistrateAgents;
    }

    public void setMagistrateAgents(DFAgentDescription[] magistrateAgents) {
        this.magistrateAgents = magistrateAgents;
//        for (DFAgentDescription dfd : magistrateAgents) {
//            Utils.logInfo(dfd.getName().toString());
//        }
        addBehaviour(new RequestOJComposition(this, magistrateAgents));
    }

    private void findAgents() {
        if (composicao != null) {
            composicao.clear();
        }
        // Using mutex to avoid multiple findAgents on short time
        findAgentsMutex = true;
        addBehaviour(new DiscoverMagistrateAgents(this));
        addBehaviour(new DiscoverProtocolAgents(this));
    }

    public void requestLawsuit() {
        if (protocolAgents != null) {
            addBehaviour(new RequestLawsuit(this, protocolAgents));
        }
    }

}
