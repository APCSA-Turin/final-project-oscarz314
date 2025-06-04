package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JPanel {

    private int strokeSize = 4;
    private Color color;
    private Color tempColor;
    private int x;
    private int y;
    private int width;
    private int height;
    private ColorHistory colorHistory;
    private Indicator indicator;

    //Draw lines between points
    private List<ColorPoints> currentPath;
    private List<List<ColorPoints>> allPath;

    public Canvas(int width, int height){
        this.setPreferredSize(new Dimension(width, height));
        this.setOpaque(true);
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.width = width;
        this.height = height;
        allPath = new ArrayList<>(200);
        colorHistory = new ColorHistory(this);
        color = Color.BLACK;

        // Mouse indicator
        indicator = new Indicator();
        this.add(indicator);

        // Set cursor to transparent
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        this.setCursor(blankCursor);

        MouseAdapter ma = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                //get mouse location
                x = e.getX();
                y = e.getY();

                //draw in location
                Graphics g = getGraphics();
                g.setColor(color);
                g.fillRect(x, y, strokeSize, strokeSize);
                g.dispose();

                //start path
                currentPath = new ArrayList<>(200);
                currentPath.add(new ColorPoints(x, y, color, strokeSize));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //reset current path
                addColorHistory();
                allPath.add(currentPath);
                currentPath = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                indicator.setVisible(false);
                //get mouse location
                x = e.getX();
                y = e.getY();

                //
                Graphics2D g2d = (Graphics2D) getGraphics();
                g2d.setColor(color);
                if(!currentPath.isEmpty()){
                    ColorPoints prevPoint = currentPath.get(currentPath.size() - 1);
                    g2d.setStroke(new BasicStroke(strokeSize));

                    //connect current to previous point by drawing a line
                    g2d.drawLine(prevPoint.getX(), prevPoint.getY(), x, y);
                }
                g2d.dispose();

                //add new point to path
                ColorPoints nextPoint = new ColorPoints(e.getX(), e.getY(), color, strokeSize);
                currentPath.add(nextPoint);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                indicator.setVisible(true);
                indicator.setBounds(e.getX(), e.getY(), 100, 100);
                indicator.updateIndicator(strokeSize, color);
            }
        };


        addMouseListener(ma);
        addMouseMotionListener(ma);
    }

    public void setColor(Color c){
        color = c;
    }

    public void resetCanvas(){
        Graphics g = getGraphics();
        g.clearRect(0, 0, width, height);
        g.dispose();

        currentPath = null;
        allPath = new ArrayList<>(200);

        repaint();
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d= (Graphics2D) g;

        //redraws lines
        for(List<ColorPoints> path: allPath){
            ColorPoints from = null;
            for(ColorPoints points: path){
                if(path.size() == 1){
                    g2d.fillRect(points.getX(), points.getY(), strokeSize, strokeSize);
                }

                if(from != null){
                    g2d.setColor(points.getColor());
                    g2d.setStroke(new BasicStroke(points.getStrokeSize()));
                    g2d.drawLine(from.getX(), from.getY(), points.getX(), points.getY());
                }
                from = points;
            }
        }
    }

    public void IsEraser(boolean isEraser){
        if(isEraser){
            tempColor = color;
            color = Color.WHITE;
        }
        else{
            color = tempColor;
        }
    }

    public void setBrushSize(int brushSize){
        strokeSize = brushSize;
    }

    public void Undo(){
        allPath.remove(allPath.getLast());
        paintComponent(getGraphics());
    }

    public void setImage(BufferedImage image){
        Graphics g = getGraphics();
        g.drawImage(image,0, 0, null);
    }

    public void addColorHistory(){
        colorHistory.addColor(color);
    }

    public ColorHistory returnColorHistory(){
        return colorHistory;
    }
}
