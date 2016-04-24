/**
 * This class is generated by jOOQ
 */
package br.unb.sma.entities;


import javax.annotation.Generated;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * informações com detalhes acerca de distribuições realizadas
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.7.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class InfoDistribuicao implements Serializable {

    private static final long serialVersionUID = -1420290738;

    private Integer seqInformacao;
    private Timestamp dtaInformacao;
    private String codAgente;
    private String tipInformacao;
    private Integer seqDistribuicao;
    private String txtInformacao;
    private String txtDetalhes;

    public InfoDistribuicao() {
    }

    public InfoDistribuicao(InfoDistribuicao value) {
        this.seqInformacao = value.seqInformacao;
        this.dtaInformacao = value.dtaInformacao;
        this.codAgente = value.codAgente;
        this.tipInformacao = value.tipInformacao;
        this.seqDistribuicao = value.seqDistribuicao;
        this.txtInformacao = value.txtInformacao;
        this.txtDetalhes = value.txtDetalhes;
    }

    public InfoDistribuicao(
            Integer seqInformacao,
            Timestamp dtaInformacao,
            String codAgente,
            String tipInformacao,
            Integer seqDistribuicao,
            String txtInformacao,
            String txtDetalhes
    ) {
        this.seqInformacao = seqInformacao;
        this.dtaInformacao = dtaInformacao;
        this.codAgente = codAgente;
        this.tipInformacao = tipInformacao;
        this.seqDistribuicao = seqDistribuicao;
        this.txtInformacao = txtInformacao;
        this.txtDetalhes = txtDetalhes;
    }

    public Integer getSeqInformacao() {
        return this.seqInformacao;
    }

    public void setSeqInformacao(Integer seqInformacao) {
        this.seqInformacao = seqInformacao;
    }

    public Timestamp getDtaInformacao() {
        return this.dtaInformacao;
    }

    public void setDtaInformacao(Timestamp dtaInformacao) {
        this.dtaInformacao = dtaInformacao;
    }

    public String getCodAgente() {
        return this.codAgente;
    }

    public void setCodAgente(String codAgente) {
        this.codAgente = codAgente;
    }

    public String getTipInformacao() {
        return this.tipInformacao;
    }

    public void setTipInformacao(String tipInformacao) {
        this.tipInformacao = tipInformacao;
    }

    public Integer getSeqDistribuicao() {
        return this.seqDistribuicao;
    }

    public void setSeqDistribuicao(Integer seqDistribuicao) {
        this.seqDistribuicao = seqDistribuicao;
    }

    public String getTxtInformacao() {
        return this.txtInformacao;
    }

    public void setTxtInformacao(String txtInformacao) {
        this.txtInformacao = txtInformacao;
    }

    public String getTxtDetalhes() {
        return this.txtDetalhes;
    }

    public void setTxtDetalhes(String txtDetalhes) {
        this.txtDetalhes = txtDetalhes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("InfoDistribuicao (");

        sb.append(seqInformacao);
        sb.append(", ").append(dtaInformacao);
        sb.append(", ").append(codAgente);
        sb.append(", ").append(tipInformacao);
        sb.append(", ").append(seqDistribuicao);
        sb.append(", ").append(txtInformacao);
        sb.append(", ").append(txtDetalhes);

        sb.append(")");
        return sb.toString();
    }
}
