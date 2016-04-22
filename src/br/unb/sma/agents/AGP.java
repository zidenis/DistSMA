package br.unb.sma.agents;

import br.unb.sma.agents.gui.AGPview;
import br.unb.sma.behaviors.ActivateAgent;
import br.unb.sma.behaviors.DeactivateAgent;
import br.unb.sma.behaviors.GetAgentsInfo;
import br.unb.sma.behaviors.ShutdownSMA;
import br.unb.sma.entities.AgentEntity;
import jade.content.lang.sl.SLCodec;
import jade.domain.FIPANames;
import jade.domain.JADEAgentManagement.JADEManagementOntology;

/**
 * AGP (Agente Gestor da Plataforma - Plataform Management Agent)
 *
 * @author zidenis
 * @version 2016.4.22
 */
public class AGP extends LawDisTrAgent {

    public static final String STATUS_ENABLED = "ativado";
    public static final String STATUS_DISABLE = "desativ.";
    public static final String STATUS_LOADED = "";
    public static final String SERVICE_TYPE = "AGP";
    public static boolean ENABLE_GUI = true;
    private final String[] SERVICES = {""};

    private AGP agp = this;
    private AGPview view = new AGPview(agp);

    protected void setup() {
        super.setup();
        removeBehaviour(receiveMessages); // this agent does not need to receive messages
        getContentManager().registerLanguage(new SLCodec(), FIPANames.ContentLanguage.FIPA_SL);
        getContentManager().registerOntology(JADEManagementOntology.getInstance());
        addBehaviour(new GetAgentsInfo(agp));
    }

    @Override
    public AGPview getView() {
        return view;
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    @Override
    public String[] getServices() {
        return SERVICES;
    }

    /**
     * Activates an agent on LawDisTrA
     *
     * @param agentEntity an entity that represents the agent
     */
    public void activateAgent(AgentEntity agentEntity) {
        addBehaviour(new ActivateAgent(this, agentEntity));
    }

    /**
     * Deactivates an agent on LawDisTrA
     *
     * @param agentEntity an entity that represents the agent
     */
    public void deactivateAgent(AgentEntity agentEntity) {
        addBehaviour(new DeactivateAgent(this, agentEntity));
    }

    /**
     * Turns off the LawDisTrA
     */
    public void shutdownSMA() {
        addBehaviour(new ShutdownSMA());
    }

}
