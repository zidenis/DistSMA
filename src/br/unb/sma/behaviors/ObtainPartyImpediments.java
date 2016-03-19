package br.unb.sma.behaviors;

import br.unb.sma.agents.AM;
import br.unb.sma.utils.Utils;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Set;

import static br.unb.sma.database.Tables.T_IMPEDIMENTO_PARTE;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class ObtainPartyImpediments extends OneShotBehaviour {

    AM am;

    public ObtainPartyImpediments(AM agent) {
        super(agent);
        this.am = agent;
    }

    @Override
    public void action() {
        Utils.logInfo(myAgent.getLocalName() + " : tarefa iniciada : ObtainPartyImpediments");
        /* Obter a relação de impedimentos quanto a processos para um determinado magistrado
        SELECT cod_parte
          FROM t_impedimento_parte
         WHERE cod_magistrado = ?cod_magistrado?
         */
        Set<Long> impedimentos = am.getDbDSL().select(T_IMPEDIMENTO_PARTE.COD_PARTE)
                .from(T_IMPEDIMENTO_PARTE)
                .where(T_IMPEDIMENTO_PARTE.COD_MAGISTRADO.equal(am.getMagistrado().getCodMagistrado()))
                .fetch().intoSet(T_IMPEDIMENTO_PARTE.COD_PARTE);
        am.setImpedimentosPartes(impedimentos);
    }
}
