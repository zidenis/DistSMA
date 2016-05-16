package br.unb.sma.entities;

import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

/**
 * Created by zidenis.
 * 15-04-2016
 */
public class Sorteio {

    private List<String> listSigOJsCompetentes;
    private String sigOJSorteado;
    private List<String> listCodMagsOJSorteado;
    private String codMagistradoSorteado;
    private Set<String> magsImpedidos;

    public Sorteio(List<String> listSigOJsCompetentes, String sigOJSorteado, List<String> listCodMagsOJSorteado, Set<String> magsImpedidos, String codMagistradoSorteado) {
        this.listSigOJsCompetentes = listSigOJsCompetentes;
        this.sigOJSorteado = sigOJSorteado;
        this.listCodMagsOJSorteado = listCodMagsOJSorteado;
        this.codMagistradoSorteado = codMagistradoSorteado;
        this.magsImpedidos = magsImpedidos;
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

    public Set<String> getMagsImpedidos() {
        return magsImpedidos;
    }

    public void setMagsImpedidos(Set<String> magsImpedidos) {
        this.magsImpedidos = magsImpedidos;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
