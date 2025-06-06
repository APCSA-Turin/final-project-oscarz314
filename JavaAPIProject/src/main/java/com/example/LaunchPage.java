package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LaunchPage extends JFrame implements ActionListener {
    JFrame frame = new JFrame();
    int pageHorizontal = 600;
    int pageVertical = 600;
    JLabel title = new JLabel("Welcome to Quote Painter");
    JLabel options = new JLabel("Please select a challenge");
    JButton button = new JButton("Continue to selection page");

    LaunchPage(){
        // Title
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setBounds(pageHorizontal / 2 - (500 / 4), 0, 500 , 50);

        // Explanation
        JLabel explanation = new JLabel("Challenge yourself to paint quotes with a time constraint!");
        explanation.setBounds(pageHorizontal / 2 - (500 / 3), (pageVertical / 2) - 100, 500 , 50);
        frame.add(explanation);

        //Option
        options.setFont(new Font("Helvetica", Font.PLAIN, 15));
        options.setBounds(pageHorizontal / 2 - (500 / 3) - 20, (pageVertical / 2), 500 , 50);

        //Button
        button.setBounds(pageHorizontal / 2, (pageVertical / 2), 200, 50);
        button.setFocusable(false);
        button.addActionListener(this);

        //Frame
        frame.setSize(pageHorizontal , pageVertical);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.add(title);
        frame.add(options);
        frame.add(button);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            frame.dispose();
            SelectionPage page = new SelectionPage();
        }
    }
}
