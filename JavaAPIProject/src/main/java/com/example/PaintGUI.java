package com.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class PaintGUI extends JFrame {
    JFrame frame = new JFrame();
    private int startTime;
    MenuBar menuBar;
    Menu fileMenu;
    MenuItem saveItem;
    MenuItem importItem;
    private Canvas canvas;

    PaintGUI(int time){

        //Menu
        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        saveItem = new MenuItem("Save");
        importItem = new MenuItem("Load");
        fileMenu.add(saveItem);
        fileMenu.add(importItem);

        menuBar.add(fileMenu);
        this.setMenuBar(menuBar);

        //Frame
        this.setPreferredSize(new Dimension(1800, 1000));
        this.pack();
        this.setFocusable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startTime = time * 60;
        addGUI();
    }

    public void addGUI(){
        //JPanel
        JPanel canvasPanel = new JPanel();
        SpringLayout springLayout = new SpringLayout();
        canvasPanel.setLayout(springLayout);

        //Canvas
        canvas = new Canvas(1500, 800);
        canvasPanel.add(canvas);
        springLayout.putConstraint(SpringLayout.NORTH, canvas, 50, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, canvas, 0, SpringLayout.HORIZONTAL_CENTER, canvasPanel);

        //Color picker
        JButton chooseColorButton = new JButton("Pick Color");
        chooseColorButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Color c = JColorChooser.showDialog(null, "Pick a color", Color.BLACK);
                chooseColorButton.setBackground(c);
                canvas.setColor(c);
            }
        });
        canvasPanel.add(chooseColorButton);
        springLayout.putConstraint(SpringLayout.NORTH, chooseColorButton, 10, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, chooseColorButton, 10, SpringLayout.WEST, canvasPanel);

        //Reset canvas
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.resetCanvas();
            }
        });
        canvasPanel.add(resetButton);

        springLayout.putConstraint(SpringLayout.NORTH, resetButton, 10, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, resetButton, 150, SpringLayout.WEST, canvasPanel);

        //Eraser
        JCheckBox eraserToggle = new JCheckBox("Eraser");
        eraserToggle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (eraserToggle.isSelected()){
                    canvas.IsEraser(true);
                }
                else{
                    canvas.IsEraser(false);
                }
            }
        });
        canvasPanel.add(eraserToggle);
        springLayout.putConstraint(SpringLayout.NORTH, eraserToggle, 10, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, eraserToggle, 250, SpringLayout.WEST, canvasPanel);

        //Timer
        TimerBar barTimer = new TimerBar(startTime, this);

        canvasPanel.add(barTimer);
        springLayout.putConstraint(SpringLayout.NORTH, barTimer, 25, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, barTimer, 0, SpringLayout.HORIZONTAL_CENTER, canvasPanel);

        //Quote
        Quote quote = new Quote();
        canvasPanel.add(quote);
        springLayout.putConstraint(SpringLayout.NORTH, quote, 0, SpringLayout.NORTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, quote, 0, SpringLayout.HORIZONTAL_CENTER, canvasPanel);

        //Brush size
        JSlider brushSlider = new JSlider(0,1, 150,5);
        brushSlider.setPreferredSize(new Dimension(1000, 50));
        brushSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                canvas.setBrushSize(brushSlider.getValue());
            }
        });
        canvasPanel.add(brushSlider);
        springLayout.putConstraint(SpringLayout.SOUTH, brushSlider, -10, SpringLayout.SOUTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, brushSlider, 75, SpringLayout.WEST, canvasPanel);

        //Undo Button
        JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                canvas.Undo();
            }
        });

        canvasPanel.add(undo);
        springLayout.putConstraint(SpringLayout.SOUTH, undo, -25, SpringLayout.SOUTH, canvasPanel);
        springLayout.putConstraint(SpringLayout.WEST, undo, 10, SpringLayout.WEST, canvasPanel);

        this.getContentPane().add(canvasPanel);

        saveItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePanel(canvas);
            }
        });

        importItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { loadImage(); }
        });
    }

    public Canvas getCanvas(){
        return canvas;
    }

    public void savePanel(JPanel panel){
        BufferedImage imagebuf=null;
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showSaveDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            try {
                imagebuf = new Robot().createScreenCapture(panel.bounds());
            } catch (AWTException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            Graphics2D graphics2D = imagebuf.createGraphics();
            panel.paint(graphics2D);
            try {
                ImageIO.write(imagebuf,"jpeg", new File(fileChooser.getSelectedFile().getAbsolutePath()));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                System.out.println("error");
            }
        }
    }

    public void loadImage(){
        JFileChooser fileChooser = new JFileChooser();
        int response = fileChooser.showOpenDialog(null);

        if(response == JFileChooser.APPROVE_OPTION){
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(new File(selectedFile.getAbsolutePath()));
                canvas.setImage(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
