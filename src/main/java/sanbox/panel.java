package sanbox;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.JPanel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Yukun
 */
public class panel extends JPanel implements ActionListener, KeyListener, MouseListener {

    Object o = new Object();
    Timer time;

    public panel() {
        this.setSize(610, 610);
        o.initializ(600, 600);
        time = new Timer(50, this);
        time.setInitialDelay(200);
        time.start();
        this.addKeyListener(this);
        this.addMouseListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics g2 = (Graphics2D) g;
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, 615, 615);
        g2.setColor(Color.RED);
        g2.fillRect((int) o.getPositionX(), (int) o.getPositionY(), 10, 10);

        next();
    }

    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    public void next() {
        o.move();
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyPressed(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_UP) {
            o.up();
        }
        if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            o.down();
        }
        if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            o.right();
        }
        if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            o.left();
        }
    }

    public void mouseClicked(MouseEvent me) {
        o.mouse(me.getX(), me.getY());
    }

    public void mousePressed(MouseEvent me) {
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("physice");
        frame.setSize(615, 650);
        frame.setTitle("example");
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        panel p = new panel();
        frame.add(p);
        p.requestFocus();
    }
}

class Object {

    double positionX;
    double positionY;
    double speedX;
    double speedY;
    double borderX;
    double borderY;

    public void initializ(double x, double y) {
        borderX = x;
        borderY = y;
        positionX = x / 2;
        positionY = y / 2;
        speedX = 1;
        speedY = 1;
    }

    public void setPositionX(double x) {
        positionX = x;
    }

    public void setPositionY(double y) {
        positionY = y;
    }

    public double getPositionX() {
        return positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void move() {
        if (positionX + speedX > borderX) {
            positionX = 2 * borderX - positionX - speedX;
            speedX = -speedX;
        } else if (positionX + speedX < 0) {
            positionX = -positionX - speedX;
            speedX = -speedX;
        } else {
            positionX = positionX + speedX;
        }

        if (positionY + speedY > borderY) {
            positionY = 2 * borderY - positionY - speedY;
            speedY = -speedY;
        } else if (positionY + speedY < 0) {
            positionY = -positionY - speedY;
            speedY = -speedY;
        } else {
            positionY = positionY + speedY;
        }
    }

    public void down() {
        if (speedY < 3) {
            speedY = speedY + 1;
        }
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

    void mouse(int x, int y) {
        speedX = (x - positionX) / 100;
        speedY = (y - positionY) / 100;
    }
}
