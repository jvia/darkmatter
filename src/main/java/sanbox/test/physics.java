/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox.test;

import java.util.ArrayList;

/**
 *
 * @author Yukun
 */
public class physics {

    int width;
    int height;

    public physics(int x, int y) {
        width = x;
        height = y;
    }

    public ArrayList absorb(matter a, matter b) {
        double d = Math.hypot((a.x + a.radius - b.x - b.radius), (a.y + a.radius - b.y - b.radius));
        double s = a.size + b.size;
        double big = (d + Math.sqrt(d * d - 2 * (d * d - s / Math.PI))) / 2;
        double small = (d - Math.sqrt(d * d - 2 * (d * d - s / Math.PI))) / 2;
        if (a.size > b.size) {
            a = new matter(a.x, a.y, big, a.speedX, a.speedY);
            b = new matter(b.x, b.y, small, b.speedX, b.speedY);
        } else {
            b = new matter(a.x, a.y, big, a.speedX, a.speedY);
            a = new matter(b.x, b.y, small, b.speedX, b.speedY);
        }
        ArrayList<matter> l = new ArrayList<matter>();
        l.add(a);
        l.add(b);
        return l;
    }

    public matter eat(matter a, matter b) {
        matter c;

        double x;// new position x
        double y;// new position y
        double speedX = (a.speedX * a.size + b.speedX * b.size) / (a.size + b.size);
        double speedY = (a.speedY * a.size + b.speedY * b.size) / (a.size + b.size);
        double size = a.size + b.size;
        double r = Math.sqrt(size / Math.PI); // new radius
        if (a.size > b.size) {
            x = a.x;
            y = a.y;
        } else {
            x = b.x;
            y = b.y;
        }
        c = new matter(x, y, r, speedX, speedY);
        return c;
    }

    public void move(matter x) {
        if (x.getMaxX() > width) {
            x.speedX = -x.speedX;
        } else if (x.getMinX() < 0) {
            x.speedX = -x.speedX;
        }
        if (x.getMaxY() > height) {
            x.speedY = -x.speedY;
        } else if (x.getMinY() < 0) {
            x.speedY = -x.speedY;
        }
        while (x.getMaxX() > width || x.getMinX() < 0 ) {
            x.x = x.x + x.speedX;
            
        }
        while(  x.getMaxY() > height || x.getMinY() < 0) {
            x.y = x.y + x.speedY;
        }
        x.x = x.x + x.speedX;
        x.y = x.y + x.speedY;


    }
}
