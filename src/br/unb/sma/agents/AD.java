package br.unb.sma.agents;

import br.unb.sma.agents.gui.ADview;
import br.unb.sma.behaviors.*;
import br.unb.sma.entities.*;
import br.unb.sma.utils.Utils;
import jade.core.AID;
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

import java.util.*;

import static br.unb.sma.database.Tables.T_HIST_DISTRIBUICAO;

/**
 * AD (Agente de Distribuição - Distribution Agent)
 * @author zidenis
 * @version 2016.4.22
 */
public class AD extends LawDisTrAgent {

    public static final String SERVICE_TYPE = "AD";
    public static final String DISTRIBUTION = "distribution";
    public static final String INFORM_PLATAFORM_CHANGE = "inform-plataform-change";
    public static final String INFORM_COMPOSTION = "inform-composition";
    public static final String INFORM_LAWSUIT = "inform-lawsuit";
    public static final String INFORM_NO_LAWSUIT = "inform-no-lawsuit";
    public static final String INFORM_IMPEDIMENT = "inform-impediment";
    public static final String INFORM_COMPETENCE = "inform-competence";
    private final String DROOLS_RULES_FILE = "br/unb/sma/rules/Distribution.drl";

    private AD ad = this;
    private Distribuidor distributor;
    private Integer distributionId; // an identification for the distribution
    private boolean running = false; // Identifies if LawDisTrA is currently running on continuous distribution mode
    private boolean findingAgentsMutex = false; // Using mutex to avoid multiple invokes of findMagistratesAndProtocolAgents on short period of time
    private Map<String, List<ComposicaoOj>> judginOrgansComposition = new HashMap<>(); // Maps the magistrate id with the judging organs that he is member of
    private List<Competencia> judginOrgansCompetencies; // A list of juding organs competencies
    private DFAgentDescription[] protocolAgents; // the Protocol Agents available in LawDisTrA
    private DFAgentDescription[] magistrateAgents; // the Magistrate Agentes available in LawDisTrA
    private Map<Long, Set<String>> askedMagistrates = new HashMap<>(); // maps the lawsuit identification with tha magistrate agents that were asked about their competences
    private Map<Long, Set<Impedimento>> impediments = new HashMap<>(); // maps the lawsuit identification with the impediments of the process
    private Map<Long, AID> responsibleProtocolAgent = new HashMap<>(); // maps the lawsuit identification with the protocol agent identification responsible for handle the lawsuit
    private Set<Long> lawsuitsInProcessing = new HashSet<>(); // a set of lawsuit in distribution process
    private Set<AID> protocolAgentsInProcessing = new HashSet<>(); // a set of protocol agents in processing

    @Override
    protected void setup() {
        distributor = (Distribuidor) getArguments()[0];
        setServices(new String[]{DISTRIBUTION});
        setView(new ADview(ad));
        super.setup();
        distributionId = getLastDistributionIdFromDB() + 1;
        addBehaviour(new ObtainOJCompetencies(this));
        findMagistratesAndProtocolAgents();
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    /**
     * Gets the last distribution id recorded on database
     * SELECT max(seq_distribuicao)
     * FROM t_hist_distribuicao
     * WHERE cod_distribuidor = 'AD01'
     *
     * @return the distribution id
     */
    private Integer getLastDistributionIdFromDB() {

        Integer actualSeq = getDbDSL()
                .select(T_HIST_DISTRIBUICAO.SEQ_DISTRIBUICAO.max())
                .from(T_HIST_DISTRIBUICAO)
                .where(T_HIST_DISTRIBUICAO.COD_DISTRIBUIDOR.equal(distributor.getCodDistribuidor()))
                .fetchOne().value1();
        if (actualSeq == null) actualSeq = 0;
        return actualSeq;
    }

    /**
     * Discovers the Protocol and Magistrate agents alive in the plataform
     */
    private void findMagistratesAndProtocolAgents() {
        judginOrgansComposition.clear();
        protocolAgentsInProcessing.clear();
        findingAgentsMutex = true;
        addBehaviour(new SearchMagistrateAgents(this));
        addBehaviour(new SearchProtocolAgents(this));
    }

    @Override
    protected void processReceivedMessage(ACLMessage msg) {
        super.processReceivedMessage(msg);
        switch (msg.getEnvelope().getComments()) {
            case INFORM_PLATAFORM_CHANGE:
                processInformPlataformChange();
                break;
            case INFORM_COMPOSTION:
                processInformComposition(msg);
                break;
            case INFORM_LAWSUIT:
                processInformLawsuit(msg);
                break;
            case INFORM_NO_LAWSUIT:
                processInformNoLawsuit(msg);
                break;
            case INFORM_IMPEDIMENT:
                processInformImpediment(msg, true);
                break;
            case INFORM_COMPETENCE:
                processInformImpediment(msg, false);
                break;
        }
    }

    /**
     * Process messages that informs that the agents plataform composition has changed, agentes were born or die
     */
    private void processInformPlataformChange() {
        if (findingAgentsMutex) {
            findingAgentsMutex = false;
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    findMagistratesAndProtocolAgents();
                }
            }, 1000);
        }
    }

    /**
     * Process messages received from Magistrate Agents that informs their Judgin Organs composition
     * @param msg the message to be processed
     */
    private void processInformComposition(ACLMessage msg) {
        try {
            List<ComposicaoOj> composicaoOjList = (List) msg.getContentObject();
            for (ComposicaoOj coj : composicaoOjList) {
                String codMag = coj.getCodMagistrado();
                if (judginOrgansComposition.containsKey(codMag)) {
                    judginOrgansComposition.get(codMag).add(coj);
                } else {
                    List<ComposicaoOj> ojs = new LinkedList<>();
                    ojs.add(coj);
                    judginOrgansComposition.put(codMag, ojs);
                }
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao identificar OJs de " + msg.getSender().getLocalName());
        }
    }

    /**
     * Process messages received from Protocol Agents with lawsuit information
     * @param msg that message that contains the lawsuit information
     */
    private void processInformLawsuit(ACLMessage msg) {
        try {
            ProcessoCompleto lawsuit = (ProcessoCompleto) msg.getContentObject();
            if (lawsuitsInProcessing.add(lawsuit.getProcesso().getCodProcesso())) {
                responsibleProtocolAgent.put(lawsuit.getProcesso().getCodProcesso(), msg.getSender());
                if (hasRelatedDistribution(lawsuit)) {
                    HistDistribuicao distribuicao = applyDistributionRules(lawsuit, msg.getConversationId());
                    requestAplicationOfDistribution(distribuicao, msg.getSender());
                } else {
                    Set<String> magistradosDisponiveis = new HashSet<>();
                    if (magistrateAgents.length > 0) {
                        for (DFAgentDescription dfd : magistrateAgents) {
                            magistradosDisponiveis.add(dfd.getName().getLocalName());
                        }
                        askedMagistrates.put(lawsuit.getProcesso().getCodProcesso(), magistradosDisponiveis);
                        addBehaviour(new QueryIfImpediment(this, lawsuit, magistrateAgents, msg.getConversationId()), msg.getConversationId());
                    } else {
                        Utils.logDistributionInfo(getLocalName(), "erro", msg.getConversationId(), "não há magistrados disponíveis para distribuição", "");
                        protocolAgentsInProcessing.remove(msg.getSender());
                        lawsuitsInProcessing.remove(lawsuit.getProcesso().getCodProcesso());
                    }
                }
            } else {
                Utils.logInfo(getLocalName() + " - processo informado já em processo distribuição");
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro no Processo recebido de " + msg.getSender().getLocalName());
        }
    }

    /**
     * Process messages received from Protocol Agents informing that it has no more lawsuits to be distributed
     *
     * @param msg the message to be processed
     */
    private void processInformNoLawsuit(ACLMessage msg) {
        protocolAgentsInProcessing.remove(msg.getSender());
        List<DFAgentDescription> newProtocolAgents = new LinkedList<>(Arrays.asList(protocolAgents));
        newProtocolAgents.removeIf(dfd -> dfd.getName().getLocalName().equals(msg.getSender().getLocalName()));
        protocolAgents = newProtocolAgents.toArray(new DFAgentDescription[0]);
        nextTurn();
    }

    /**
     * Process messages received from Magistrate Agentes informing that it is impeded or competent to judge an lawsuit
     *
     * @param msg     the message that contains lawsuit's impediment information
     * @param impedid true if the magistrate is impeded
     */
    private void processInformImpediment(ACLMessage msg, boolean impedid) {
        try {
            ProcessoCompleto lawsuit = (ProcessoCompleto) msg.getContentObject();
            String magistrado = msg.getSender().getLocalName();
            Long codProcesso = lawsuit.getProcesso().getCodProcesso();
            askedMagistrates.get(codProcesso).remove(magistrado);
            if (impedid) {
                Impedimento impedimento = new Impedimento(magistrado, msg.getUserDefinedParameter("tipo"), msg.getUserDefinedParameter("detalhamento"));
                if (impediments.get(codProcesso) == null) {
                    Set<Impedimento> impedimentosSet = new HashSet<>();
                    impedimentosSet.add(impedimento);
                    impediments.put(codProcesso, impedimentosSet);
                } else {
                    impediments.get(codProcesso).add(impedimento);
                }
            }
            if (askedMagistrates.get(codProcesso).size() == 0) {
                HistDistribuicao distribuicao = applyDistributionRules(lawsuit, msg.getConversationId());
                AID protocolAgentReceiver = responsibleProtocolAgent.get(codProcesso);
                requestAplicationOfDistribution(distribuicao, protocolAgentReceiver);
            }
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao processar informação de impedimento de " + msg.getSender().getLocalName());
        }
    }

    /**
     * Checks if the lawsuit has related lawsuits with previous distribution
     *
     * @param lawsuit the lawsuit
     * @return true if the lawsuit has related lawsuits with previsou distribution
     */
    private boolean hasRelatedDistribution(ProcessoCompleto lawsuit) {
        if (lawsuit.getFaseAnterior() != null) {
            if (!lawsuit.getFaseAnterior().getCodMagistrado().equals("null")) {
                return true;
            }
        } else if (lawsuit.getFasesProcRel() != null) {
            for (FaseProcessual fp : lawsuit.getFasesProcRel()) {
                if (fp.getCodMagistrado() != null) return true;
            }
        }
        return false;
    }

    /**
     * helper method to initiate the Drools Knowledge Base
     *
     * @return the Drools Knowledge Base
     */
    private KnowledgeBase buildDroolsKnowledgeBase() {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(DROOLS_RULES_FILE), ResourceType.DRL);
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

    /**
     * Applies the distribution rules for one lawsuit and creates one resulting distribution
     *
     * @param lawsuit        the lawsuit
     * @param distributionId the related distribution id
     * @return a distribution bean
     */
    private HistDistribuicao applyDistributionRules(ProcessoCompleto lawsuit, String distributionId) {
        KnowledgeBase kbase = buildDroolsKnowledgeBase();
        StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
        HistDistribuicao distribution = new HistDistribuicao();
        distribution.setCodDistribuidor(distributor.getCodDistribuidor());
        distribution.setSeqDistribuicao(Integer.valueOf(distributionId));
        Utils.logDistributionInfo(getLocalName(), "tarefa", distributionId, "aplicar regras de distribuição", "");
        ksession.insert(distribution);
        ksession.insert(lawsuit);
        for (Competencia competencia : judginOrgansCompetencies) ksession.insert(competencia);
        String[] codMagsAdministracao = new String[]{"MIGM", "MEMP", "MRLP"};
        Set<String> administracao = new HashSet<>(Arrays.asList(codMagsAdministracao));
        for (List<ComposicaoOj> magistrados : judginOrgansComposition.values()) {
            for (ComposicaoOj composicaoOj : magistrados) {
                if (!administracao.contains(composicaoOj.getCodMagistrado())) {
                    ksession.insert(composicaoOj);
                }
            }
        }
        if (impediments.get(lawsuit.getProcesso().getCodProcesso()) != null) {
            for (Impedimento impedimento : impediments.get(lawsuit.getProcesso().getCodProcesso())) {
                ksession.insert(impedimento);
            }
        }
        ksession.fireAllRules();
        ksession.dispose();
        return distribution;
    }

    /**
     * Request the aplication of an distribution to the responsible protocol agent
     *
     * @param distribution          the distribution bean
     * @param protocolAgentReceiver the responsible protocol agent for the distribution
     */
    private void requestAplicationOfDistribution(HistDistribuicao distribution, AID protocolAgentReceiver) {
        if (distribution != null) {
            String distribuicaoId = distribution.getSeqDistribuicao().toString();
            addBehaviour(new UpdateDistributionDB(this, distribution), distribuicaoId);
            addBehaviour(new InformDistribution(this, distribution, protocolAgentReceiver, distribuicaoId), distribuicaoId);
            Utils.logDistributionInfo(getLocalName(), "info", distribuicaoId, "distribuição finalizada", "");
        }
        lawsuitsInProcessing.remove(distribution.getCodProcesso());
        protocolAgentsInProcessing.remove(protocolAgentReceiver);
        nextTurn();
    }

    /**
     * Continues the distribution if the agent continuous running mode
     */
    private void nextTurn() {
        if (isRunning() && protocolAgentsInProcessing.size() == 0) {
            if (protocolAgents.length != 0) {
                requestLawsuit();
            } else {
                running = false;
                ((ADview) view).setPlayButtonText("Play");
            }
        }
    }

    /**
     * Defines the Judgin Organs' Competencies
     *
     * @param judginOrgansCompetencies a list of judgin organs competencies
     */
    public void setJudginOrgansCompetencies(List<Competencia> judginOrgansCompetencies) {
        this.judginOrgansCompetencies = judginOrgansCompetencies;
    }

    /**
     * Defines the protocol agentes available in LawDisTrA
     * @param protocolAgents an array of protocol agent descriptions
     */
    public void setProtocolAgents(DFAgentDescription[] protocolAgents) {
        this.protocolAgents = protocolAgents;
    }

    /**
     * Defines the magistrate agents available in LawDisTrA
     * @param magistrateAgents an array of magistrate agent descriptions
     */
    public void setMagistrateAgents(DFAgentDescription[] magistrateAgents) {
        this.magistrateAgents = magistrateAgents;
        addBehaviour(new RequestComposition(this, magistrateAgents, distributionId.toString()));
    }

    /**
     * Requests lawsuits from all the protocol agents available in the LawDisTrA
     */
    public void requestLawsuit() {
        for (DFAgentDescription dfd : protocolAgents) {
            if (!protocolAgentsInProcessing.contains(dfd.getName())) {
                protocolAgentsInProcessing.add(dfd.getName());
                Utils.logDistributionInfo(getLocalName(), "info", distributionId.toString(), "distribuição iniciada", "");
                addBehaviour(new RequestLawsuit(this, dfd, distributionId.toString()), distributionId.toString());
                distributionId++;
            }
        }

    }

    /**
     * Checks if the LawDisTrA is currently running on continuous distribution mode
     * @return the running state
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * Defines the running state
     *
     * @param running true if the distribution should be made continously. False if distribution should be made one time after each request
     */
    public void setRunning(boolean running) {
        this.running = running;
    }
}
