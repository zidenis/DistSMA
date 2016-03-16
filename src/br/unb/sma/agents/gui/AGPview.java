package br.unb.sma.agents.gui;

import br.unb.sma.agents.AD;
import br.unb.sma.agents.AGP;
import br.unb.sma.agents.AM;
import br.unb.sma.agents.AP;

import javax.swing.*;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AGPview {
    final AGP agent;
    private JList listAP;
    private JList listAM;
    private JList listAD;
    private JButton ativarAP;
    private JButton ativarAD;
    private JButton ativarAM;
    private JButton desativarAP;
    private JButton desativarAD;
    private JButton desativarAM;
    private JPanel form;
    private JScrollPane paneAP;
    private JScrollPane paneAM;
    private JScrollPane paneAD;
    private DefaultListModel<AP> listAPModel;
    private DefaultListModel<AD> listADModel;
    private DefaultListModel<AM> listAMModel;

    public AGPview(AGP agent) {
        this.agent = agent;
        listAPModel = new DefaultListModel<>();
        listAP.setModel(listAPModel);
        listADModel = new DefaultListModel<>();
        listAD.setModel(listADModel);
        listAMModel = new DefaultListModel<>();
        listAM.setModel(listAMModel);
    }

    public JPanel getForm() {
        return form;
    }

    public DefaultListModel<AP> getListAPModel() {
        return listAPModel;
    }

    public DefaultListModel<AD> getListADModel() {
        return listADModel;
    }

    public DefaultListModel<AM> getListAMModel() {
        return listAMModel;
    }
}
