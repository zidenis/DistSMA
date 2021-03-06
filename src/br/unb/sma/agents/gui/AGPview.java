package br.unb.sma.agents.gui;

import br.unb.sma.agents.AGP;
import br.unb.sma.entities.Distribuidor;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.entities.Protocolo;

import javax.swing.*;
import java.util.List;

/**
 * Created by zidenis.
 * 16-03-2016
 */
public class AGPview implements AgentView {
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
    private JButton encerrarSMA;
    private JButton iniciarTodosButton;
    private JCheckBox guiCheck;
    private MyListModel<Protocolo> listAPModel;
    private MyListModel<Distribuidor> listADModel;
    private MyListModel<Magistrado> listAMModel;

    public AGPview(AGP agent) {
        this.agent = agent;
        listAPModel = new MyListModel<>();
        listAP.setModel(listAPModel);
        listADModel = new MyListModel<>();
        listAD.setModel(listADModel);
        listAMModel = new MyListModel<>();
        listAM.setModel(listAMModel);
        ativarAP.addActionListener(e -> ativarAPselecionado());
        desativarAP.addActionListener(e -> desativarAPselecionado());
        ativarAD.addActionListener(e -> ativarADselecionado());
        desativarAD.addActionListener(e -> desativarADselecionado());
        ativarAM.addActionListener(e -> ativarAMselecionado());
        desativarAM.addActionListener(e -> desativarAMselecionado());
        encerrarSMA.addActionListener(e -> encerrarSMA());
        iniciarTodosButton.addActionListener(e -> iniciarTodos());
        guiCheck.setSelected(AGP.ENABLE_GUI);
        guiCheck.addActionListener(e -> guiCheckClicked());
    }

    private void guiCheckClicked() {
        AGP.ENABLE_GUI = !AGP.ENABLE_GUI;
        guiCheck.setSelected(AGP.ENABLE_GUI);
    }

    public JPanel getForm() {
        return form;
    }

    public MyListModel<Protocolo> getListAPModel() {
        return listAPModel;
    }

    public MyListModel<Distribuidor> getListADModel() {
        return listADModel;
    }

    public MyListModel<Magistrado> getListAMModel() {
        return listAMModel;
    }

    private void ativarAPselecionado() {
        for (Protocolo protocolo : (List<Protocolo>) listAP.getSelectedValuesList()) {
            if (!protocolo.getStatus().equals(AGP.STATUS_ENABLED)) {
                agent.activateAgent(protocolo);
            }
        }
    }

    private void desativarAPselecionado() {
        for (Protocolo protocolo : (List<Protocolo>) listAP.getSelectedValuesList()) {
            String status = protocolo.getStatus();
            if (status.equals(AGP.STATUS_ENABLED)) {
                agent.deactivateAgent(protocolo);
            }
        }
    }

    private void ativarADselecionado() {
        for (Distribuidor distribuidor : (List<Distribuidor>) listAD.getSelectedValuesList()) {
            if (!distribuidor.getStatus().equals(AGP.STATUS_ENABLED)) {
                agent.activateAgent(distribuidor);
            }
        }
    }

    private void desativarADselecionado() {
        for (Distribuidor distribuidor : (List<Distribuidor>) listAD.getSelectedValuesList()) {
            String status = distribuidor.getStatus();
            if (status.equals(AGP.STATUS_ENABLED)) {
                agent.deactivateAgent(distribuidor);
            }
        }
    }

    private void ativarAMselecionado() {
        for (Magistrado magistrado : (List<Magistrado>) listAM.getSelectedValuesList()) {
            if (!magistrado.getStatus().equals(AGP.STATUS_ENABLED)) {
                agent.activateAgent(magistrado);
            }
        }
    }

    private void desativarAMselecionado() {
        for (Magistrado magistrado : (List<Magistrado>) listAM.getSelectedValuesList()) {
            String status = magistrado.getStatus();
            if (status.equals(AGP.STATUS_ENABLED)) {
                agent.deactivateAgent(magistrado);
            }
        }
    }

    private void iniciarTodos() {
        listAP.setSelectionInterval(0, listAPModel.getSize() - 1);
        listAD.setSelectionInterval(0, listADModel.getSize() - 1);
        listAM.setSelectionInterval(0, listAMModel.getSize() - 1);
        ativarAPselecionado();
        ativarADselecionado();
        ativarAMselecionado();
    }

    private void encerrarSMA() {
        agent.shutdownSMA();
    }

    public void update() {
        listAPModel.update();
        listAMModel.update();
        listADModel.update();
    }

}