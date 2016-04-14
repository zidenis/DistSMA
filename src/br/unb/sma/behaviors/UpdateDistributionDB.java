package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.database.tables.records.THistDistribuicaoRecord;
import br.unb.sma.entities.HistDistribuicao;
import jade.core.behaviours.OneShotBehaviour;

import static br.unb.sma.database.Tables.T_HIST_DISTRIBUICAO;

/**
 * Created by zidenis.
 * 14-04-2016
 */
public class UpdateDistributionDB extends OneShotBehaviour {

    HistDistribuicao distribuicao;
    AD agent;

    public UpdateDistributionDB(AD agent, HistDistribuicao distribuicao) {
        this.agent = agent;
        this.distribuicao = distribuicao;
    }

    @Override
    public void action() {
        THistDistribuicaoRecord distribuicaoRecord = new THistDistribuicaoRecord();
        distribuicaoRecord.from(distribuicao);
        agent.getDbDSL().insertInto(T_HIST_DISTRIBUICAO).set(distribuicaoRecord).execute();
    }
}
