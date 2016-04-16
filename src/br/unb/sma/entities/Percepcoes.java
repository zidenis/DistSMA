package br.unb.sma.entities;

import java.util.Map;
import java.util.Set;

/**
 * Created by zidenis.
 * 15-04-2016
 */
public class Percepcoes {
    private Map<Long, Set<String>> magistradosImpedidos; // "Código do Processo" -> {Magistrados}
    private Map<Long, Set<String>> magistradosCompetentes; // "Código do Processo" -> {Magistrados}

    public Percepcoes(Map<Long, Set<String>> magistradosImpedidos, Map<Long, Set<String>> magistradosCompetentes) {
        this.magistradosImpedidos = magistradosImpedidos;
        this.magistradosCompetentes = magistradosCompetentes;
    }

    public Map<Long, Set<String>> getMagistradosImpedidos() {
        return magistradosImpedidos;
    }

    public void setMagistradosImpedidos(Map<Long, Set<String>> magistradosImpedidos) {
        this.magistradosImpedidos = magistradosImpedidos;
    }

    public Map<Long, Set<String>> getMagistradosCompetentes() {
        return magistradosCompetentes;
    }

    public void setMagistradosCompetentes(Map<Long, Set<String>> magistradosCompetentes) {
        this.magistradosCompetentes = magistradosCompetentes;
    }

    @Override
    public String toString() {
        return "Percepcoes{" +
                ", magistradosImpedidos=" + magistradosImpedidos +
                ", magistradosCompetentes=" + magistradosCompetentes +
                '}';
    }
}
