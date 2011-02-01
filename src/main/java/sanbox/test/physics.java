/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sanbox.test;

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

    public matter absorb(matter a, matter b) {
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
        if (x.x + x.speedX > width) {
            x.x = 2 * width - x.x - x.speedX;
            x.speedX = -x.speedX;
        } else if (x.x + x.speedX < 0) {
            x.x = -x.x - x.speedX;
            x.speedX = -x.speedX;
        } else {
            x.x = x.x + x.speedX;
        }

        if (x.y + x.speedY > height) {
            x.y = 2 * height - x.y - x.speedY;
            x.speedY = -x.speedY;
        } else if (x.y + x.speedY < 0) {
            x.y = -x.y - x.speedY;
            x.speedY = -x.speedY;
        } else {
            x.y = x.y + x.speedY;
        }
    }


}
