package br.unb.sma.behaviors;

import br.unb.sma.agents.AM;
import br.unb.sma.entities.ComposicaoOj;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;

import java.util.List;

import static br.unb.sma.database.Tables.T_COMPOSICAO_OJ;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class ObtainOJComposition extends OneShotBehaviour {

    AM am;

    public ObtainOJComposition(AM agent) {
        super(agent);
        this.am = agent;
    }

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " - tarefa iniciada : ObtainOJComposition");
        List<ComposicaoOj> composicaoOjList;
        /* Obter Órgãos Judicantes de um determinado Magistrado
        SELECT *
          FROM t_composicao_oj
         WHERE cod_magistrado = ?codMagistrado?
           AND dta_ter_atuacao is NULL
         */
        composicaoOjList = am.getDbDSL().select()
                .from(T_COMPOSICAO_OJ)
                .where(T_COMPOSICAO_OJ.COD_MAGISTRADO.equal(am.getMagistrado().getCodMagistrado()))
                .and(T_COMPOSICAO_OJ.DTA_TER_ATUACAO.isNull())
                .fetchInto(ComposicaoOj.class);
        am.setComposicaoOjList(composicaoOjList);
    }
}
