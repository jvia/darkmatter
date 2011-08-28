package com.giantcow.darkmatter.level.editor;

import java.awt.*;

/** @author yukun */
public class Oval {
    private int x, y, x2, y2;
    private int radius;

    /**
     * @param x  the vale of coordinate x
     * @param y  the vale of coordinate y
     * @param x2 the value of x2
     * @param y2 the value of y2
     */
    public Oval(int x, int y, int x2, int y2) {
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        radius = Math.abs(x - x2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getX2() {
        return x2;
    }

    public int getY2() {
        return y2;
    }

    /**
     * draw the oval
     *
     * @param g
     */
    public void draw(Graphics g) {// </editor-fold>
        if (Math.abs(getX2() - getX()) > Math.abs(getY2() - getY())) {
            if (getX2() > getX() && getY2() > getY()) {
                g.fillOval(getX(), getY(), getX2() - getX(), getX2() - getX());
            } else if (getX2() > getX() && getY2() < getY()) {
                g.fillOval(getX(), getY2(), getX2() - getX(), getX() - getX2());
            } else if (getX2() < getX() && getY2() < getY()) {
                g.fillOval(getX2(), getY2(), getX() - getX2(), getX() - getX2());
            } else if (getX2() < getX() && getY2() > getY()) {
                g.fillOval(getX2(), getY(), getX() - getX2(), getX2() - getX());
            }
        } else {
            if (getX2() > getX() && getY2() > getY()) {
                g.fillOval(getX(), getY(), getY2() - getY(), getY2() - getY());
            } else if (getX2() > getX() && getY2() < getY()) {
                g.fillOval(getX(), getY2(), getY() - getY2(), getY() - getY2());
            } else if (getX2() < getX() && getY2() < getY()) {
                g.fillOval(getX2(), getY2(), getY() - getY2(), getY() - getY2());
            } else if (getX2() < getX() && getY2() > getY()) {
                g.fillOval(getX2(), getY(), getY2() - getY(), getY2() - getY());
            }
        }
    }

    /**
     * set method
     *
     * @param x1 the value of x1
     * @param y1 the value of y1
     * @param x2 the value of x2
     * @param y2 the value of y2
     */
    public void setPosition(int x1, int y1, int x2, int y2) {
        this.x = x1;
        this.y = y1;
        this.x2 = x2;
        this.y2 = y2;
        radius = Math.abs(x - x2);
    }

    public String toString() {
        return String.format("%4d.0, %4d.0, %3d.0,   0.0,   0.0", x, y, radius);
    }

    public int width() {
        return 2 * radius;
    }

    public int height() {
        return 2 * radius;
    }
}

