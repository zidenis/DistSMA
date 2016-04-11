package br.unb.sma.agents.gui;

import br.unb.sma.agents.AD;

import javax.swing.*;

/**
 * Created by zidenis.
 * 06-04-2016
 */
public class ADview {
    private JButton distribuirBtn;
    private JPanel panel;

    private JPanel form;
    private AD agent;

    public ADview(AD agent) {
        this.agent = agent;
        distribuirBtn.addActionListener(event -> distribuir());
    }

    public JPanel getForm() {
        return form;
    }

    private void distribuir() {

    }
}
