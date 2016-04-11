package br.unb.sma.behaviors;

import br.unb.sma.agents.AD;
import jade.core.behaviours.OneShotBehaviour;
import org.jooq.Record;
import org.jooq.Result;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.unb.sma.database.Tables.T_COMPETENCIA;

/**
 * Created by zidenis.
 * 06-04-2016
 */
public class ObtainOJCompetencies extends OneShotBehaviour {

    private AD ad;
    private HashMap<String, List<String>> competencias;

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
        result = ad.getDbDSL().select().from(T_COMPETENCIA).fetch();
        for (Record r : result) {
            String sigClasse = r.getValue(T_COMPETENCIA.SIG_CLASSE);
            String sigOJ = r.getValue(T_COMPETENCIA.SIG_OJ);
            if (competencias.get(sigClasse) == null) {
                List<String> ojs = new ArrayList<>();
                ojs.add(sigOJ);
                competencias.put(sigClasse, ojs);
            } else {
                competencias.get(sigClasse).add(sigOJ);
            }
        }
        ad.setCompetencias(competencias);
    }
}
