/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox.test;

import java.awt.geom.Ellipse2D;

/**
 *
 * @author Yukun
 */
public class matter extends Ellipse2D.Double {

    public double radius;
    public double size;
    public double speedX;
    public double speedY;

    public matter(double x, double y, double r, double sX, double sY) {
        super(x, y, 2*r, 2*r);
        radius = r;
        setSize();
        speedX = sX;
        speedY = sY;
    }

    public void setRadius() {
        radius = Math.sqrt(size / Math.PI);
    }

    public final void setSize() {
        size = Math.PI * Math.pow(radius, 2);
    }

    public double getSpeedX() {
        return speedX;
    }

    public double getSpeedT() {
        return speedY;
    }

    public double getSize() {
        return size;
    }

    public double getRadius() {
        return radius;
    }

    public boolean intersects(matter a) {
        double distance = Math.hypot((a.x+a.radius - x-radius), (a.y+a.radius - y-radius));
        return distance <= (a.radius + radius);
    }

    public boolean eat(matter a) {
        return this.contains(a.x+a.radius, a.y+a.radius);
    }

    public void up() {
        if (speedY > -3) {
            speedY = speedY - 1;
        }
    }

    public void right() {
        if (speedX < 3) {
            speedX = speedX + 1;
        }
    }

    public void left() {
        if (speedY > -3) {
            speedX = speedX - 1;
        }
    }

    void mouse(int px, int py) {
        if (px > x + radius) {
            speedX = speedX - 0.4;
        } else {
            speedX = speedX + 0.4;
        }
        if (py > y + radius) {
            speedY = speedY - 0.4;
        } else {
            speedY = speedY + 0.4;
        }

    }

    void slow() {
        speedX = speedX / 2;
        speedY = speedY / 2;
    }
}
