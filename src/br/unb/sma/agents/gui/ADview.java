package br.unb.sma.agents.gui;

import br.unb.sma.agents.AD;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zidenis.
 * 06-04-2016
 */
public class ADview implements AgentView {
    private JButton distribuirBtn;
    private JPanel panel;

    private JPanel form;
    private JButton playButton;
    private AD ad;

    public ADview(AD ad) {
        this.ad = ad;
        distribuirBtn.addActionListener(event -> distribuir());
        playButton.addActionListener(event -> playButtonPress());
    }

    public JPanel getForm() {
        return form;
    }

    private void distribuir() {
        ad.requestLawsuit();
    }

    private void playButtonPress() {
        if (ad.isRunning()) {
            ad.setRunning(false);
            playButton.setText("Play");
        } else {
            ad.setRunning(true);
            playButton.setText("Stop");
            distribuir();
        }
    }

    public void setPlayButtonText(String text) {
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> playButton.setText(text));
            }
        }, 200);
    }

}
