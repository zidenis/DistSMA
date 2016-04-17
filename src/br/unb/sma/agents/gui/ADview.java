package br.unb.sma.agents.gui;

import br.unb.sma.agents.AD;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zidenis.
 * 06-04-2016
 */
public class ADview {
    private JButton distribuirBtn;
    private JPanel panel;

    private JPanel form;
    private JButton playButton;
    private AD agent;
    private boolean playing = false;
    private int delay = 3000;

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
        if (playing) {
            playing = false;
            playButton.setText("Play");
        } else {
            playing = true;
            playButton.setText("Stop");
            startContinuousPlay();
        }
    }

    private void startContinuousPlay() {
        if (playing) {
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    distribuir();
                    startContinuousPlay();
                }
            }, delay);
        }
    }

}
