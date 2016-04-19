package br.unb.sma.behaviors;

import br.unb.sma.agents.AP;
import br.unb.sma.database.tables.TFaseProcessual;
import br.unb.sma.database.tables.TProcesso;
import br.unb.sma.entities.Processo;
import jade.core.behaviours.OneShotBehaviour;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;
import static br.unb.sma.database.Tables.T_PROCESSO;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public class ObtainLawsuitAwaintingDistribution extends OneShotBehaviour {

    AP ap;
    byte numTribunal;

    public ObtainLawsuitAwaintingDistribution(AP agent) {
        super(agent);
        ap = agent;
        numTribunal = ap.getNumTribunal();
    }

    @Override
    public void action() {
        Processo processo;
        /* Obter processo do banco de dados
           SELECT proc.cod_processo, num_processo, num_digito, ano_processo, num_segmento, num_tribunal, num_origem, dta_autuacao
             FROM t_processo proc
       INNER JOIN t_fase_processual fase
               ON proc.cod_processo = fase.cod_processo
            WHERE num_tribunal = ?numTribunal?
              AND cod_magistrado is null
            LIMIT 1
         */
        TProcesso p = T_PROCESSO.as("p");
        TFaseProcessual f = T_FASE_PROCESSUAL.as("f");
        processo = ap.getDbDSL()
                .select(p.COD_PROCESSO, p.NUM_PROCESSO, p.NUM_DIGITO, p.ANO_PROCESSO, p.NUM_SEGMENTO, p.NUM_TRIBUNAL, p.NUM_ORIGEM, p.DTA_AUTUACAO)
                .from(p)
                .innerJoin(f)
                .on(p.COD_PROCESSO.equal(f.COD_PROCESSO))
                .where(p.NUM_TRIBUNAL.equal(numTribunal))
                .and(f.COD_MAGISTRADO.isNull())
                .and(f.COD_MOTIVO_REDIST.isNull())
                .orderBy(p.DTA_AUTUACAO)
                .limit(1)
                .fetchOneInto(Processo.class);
        ap.setProcesso(processo);
        ap.updateQtdProcessos();
    }
}
