package br.unb.sma.agents.gui;

import javax.swing.*;

/**
 * Created by zidenis.
 * 17-03-2016
 */
public class MyListModel<T> extends DefaultListModel<T> {
    public void update() {
        fireContentsChanged(this, 0, 0);
    }
}
