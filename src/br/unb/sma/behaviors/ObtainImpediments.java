package br.unb.sma.behaviors;

import br.unb.sma.agents.AM;
import jade.core.behaviours.OneShotBehaviour;

import java.util.Set;

import static br.unb.sma.database.Tables.*;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class ObtainImpediments extends OneShotBehaviour {

    AM am;

    public ObtainImpediments(AM agent) {
        super(agent);
        this.am = agent;
    }

    @Override
    public void action() {
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
        /* Obter a relação de impedimentos quanto a processos para um determinado magistrado
        SELECT cod_parte
          FROM t_impedimento_parte
         WHERE cod_magistrado = ?cod_magistrado?
         */
        Set<Integer> impedimentosAdv = am.getDbDSL().select(T_IMPEDIMENTO_ADVOGADO.NUM_ADVOGADO)
                .from(T_IMPEDIMENTO_ADVOGADO)
                .where(T_IMPEDIMENTO_ADVOGADO.COD_MAGISTRADO.equal(am.getMagistrado().getCodMagistrado()))
                .fetch().intoSet(T_IMPEDIMENTO_ADVOGADO.NUM_ADVOGADO);
        am.setImpedimentosAdvogados(impedimentosAdv);
        /* Obter a relação de impedimentos quanto a processos para um determinado magistrado
        SELECT cod_parte
          FROM t_impedimento_parte
         WHERE cod_magistrado = ?cod_magistrado?
         */
        Set<Long> impedimentosParte = am.getDbDSL().select(T_IMPEDIMENTO_PARTE.COD_PARTE)
                .from(T_IMPEDIMENTO_PARTE)
                .where(T_IMPEDIMENTO_PARTE.COD_MAGISTRADO.equal(am.getMagistrado().getCodMagistrado()))
                .fetch().intoSet(T_IMPEDIMENTO_PARTE.COD_PARTE);
        am.setImpedimentosPartes(impedimentosParte);

    }
}
