package br.unb.sma.agents.gui;

import br.unb.sma.agents.AP;

import javax.swing.*;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public class APview {
    AP agent;
    private JPanel form;
    private JLabel processo;
    private JLabel qtdProcessosLabel;
    private JPanel processoPane;
    private JLabel qtdProcessos;

    public APview(AP agent) {
        this.agent = agent;
    }

    public JPanel getForm() {
        return form;
    }

    public void setProcesso(String processoNum) {
        processo.setText(processoNum);
    }

    public void setQtdProcessosFila(Integer qtd) {
        qtdProcessos.setText(String.valueOf(qtd));
    }

}
