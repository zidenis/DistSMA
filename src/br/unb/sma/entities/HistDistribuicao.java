/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * Historico de distribuicões realizadas
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class HistDistribuicao implements Serializable {

    private static final long serialVersionUID = -568231922;

    private String codDistribuidor;
    private Integer seqDistribuicao;
    private Long codProcesso;
    private String codTipoDist;
    private Timestamp dtaDistribuicao;
    private String codMagistrado;
    private String txtDistribuicao;
    private String sigOj;
    private String txtRegraAplicada;

    public HistDistribuicao() {
    }

    public HistDistribuicao(HistDistribuicao value) {
        this.codDistribuidor = value.codDistribuidor;
        this.seqDistribuicao = value.seqDistribuicao;
        this.codProcesso = value.codProcesso;
        this.codTipoDist = value.codTipoDist;
        this.dtaDistribuicao = value.dtaDistribuicao;
        this.codMagistrado = value.codMagistrado;
        this.txtDistribuicao = value.txtDistribuicao;
        this.sigOj = value.sigOj;
        this.txtRegraAplicada = value.txtRegraAplicada;
    }

    public HistDistribuicao(
            String codDistribuidor,
            Integer seqDistribuicao,
            Long codProcesso,
            String codTipoDist,
            Timestamp dtaDistribuicao,
            String codMagistrado,
            String txtDistribuicao,
            String sigOj,
            String txtRegraAplicada
    ) {
        this.codDistribuidor = codDistribuidor;
        this.seqDistribuicao = seqDistribuicao;
        this.codProcesso = codProcesso;
        this.codTipoDist = codTipoDist;
        this.dtaDistribuicao = dtaDistribuicao;
        this.codMagistrado = codMagistrado;
        this.txtDistribuicao = txtDistribuicao;
        this.sigOj = sigOj;
        this.txtRegraAplicada = txtRegraAplicada;
    }

    public String getCodDistribuidor() {
        return this.codDistribuidor;
    }

    public void setCodDistribuidor(String codDistribuidor) {
        this.codDistribuidor = codDistribuidor;
    }

    public Integer getSeqDistribuicao() {
        return this.seqDistribuicao;
    }

    public void setSeqDistribuicao(Integer seqDistribuicao) {
        this.seqDistribuicao = seqDistribuicao;
    }

    public Long getCodProcesso() {
        return this.codProcesso;
    }

    public void setCodProcesso(Long codProcesso) {
        this.codProcesso = codProcesso;
    }

    public String getCodTipoDist() {
        return this.codTipoDist;
    }

    public void setCodTipoDist(String codTipoDist) {
        this.codTipoDist = codTipoDist;
    }

    public Timestamp getDtaDistribuicao() {
        return this.dtaDistribuicao;
    }

    public void setDtaDistribuicao(Timestamp dtaDistribuicao) {
        this.dtaDistribuicao = dtaDistribuicao;
    }

    public String getCodMagistrado() {
        return this.codMagistrado;
    }

    public void setCodMagistrado(String codMagistrado) {
        this.codMagistrado = codMagistrado;
    }

    public String getTxtDistribuicao() {
        return this.txtDistribuicao;
    }

    public void setTxtDistribuicao(String txtDistribuicao) {
        this.txtDistribuicao = txtDistribuicao;
    }

    public String getSigOj() {
        return this.sigOj;
    }

    public void setSigOj(String sigOj) {
        this.sigOj = sigOj;
    }

    public String getTxtRegraAplicada() {
        return txtRegraAplicada;
    }

    public void setTxtRegraAplicada(String txtRegraAplicada) {
        this.txtRegraAplicada = txtRegraAplicada;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("HistDistribuicao (");

        sb.append(codDistribuidor);
        sb.append(", ").append(seqDistribuicao);
        sb.append(", ").append(codProcesso);
        sb.append(", ").append(codTipoDist);
        sb.append(", ").append(dtaDistribuicao);
        sb.append(", ").append(codMagistrado);
        sb.append(", ").append(txtRegraAplicada);
        sb.append(", ").append(txtDistribuicao);
        sb.append(", ").append(sigOj);

        sb.append(")");
        return sb.toString();
    }
}
