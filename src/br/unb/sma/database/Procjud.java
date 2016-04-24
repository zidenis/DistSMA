/**
 * This class is generated by jOOQ
 */
package br.unb.sma.database;


import br.unb.sma.database.tables.*;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Procjud extends SchemaImpl {

    /**
     * The reference instance of <code>procjud</code>
     */
    public static final Procjud PROCJUD = new Procjud();
    private static final long serialVersionUID = -1897786336;

    /**
     * No further instances allowed
     */
    private Procjud() {
        super("procjud");
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        List result = new ArrayList();
        result.addAll(getSequences0());
        return result;
    }

    private final List<Sequence<?>> getSequences0() {
        return Arrays.<Sequence<?>>asList(
                Sequences.T_INFO_DISTRIBUICAO_SEQ_INFORMACAO_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        List result = new ArrayList();
        result.addAll(getTables0());
        return result;
    }

    private final List<Table<?>> getTables0() {
        return Arrays.<Table<?>>asList(
                TAdvogado.T_ADVOGADO,
                TClasseProcessual.T_CLASSE_PROCESSUAL,
                TCompetencia.T_COMPETENCIA,
                TComposicaoOj.T_COMPOSICAO_OJ,
                TDenParteFaseProc.T_DEN_PARTE_FASE_PROC,
                TDistribuidor.T_DISTRIBUIDOR,
                TFaseProcessual.T_FASE_PROCESSUAL,
                THistDistribuicao.T_HIST_DISTRIBUICAO,
                TImpedimentoAdvogado.T_IMPEDIMENTO_ADVOGADO,
                TImpedimentoParte.T_IMPEDIMENTO_PARTE,
                TImpedimentoProcesso.T_IMPEDIMENTO_PROCESSO,
                TInfoDistribuicao.T_INFO_DISTRIBUICAO,
                TMagistrado.T_MAGISTRADO,
                TMotivoRedistribuicao.T_MOTIVO_REDISTRIBUICAO,
                TOrgaoJudicante.T_ORGAO_JUDICANTE,
                TParte.T_PARTE,
                TProcesso.T_PROCESSO,
                TProcessoParte.T_PROCESSO_PARTE,
                TProcessoParteAdvogado.T_PROCESSO_PARTE_ADVOGADO,
                TProcessoParteProcurador.T_PROCESSO_PARTE_PROCURADOR,
                TProcessoRelacionado.T_PROCESSO_RELACIONADO,
                TProcurador.T_PROCURADOR,
                TProtocolo.T_PROTOCOLO,
                TTipoDist.T_TIPO_DIST,
                TTipoRelacionamento.T_TIPO_RELACIONAMENTO);
    }
}
