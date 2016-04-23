package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import br.unb.sma.entities.Competencia;
import jade.core.behaviours.OneShotBehaviour;
import org.jooq.Record;
import org.jooq.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static br.unb.sma.database.Tables.T_COMPETENCIA;

/**
 * Created by zidenis.
 * 06-04-2016
 */
public class ObtainOJCompetencies extends OneShotBehaviour {

    private AD ad;
    private HashMap<String, Set<String>> competencias;

    public ObtainOJCompetencies(AD agent) {
        super(agent);
        this.ad = agent;
        competencias = new HashMap<>();
    }

    @Override
    public void action() {
        /* Obter Órgãos Judicantes de um determinado Magistrado
            SELECT *
            FROM t_competencia
         */
        Result<Record> result;
        List<Competencia> competencias = ad.getDbDSL()
                .select()
                .from(T_COMPETENCIA)
                .fetchInto(Competencia.class);
        ad.setJudginOrgansCompetencies(competencias);
    }
}
