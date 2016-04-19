package br.unb.sma.behaviors;

import br.unb.sma.agents.AP;
import br.unb.sma.entities.HistDistribuicao;
import jade.core.behaviours.OneShotBehaviour;

import static br.unb.sma.database.Tables.T_FASE_PROCESSUAL;

/**
 * Created by zidenis.
 * 14-04-2016
 */
public class UpdateLawsuitDB extends OneShotBehaviour {

    HistDistribuicao distribuicao;
    AP agent;

    public UpdateLawsuitDB(AP agent, HistDistribuicao distribuicao) {
        this.agent = agent;
        this.distribuicao = distribuicao;
    }

    @Override
    public void action() {
        if (distribuicao.getCodTipoDist().equals("N")) {
            agent.getDbDSL()
                    .update(T_FASE_PROCESSUAL)
                    .set(T_FASE_PROCESSUAL.COD_MOTIVO_REDIST, (byte) 0)
                    .where(T_FASE_PROCESSUAL.COD_PROCESSO.equal(distribuicao.getCodProcesso()))
                    .and(T_FASE_PROCESSUAL.COD_MAGISTRADO.isNull())
                    .and(T_FASE_PROCESSUAL.SIG_OJ.isNull())
                    .execute();
        } else {
            agent.getDbDSL()
                    .update(T_FASE_PROCESSUAL)
                    .set(T_FASE_PROCESSUAL.COD_MAGISTRADO, distribuicao.getCodMagistrado())
                    .set(T_FASE_PROCESSUAL.SIG_OJ, distribuicao.getSigOj())
                    .where(T_FASE_PROCESSUAL.COD_PROCESSO.equal(distribuicao.getCodProcesso()))
                    .and(T_FASE_PROCESSUAL.COD_MAGISTRADO.isNull())
                    .and(T_FASE_PROCESSUAL.SIG_OJ.isNull())
                    .execute();
        }
        agent.addBehaviour(new ObtainLawsuitAwaintingDistribution(agent));
    }
}
