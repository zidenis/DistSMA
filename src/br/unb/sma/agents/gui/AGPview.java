package br.unb.sma.agents.gui;

import br.unb.sma.agents.AGP;
import br.unb.sma.entities.Distribuidor;
import br.unb.sma.entities.Magistrado;
import br.unb.sma.entities.Protocolo;
import br.unb.sma.utils.Status;

import javax.swing.*;
import java.util.List;

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
    private JButton obterProc;
    private JButton distribuir;
    private JButton encerrarSMA;
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
        obterProc.addActionListener(e -> obterProcessos());
        encerrarSMA.addActionListener(e -> encerrarSMA());
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
            if (!protocolo.getStatus().equals(Status.ATIVADO)) {
                agent.activateAgent(protocolo);
            }
        }
    }

    private void desativarAPselecionado() {
        for (Protocolo protocolo : (List<Protocolo>) listAP.getSelectedValuesList()) {
            String status = protocolo.getStatus();
            if (status.equals(Status.ATIVADO)) {
                agent.deactivateAgent(protocolo);
            }
        }
    }

    private void obterProcessos() {
        if (!listAP.isSelectionEmpty()) {
            String input = (String) JOptionPane.showInputDialog(form, "Quantidade de Processos", "Informe", JOptionPane.QUESTION_MESSAGE, UIManager.getIcon("FileView.computerIcon"), null, "7");
            try {
                if (input.equals("")) return;
                Integer numProcesses = Integer.valueOf(input);
                for (Protocolo protocolo : (List<Protocolo>) listAP.getSelectedValuesList()) {
                    String status = protocolo.getStatus();
                    if (status.equals(Status.ATIVADO)) {
                        agent.reqObterProcessos(protocolo, numProcesses);
                    }
                }
            } catch (NumberFormatException e) {
                obterProcessos();
            } catch (NullPointerException e) {
            }
        }
    }

    private void ativarADselecionado() {
        for (Distribuidor distribuidor : (List<Distribuidor>) listAD.getSelectedValuesList()) {
            if (!distribuidor.getStatus().equals(Status.ATIVADO)) {
                agent.activateAgent(distribuidor);
            }
        }
    }

    private void desativarADselecionado() {
        for (Distribuidor distribuidor : (List<Distribuidor>) listAD.getSelectedValuesList()) {
            String status = distribuidor.getStatus();
            if (status.equals(Status.ATIVADO)) {
                agent.deactivateAgent(distribuidor);
            }
        }
    }

    private void ativarAMselecionado() {
        for (Magistrado magistrado : (List<Magistrado>) listAM.getSelectedValuesList()) {
            if (!magistrado.getStatus().equals(Status.ATIVADO)) {
                agent.activateAgent(magistrado);
            }
        }
    }

    private void desativarAMselecionado() {
        for (Magistrado magistrado : (List<Magistrado>) listAM.getSelectedValuesList()) {
            String status = magistrado.getStatus();
            if (status.equals(Status.ATIVADO)) {
                agent.deactivateAgent(magistrado);
            }
        }
    }

    private void encerrarSMA() {
        agent.encerrarSMA();
    }

    public void update() {
        listAPModel.update();
        listAMModel.update();
        listADModel.update();
    }
}