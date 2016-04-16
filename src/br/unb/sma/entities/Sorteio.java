package br.unb.sma.entities;

import java.util.List;

/**
 * Created by zidenis.
 * 15-04-2016
 */
public class Sorteio {

    private List<String> listSigOJsCompetentes;
    private String sigOJSorteado;
    private List<String> listCodMagsOJSorteado;
    private String codMagistradoSorteado;

    public Sorteio(List<String> listSigOJsCompetentes, String sigOJSorteado, List<String> listCodMagsOJSorteado, String codMagistradoSorteado) {
        this.listSigOJsCompetentes = listSigOJsCompetentes;
        this.sigOJSorteado = sigOJSorteado;
        this.listCodMagsOJSorteado = listCodMagsOJSorteado;
        this.codMagistradoSorteado = codMagistradoSorteado;
    }

    public List<String> getListSigOJsCompetentes() {
        return listSigOJsCompetentes;
    }

    public void setListSigOJsCompetentes(List<String> listSigOJsCompetentes) {
        this.listSigOJsCompetentes = listSigOJsCompetentes;
    }

    public String getSigOJSorteado() {
        return sigOJSorteado;
    }

    public void setSigOJSorteado(String sigOJSorteado) {
        this.sigOJSorteado = sigOJSorteado;
    }

    public List<String> getListCodMagsOJSorteado() {
        return listCodMagsOJSorteado;
    }

    public void setListCodMagsOJSorteado(List<String> listCodMagsOJSorteado) {
        this.listCodMagsOJSorteado = listCodMagsOJSorteado;
    }

    public String getCodMagistradoSorteado() {
        return codMagistradoSorteado;
    }

    public void setCodMagistradoSorteado(String codMagistradoSorteado) {
        this.codMagistradoSorteado = codMagistradoSorteado;
    }

    @Override
    public String toString() {
        return "Sorteio{" +
                "listSigOJsCompetentes=" + listSigOJsCompetentes +
                ", sigOJSorteado='" + sigOJSorteado + '\'' +
                ", listCodMagsOJSorteado=" + listCodMagsOJSorteado +
                ", codMagistradoSorteado='" + codMagistradoSorteado + '\'' +
                '}';
    }
}
