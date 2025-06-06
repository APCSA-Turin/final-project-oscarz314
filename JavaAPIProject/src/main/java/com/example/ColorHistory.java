package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ColorHistory extends JFrame {
    JFrame frame = new JFrame();
    Canvas canvas;
    private int currentIndex = 0;
    private int maxIndex = 5;
    private JButton[] buttonGroup;

    public ColorHistory(Canvas canvas){
        frame.setSize(new Dimension(260, 70));
        buttonGroup = new JButton[maxIndex];

        //add buttons
        for(int i = 0; i < maxIndex; i++){
            JButton temp = new JButton();
            buttonGroup[i] = temp;
            temp.setOpaque(true);
            temp.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    canvas.setColor(temp.getBackground());
                }
            });
            temp.setBounds(50 * i,0, 50, 35);

            frame.add(temp);
        }

        //frame
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - frame.getWidth();
        int y = (int) rect.getMaxY() - frame.getHeight();
        frame.setLocation(x, y - 25);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setFocusable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setAlwaysOnTop(true);
        frame.setTitle("Color Palette History");
        this.canvas = canvas;
    }

    public void addColor(Color color){
            if(currentIndex >= maxIndex){
                currentIndex = 0;
            }

            //no repeat colors
            for(int i = 0; i < maxIndex; i++){
                if(color == buttonGroup[i].getBackground()){
                    return;
                }
            }

            buttonGroup[currentIndex].setBackground(color);
            currentIndex++;
    }

    public void closeHistory(){
        frame.dispose();
    }

    public Color averageColor(){
        int colorTotal = 0;
        int redSum = 0;
        int blueSum = 0;
        int greenSum = 0;

        for(int i = 0; i < maxIndex; i++){
            if(buttonGroup[i] != null){
                redSum += buttonGroup[i].getBackground().getRed();
                blueSum += buttonGroup[i].getBackground().getBlue();
                greenSum += buttonGroup[i].getBackground().getGreen();
                colorTotal++;
            }
        }
        return new Color(redSum/colorTotal, blueSum/colorTotal, greenSum/colorTotal);
    }

}
