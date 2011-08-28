package com.giantcow.darkmatter.level.editor;

import java.awt.*;

/**
 *
 * @author yukun
 */
public abstract class PictureElement {

    public static final int LINE = 1, RECTANGLE = 2, OVAL = 3;
    private int x1, y1, width, height;

    /**
     *
     * @param x1 the value of x1
     * @param y1 the value of y1
     * @param width the value of width
     * @param height the value of height
     */
    public PictureElement(int x1, int y1, int width, int height) {
        setPosition(x1, y1, width, height);
    }

    /**
     * set method
     * @param x1 the value of x1
     * @param y1 the value of y1
     * @param x2 the value of width
     * @param y2 the value of height
     */
    public void setPosition(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.width = x2;
        this.height = y2;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public abstract void draw(Graphics g);
}

