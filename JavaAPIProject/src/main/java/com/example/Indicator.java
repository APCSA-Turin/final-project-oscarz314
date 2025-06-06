package com.example;

import javax.swing.*;
import java.awt.*;

public class Indicator extends JPanel {
    public int strokeSize;
    private Color color;

    public Indicator(){
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setSize(new Dimension(100, 100));
    }

    public void updateIndicator(int strokeSize, Color color) {
        this.strokeSize = strokeSize;
        this.color = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(color);
        g2.fillRect(0, 0, strokeSize, strokeSize);
        g2.dispose();
    }
}
