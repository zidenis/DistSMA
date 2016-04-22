package br.unb.sma.agents.gui;

import br.unb.sma.agents.AM;

import javax.swing.*;

/**
 * Created by zidenis.
 * 19-03-2016
 */
public class AMview implements AgentView {
    private JLabel composicaoOJlabel;
    private JTextArea composicaoOJ;
    private JLabel qtdProcImpedLabel;
    private JLabel qtdProcImped;
    private JLabel qtdParteImpedLabel;
    private JLabel qtdParteImped;
    private JLabel qtdAdvImpedLabel;
    private JLabel qtdAdvImped;
    private JPanel form;
    private AM agent;

    public AMview(AM agent) {
        this.agent = agent;
    }

    public JPanel getForm() {
        return form;
    }

    public void setComposicaoOJ(String composicaoOJ) {
        this.composicaoOJ.setText(composicaoOJ);
    }

    public void setQtdProcImped(String qtdProcImped) {
        this.qtdProcImped.setText(qtdProcImped);
    }

    public void setQtdParteImped(String qtdParteImped) {
        this.qtdParteImped.setText(qtdParteImped);
    }

    public void setQtdAdvImped(String qtdAdvImped) {
        this.qtdAdvImped.setText(qtdAdvImped);
    }

}
