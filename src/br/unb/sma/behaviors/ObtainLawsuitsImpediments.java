package br.unb.sma.behaviors;

import br.unb.sma.agents.AM;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Set;

import static br.unb.sma.database.Tables.T_IMPEDIMENTO_PROCESSO;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class ObtainLawsuitsImpediments extends OneShotBehaviour {

    AM am;

    public ObtainLawsuitsImpediments(AM agent) {
        super(agent);
        this.am = agent;
    }

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " : tarefa iniciada : ObtainLawsuitsImpediments");
        /* Obter a relação de impedimentos quanto a processos para um determinado magistrado
        SELECT cod_processo
          FROM t_impedimento_processo
         WHERE cod_magistrado = ?codMagistrado?
         */
        Set<Long> impedimentos = am.getDbDSL().select(T_IMPEDIMENTO_PROCESSO.COD_PROCESSO)
                .from(T_IMPEDIMENTO_PROCESSO)
                .where(T_IMPEDIMENTO_PROCESSO.COD_MAGISTRADO.equal(am.getMagistrado().getCodMagistrado()))
                .fetch().intoSet(T_IMPEDIMENTO_PROCESSO.COD_PROCESSO);
        am.setImpedimentosProcessos(impedimentos);
    }
}
