package com.example;

import java.awt.*;

public class ColorPoints {
    private Color color;
    private int strokeSize;
    private int x;
    private int y;

    public ColorPoints(int x, int y, Color color, int strokeSize){
        this.x = x;
        this.y = y;
        this.color = color;
        this.strokeSize = strokeSize;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getStrokeSize(){
        return strokeSize;
    }
}
