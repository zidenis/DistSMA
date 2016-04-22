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
    private AD agent;

    public ADview(AD agent) {
        this.agent = agent;
        distribuirBtn.addActionListener(event -> distribuir());
        playButton.addActionListener(event -> playButtonPress());
    }

    public JPanel getForm() {
        return form;
    }

    private void distribuir() {
        agent.requestLawsuit();
    }

    private void playButtonPress() {
        if (agent.isPlaying()) {
            agent.setPlaying(false);
            playButton.setText("Play");
        } else {
            agent.setPlaying(true);
            playButton.setText("Stop");
            distribuir();
//            startContinuousPlay();
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
