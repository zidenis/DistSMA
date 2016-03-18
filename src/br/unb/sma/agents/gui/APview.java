package br.unb.sma.agents.gui;

import br.unb.sma.agents.AP;
import br.unb.sma.entities.Processo;

import javax.swing.*;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public class APview {
    AP agent;
    private JPanel form;
    private JScrollPane paneProcessos;
    private JList listProcessos;
    private MyListModel<Processo> listProcessosModel;

    public APview(AP agent) {
        this.agent = agent;
        listProcessosModel = new MyListModel<Processo>();
        listProcessos.setModel(listProcessosModel);
    }

    public JPanel getForm() {
        return form;
    }

    public MyListModel<Processo> getListProcessosModel() {
        return listProcessosModel;
    }
}
