package br.unb.sma.behaviors;

import br.unb.sma.agents.AP;
import br.unb.sma.database.tables.TFaseProcessual;
import br.unb.sma.database.tables.TProcesso;
import br.unb.sma.entities.Processo;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;

import java.util.List;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_PROCESSO;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public class ObtainLawsuitsAwaintingDistribution extends OneShotBehaviour {

    AP ap;
    int qtdProcessos;
    byte numTribunal;

    public ObtainLawsuitsAwaintingDistribution(AP agent, byte numTribunal, int qtdProcessos) {
        super(agent);
        ap = agent;
        this.numTribunal = numTribunal;
        this.qtdProcessos = qtdProcessos;
    }

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " : tarefa iniciada : ObtainLawsuitsAwaintingDistribution");
        List<Processo> processosList;
        /* Obter 10 processos do banco de dados
           SELECT proc.cod_processo, num_processo, num_digito, ano_processo, num_segmento, num_tribunal, num_origem, dta_autuacao
             FROM t_processo proc
       INNER JOIN t_fase_processual fase
               ON proc.cod_processo = fase.cod_processo
            WHERE num_tribunal = 0
              AND cod_magistrado is null
            LIMIT 10
         */
        TProcesso p = T_PROCESSO.as("p");
        TFaseProcessual f = T_FASE_PROCESSUAL.as("f");
        processosList = ap.getDbDSL()
                .select(p.COD_PROCESSO, p.NUM_PROCESSO, p.NUM_DIGITO, p.ANO_PROCESSO, p.NUM_SEGMENTO, p.NUM_TRIBUNAL, p.NUM_ORIGEM, p.DTA_AUTUACAO)
                .from(p)
                .innerJoin(f)
                .on(p.COD_PROCESSO.equal(f.COD_PROCESSO))
                .where(p.NUM_TRIBUNAL.equal(numTribunal))
                .and(f.COD_MAGISTRADO.isNull())
                .orderBy(p.DTA_AUTUACAO)
                .limit(qtdProcessos)
                .fetchInto(Processo.class);
        ap.setProcessos(processosList);
        //Utils.logInfo(processosList.toString());
    }
}
