package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerBar extends JProgressBar {
    private JProgressBar barTimer = new JProgressBar();
    private Timer timer;
    private int startTime;
    private boolean ended;
    private PaintGUI paintGUI;

    public TimerBar(int startTime, PaintGUI paintGUI){
        this.startTime = startTime;
        ended = false;
        this.setPreferredSize(new Dimension(500, 15));
        barTimer.setBounds(0, 0 , 500, 15);
        barTimer.setMaximum(startTime);
        barTimer.setValue(startTime);
        this.add(barTimer);
        this.paintGUI = paintGUI;
        countDown();
        timer.start();
    }

    public void countDown(){
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                     barTimer.setValue(barTimer.getValue() - 1);
                     if(barTimer.getValue() == 0 && ended == false){
                         ended = true;
                         Color averageColor = paintGUI.getCanvas().returnColorHistory().averageColor();
                         JOptionPane.showMessageDialog(null, "Pens down! challenge ended. Fun fa" +
                                 "ct your average color was Red: " + averageColor.getRed() + ", Blue: "
                                 + averageColor.getBlue() + ", Green: " + averageColor.getGreen(), "title",
                                 JOptionPane.INFORMATION_MESSAGE);
                         paintGUI.savePanel(paintGUI.getCanvas());
                         paintGUI.getCanvas().returnColorHistory().closeHistory();
                         paintGUI.dispose();
                         LaunchPage launchPage = new LaunchPage();
                     }
            }
        });
    }
}
