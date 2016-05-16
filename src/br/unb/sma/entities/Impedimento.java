package br.unb.sma.entities;

/**
 * Created by zidenis.
 * 16-04-2016
 */
public class Impedimento {
    private String codMagistrado;
    private String tipoImpedimento;
    private String detalhamento;

    public Impedimento(String codMagistrado, String tipoImpedimento, String detalhamento) {
        this.codMagistrado = codMagistrado;
        this.tipoImpedimento = tipoImpedimento;
        this.detalhamento = detalhamento;
    }

    public String getCodMagistrado() {
        return codMagistrado;
    }

    public void setCodMagistrado(String codMagistrado) {
        this.codMagistrado = codMagistrado;
    }

    public String getTipoImpedimento() {
        return tipoImpedimento;
    }

    public void setTipoImpedimento(String tipoImpedimento) {
        this.tipoImpedimento = tipoImpedimento;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"codMagistrado\":")
                .append(codMagistrado == null ? "null" : "\"" + codMagistrado + "\"")
                .append(", \"impedimentosList\" : [")
                .append(detalhamento.substring(1)).append("}");
        return sb.toString();
    }
}
