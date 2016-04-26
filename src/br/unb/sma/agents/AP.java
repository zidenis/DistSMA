package br.unb.sma.agents;

import br.unb.sma.agents.gui.APview;
import br.unb.sma.behaviors.InformLawsuit;
import br.unb.sma.behaviors.ObtainLawsuitAwaintingDistribution;
import br.unb.sma.behaviors.UpdateLawsuitDB;
import br.unb.sma.entities.HistDistribuicao;
import br.unb.sma.entities.Processo;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.Utils;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import javax.swing.*;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_PROCESSO;


/**
 * AP (Agente de Protocolo - Protocol Agent)
 * @author zidenis
 * @version 2016.4.22
 */
public class AP extends LawDisTrAgent {

    public static final String SERVICE_TYPE = "AP";
    public static final String REQUEST_LAWSUIT = "request-lawsuit";
    public static final String INFORM_DISTRIBUTION = "inform-distribution";

    private AP ap = this;
    private Protocolo protocol;
    private Processo lawsuit;
    private Integer lawsuitQty;

    @Override
    protected void setup() {
        protocol = (Protocolo) getArguments()[0];
        setServices(new String[]{AP.REQUEST_LAWSUIT, INFORM_DISTRIBUTION});
        setView(new APview(ap));
        super.setup();
        addBehaviour(new ObtainLawsuitAwaintingDistribution(ap));
    }

    @Override
    public String getServiceType() {
        return SERVICE_TYPE;
    }

    /**
     * Deals with the received messages by AP agents
     *
     * @param msg the message that will be processed
     */
    @Override
    protected void processReceivedMessage(ACLMessage msg) {
        super.processReceivedMessage(msg);
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

    /**
     * Process the Lawsuit Request message
     * @param msg the request message
     */
    private void processRequestLawsuit(ACLMessage msg) {
        addBehaviour(new InformLawsuit(this, msg), msg.getConversationId());
    }

    /**
     * Process the inform of a Lawsuit Distribution
     * @param msg the inform distribution message
     */
    private void processInformDistribution(ACLMessage msg) {
        try {
            HistDistribuicao distribution = (HistDistribuicao) msg.getContentObject();
            addBehaviour(new UpdateLawsuitDB(this, distribution), msg.getConversationId());
            addBehaviour(new ObtainLawsuitAwaintingDistribution(this));
        } catch (UnreadableException e) {
            Utils.logError(getLocalName() + " : erro ao processar informação de distribuição");
        }
    }

    /**
     * Gets the currently loaded lawsuit
     *
     * @return the currently loaded lawsuit
     */
    public Processo getLawsuit() {
        return lawsuit;
    }

    /**
     * Defines the actual lawsuit that will be handled by the AP agent and updates its GUI
     *
     * @param lawsuit the lawsuit
     */
    public void setLawsuit(Processo lawsuit) {
        if (lawsuit != null) {
            this.lawsuit = lawsuit;
            if (isGUIEnabled) {
                SwingUtilities.invokeLater(() -> {
                    if (gui.isDisplayable()) {
                        ((APview) view).setProcesso(lawsuit.toString());
                        ((APview) view).setQtdProcessosFila(getLawsuitQty());
                    }
                });
            }
        } else {
            this.lawsuit = null;
            if (isGUIEnabled) {
                SwingUtilities.invokeLater(() -> {
                    if (gui.isDisplayable()) {
                        ((APview) view).setProcesso("sem processos");
                        ((APview) view).setQtdProcessosFila(getLawsuitQty());
                    }
                });
            }
        }
    }

    /**
     * Gets the total of lawsuits awainting for distribution on lawsuits database
     *
     * @return the number of lawsuits
     */
    private Integer getLawsuitQty() {
        if (lawsuitQty == null) {
            lawsuitQty = getDbDSL()
                    .selectCount()
                    .from(T_PROCESSO)
                    .innerJoin(T_FASE_PROCESSUAL)
                    .on(T_PROCESSO.COD_PROCESSO.equal(T_FASE_PROCESSUAL.COD_PROCESSO))
                    .where(T_PROCESSO.NUM_TRIBUNAL.equal(protocol.getNumTribunal()))
                    .and(T_FASE_PROCESSUAL.COD_MAGISTRADO.isNull())
                    .and(T_FASE_PROCESSUAL.COD_MOTIVO_REDIST.isNull())
                    .fetchOne()
                    .value1();
        }
        return lawsuitQty--;
    }

    /**
     * Gets the number of the court corresponding to the AP agent
     * @return the number of the court
     */
    public byte getNumTribunal() {
        return protocol.getNumTribunal();
    }

    @Override
    public String getAgentName() {
        return protocol.getNomProtocolo();
    }
}
