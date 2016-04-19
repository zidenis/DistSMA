package br.unb.sma.entities;

import br.unb.sma.agents.SMAgent;
import br.unb.sma.database.tables.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static br.unb.sma.database.Tables.*;

/**
 * Created by zidenis.
 * 11-04-2016
 */
public class ProcessoCompleto implements Serializable {

    private Processo processo;
    private transient SMAgent agent;

    private long codProc;
    private List<Parte> partes;
    private List<Advogado> advogados;
    private List<Procurador> procuradores;
    private FaseProcessual faseAtual;
    private FaseProcessual faseAnterior;
    private List<FaseProcessual> fasesProcRel;

    public ProcessoCompleto(Processo processo, SMAgent agent) {
        this.processo = processo;
        codProc = processo.getCodProcesso();
        this.agent = agent;
        partes();
        advogados();
        procuradores();
        fases();
        processosRelacionados();
    }

    /* obter advogados do processo
    SELECT distinct t2.num_advogado, nom_advogado, num_oab, sig_uf_oab
    FROM t_processo_parte_advogado t1
    JOIN t_advogado t2
    ON t1.num_advogado = t2.num_advogado
    WHERE cod_processo = 200345766
    */
    private void advogados() {
        TAdvogado adv = T_ADVOGADO.as("adv");
        TProcessoParteAdvogado ppa = T_PROCESSO_PARTE_ADVOGADO.as("ppa");
        advogados = agent.getDbDSL()
                .selectDistinct(adv.NUM_ADVOGADO, adv.NOM_ADVOGADO, adv.NUM_OAB, adv.SIG_UF_OAB)
                .from(ppa)
                .join(adv)
                .on(adv.NUM_ADVOGADO.equal(ppa.NUM_ADVOGADO))
                .where(ppa.COD_PROCESSO.equal(codProc))
                .fetchInto(Advogado.class);
    }

    private void fases() {
        TFaseProcessual fp = T_FASE_PROCESSUAL.as("fp");
        /* obter fase atual do processo
        SELECT *
          FROM t_fase_processual
         WHERE cod_processo = 200345766
      ORDER BY dta_inicio_fase DESC
         LIMIT 1
         */
        faseAtual = agent.getDbDSL()
                .select()
                .from(fp)
                .where(fp.COD_PROCESSO.equal(codProc))
                .orderBy(fp.DTA_INICIO_FASE.desc())
                .limit(1)
                .fetchOneInto(FaseProcessual.class);
        /* obter fase anterior do processo
        SELECT *
          FROM t_fase_processual
         WHERE cod_processo = 200345766
           AND dta_termino_fase IS NOT NULL
      ORDER BY dta_inicio_fase DESC
         LIMIT 1
         */
        faseAnterior = agent.getDbDSL()
                .select()
                .from(fp)
                .where(fp.COD_PROCESSO.equal(codProc))
                .and(fp.DTA_TERMINO_FASE.isNotNull())
                .orderBy(fp.DTA_INICIO_FASE.desc())
                .limit(1)
                .fetchOneInto(FaseProcessual.class);
    }

    /* obter partes do processo
    SELECT DISTINCT t2.cod_parte, t2.nom_parte, t2.tip_parte, t2.num_cnpj, t2.num_cpf
      FROM t_processo_parte t1
      JOIN t_parte t2
        ON t1.cod_parte = t2.cod_parte
     WHERE cod_processo = 200345766;
     */
    private void partes() {
        TParte prt = T_PARTE.as("prt");
        TProcessoParte pp = T_PROCESSO_PARTE.as("pp");
        partes = agent.getDbDSL()
                .selectDistinct(prt.COD_PARTE, prt.NOM_PARTE, prt.TIP_PARTE, prt.NUM_CNPJ, prt.NUM_CPF)
                .from(pp)
                .join(prt)
                .on(pp.COD_PARTE.equal(prt.COD_PARTE))
                .where(pp.COD_PROCESSO.equal(codProc))
                .fetchInto(Parte.class);
    }

    /* obter procuradores do processo
    SELECT DISTINCT t2.num_procurador, t2.nom_procurador, t2.nom_procuradoria
      FROM t_processo_parte_procurador t1
      JOIN t_procurador t2
        ON t1.num_procurador = t2.num_procurador;
     */
    private void procuradores() {
        TProcurador prc = T_PROCURADOR.as("prc");
        TProcessoParteProcurador pp = T_PROCESSO_PARTE_PROCURADOR.as("pp");
        procuradores = agent.getDbDSL()
                .selectDistinct(prc.NUM_PROCURADOR, prc.NOM_PROCURADOR, prc.NOM_PROCURADORIA)
                .from(pp)
                .join(prc)
                .on(pp.NUM_PROCURADOR.equal(prc.NUM_PROCURADOR))
                .where(pp.COD_PROCESSO.equal(codProc))
                .fetchInto(Procurador.class);
    }

    /* Informações de processo relacionado
    SELECT DISTINCT t1.cod_processo, t1.cod_processo_rel, t1.cod_tipo_relac
      FROM t_processo_relacionado t1
     WHERE cod_processo = 2013252998
        OR cod_processo_rel = 2013252998
     */
    private void processosRelacionados() {
        TProcessoRelacionado pr = T_PROCESSO_RELACIONADO.as("pr");
        List<ProcessoRelacionado> processosRelacionados = agent.getDbDSL()
                .selectDistinct()
                .from(pr)
                .where(pr.COD_PROCESSO.equal(codProc))
                .or(pr.COD_PROCESSO_REL.equal(codProc))
                .fetchInto(ProcessoRelacionado.class);
        if (processosRelacionados.size() > 0) {
            fasesProcRel = new ArrayList<>();
            for (ProcessoRelacionado procRel : processosRelacionados) {
                Long codProcRel;
                if (procRel.getCodProcesso() == codProc) {
                    codProcRel = procRel.getCodProcessoRel();
                } else {
                    codProcRel = procRel.getCodProcesso();
                }
                TFaseProcessual fp = T_FASE_PROCESSUAL.as("fp");
                List<FaseProcessual> faseAtualProcRel = agent.getDbDSL()
                        .select()
                        .from(fp)
                        .where(fp.COD_PROCESSO.equal(codProcRel))
                        .orderBy(fp.DTA_INICIO_FASE.desc())
                        .fetchInto(FaseProcessual.class);
                fasesProcRel.addAll(faseAtualProcRel);
            }
        }
    }

    @Override
    public String toString() {
        return "ProcessoCompleto{" +
                "processo=" + processo +
                ", agent=" + agent +
                ", codProc=" + codProc +
                ", partes=" + partes +
                ", advogados=" + advogados +
                ", procuradores=" + procuradores +
                ", faseAtual=" + faseAtual +
                ", faseAnterior=" + faseAnterior +
                ", fasesProcRel=" + fasesProcRel +
                '}';
    }

    public Processo getProcesso() {
        return processo;
    }

    public SMAgent getAgent() {
        return agent;
    }

    public List<Parte> getPartes() {
        return partes;
    }

    public List<Advogado> getAdvogados() {
        return advogados;
    }

    public List<Procurador> getProcuradores() {
        return procuradores;
    }

    public FaseProcessual getFaseAtual() {
        return faseAtual;
    }

    public FaseProcessual getFaseAnterior() {
        return faseAnterior;
    }

    public List<FaseProcessual> getFasesProcRel() {
        return fasesProcRel;
    }

}
