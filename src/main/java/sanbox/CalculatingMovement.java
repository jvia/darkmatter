package sanbox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * @author via
 */
public class CalculatingMovement {
    JFrame frame;
    CirclePanel panel;
    static int WIDTH = 400;
    static int HEIGHT = 400;

    public CalculatingMovement() {
        frame = new JFrame();
        panel = new CirclePanel();
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
        frame.addMouseListener(panel);
    }

    public boolean isOver() {
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        CalculatingMovement cm = new CalculatingMovement();

        int fps = 0;
        long time = System.currentTimeMillis();
        // Main game loop
        while (!cm.isOver()) {

            if (System.currentTimeMillis() - time >= 1000) {
                System.out.printf("FPS: %d\n", fps);
                fps = 0;
                time = System.currentTimeMillis();
            } else {
                ++fps;
            }

            // Update the GUI & sleep
            cm.frame.repaint();
            Thread.sleep(5);
        }

    }
}

class Circle extends Ellipse2D.Double {

    public Circle(int x, int y, int r) {
        super(x, y, r, r);
    }
}

class CirclePanel extends JPanel implements MouseListener {

    private Circle circle;
    ArrayList<Circle> circles;
    private Point2D click = new Point2D.Double();
    private String msg = "HI";

    public CirclePanel() {
        circle = new Circle(185, 185, 30);
        circles = new ArrayList<Circle>();
        circles.add(circle);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0, 0, WIDTH, HEIGHT);

        g2.setColor(Color.black);
        g2.fill(circle);

        g2.setColor(Color.red);
        g2.drawString(msg, 10, 290);
        // Draw sexy triangle
        g2.draw(new Line2D.Double(circle.getCenterX(), circle.getCenterY(), click.getX(), click.getY()));
        g2.draw(new Line2D.Double(circle.getCenterX(), circle.getCenterY(), click.getX(), circle.getCenterY()));
        g2.draw(new Line2D.Double(click.getX(), circle.getCenterY(), click.getX(), click.getY()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        msg = String.format("Circle: (%.2f, %.2f)    Click: (%d, %d) ",
                circle.x, circle.y, e.getX(), e.getY());
        click.setLocation(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
