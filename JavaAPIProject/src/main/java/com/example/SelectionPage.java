package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectionPage extends JFrame implements ActionListener {
    JFrame frame = new JFrame();
    JRadioButton shortTime;
    JRadioButton midTime;
    JRadioButton longTime;
    JButton button;
    int time = 0;

    public SelectionPage(){
        //Buttons
        shortTime = new JRadioButton("1 minute");
        midTime = new JRadioButton("2 minutes");
        longTime = new JRadioButton("3 minutes");
        button = new JButton("Begin");
        shortTime.setFocusable(false);
        midTime.setFocusable(false);
        longTime.setFocusable(false);
        button.setFocusable(false);

        //Group
        ButtonGroup group = new ButtonGroup();
        group.add(shortTime);
        group.add(midTime);
        group.add(longTime);

        //Event Listener
        shortTime.addActionListener(this);
        midTime.addActionListener(this);
        longTime.addActionListener(this);
        button.addActionListener(this);

        //Frame
        frame.add(shortTime);
        frame.add(midTime);
        frame.add(longTime);
        frame.add(button);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Selector");
        frame.setLayout(new FlowLayout());
        frame.pack();
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == shortTime){
            time = 1;
        }
        else if(e.getSource() == midTime){
            time = 2;
        }
        else if(e.getSource() == longTime){
            time = 3;
        }

        if(e.getSource() == button){
            if (time != 0){
                frame.dispose();
                paint();
            }
        }
    }

    public void paint(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PaintGUI(time).setVisible(true);
            }
        });
    }
}


