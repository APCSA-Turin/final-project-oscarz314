package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerBar extends JProgressBar {
    private JProgressBar barTimer = new JProgressBar();
    Timer timer;
    int startTime;

    public TimerBar(int startTime){
        this.startTime = startTime;
        this.setPreferredSize(new Dimension(200, 40));
        barTimer.setBounds(0, 0 , 200, 40);
        barTimer.setMaximum(startTime);
        barTimer.setValue(startTime);
        this.add(barTimer);
        countDown();
        timer.start();
    }

    public void countDown(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     barTimer.setValue(barTimer.getValue() - 1);
                     if(barTimer.getValue() == 0){
                         barTimer.setValue(-1);
                         JOptionPane.showMessageDialog(null, "Pens down! challenge ended", "title", JOptionPane.INFORMATION_MESSAGE);
                     }
            }
        });
    }
}
